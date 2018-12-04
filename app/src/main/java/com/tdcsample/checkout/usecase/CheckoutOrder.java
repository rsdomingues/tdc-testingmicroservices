package com.tdcsample.checkout.usecase;

import com.tdcsample.checkout.conf.ff4j.Features;
import com.tdcsample.checkout.domain.BilletToPay;
import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.domain.Payment;
import com.tdcsample.checkout.domain.PaymentStatus;
import com.tdcsample.checkout.domain.PaymentType;
import com.tdcsample.checkout.domain.Product;
import com.tdcsample.checkout.domain.Purchase;
import com.tdcsample.checkout.domain.ShoppingCart;
import com.tdcsample.checkout.domain.Stock;
import com.tdcsample.checkout.domain.TransactionStatus;
import com.tdcsample.checkout.domain.exception.NotPaidException;
import com.tdcsample.checkout.domain.exception.StockWithoutProductException;
import com.tdcsample.checkout.gateway.FeatureGateway;
import com.tdcsample.checkout.gateway.OrderGateway;
import com.tdcsample.checkout.gateway.PaymentGateway;
import com.tdcsample.checkout.gateway.ProductGateway;
import com.tdcsample.checkout.gateway.StockGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
@Component
public class CheckoutOrder {

    private Random generator;
    private GenerateBillet generateBillet;
    private StockGateway stockGateway;
    private OrderGateway orderGateway;
    private PaymentGateway paymentGateway;
    private ProductGateway productGateway;
    private FeatureGateway featureGateway;

    public CheckoutOrder(GenerateBillet generateBillet, StockGateway stockGateway, OrderGateway orderGateway,
                         PaymentGateway paymentGateway, ProductGateway productGateway, FeatureGateway featureGateway) {
        this.generateBillet = generateBillet;
        this.stockGateway = stockGateway;
        this.orderGateway = orderGateway;
        this.paymentGateway = paymentGateway;
        this.productGateway = productGateway;
        this.featureGateway = featureGateway;

        generator = new Random();
    }

    public Order execute(ShoppingCart shoppingCart, Payment payment) {
        checkArgument(shoppingCart != null, "Shopping cart cannot be null");
        checkArgument(payment != null, "PaymentRequest method cannot be null");
        checkArgument(shoppingCart.getItems() != null, "Shopping cart must have items");
        checkArgument(!shoppingCart.getItems().isEmpty(), "Shopping cart must have items");

        double sumShoppingCart = shoppingCart.getItems()
                .stream()
                .mapToDouble(this::getProductPrice)
                .sum();

        Order order = null;

        if (featureGateway.isFeatureEnable(Features.BILLET_PAYMENT)) {
            log.info("Feature billet payment active");
            BilletToPay billetToPay = generateBillet.execute(sumShoppingCart);

            order = generateOrder(shoppingCart, sumShoppingCart, billetToPay);
        } else {
            if(payment.getType() == PaymentType.BILLET) {
                log.info("Feature billet payment NOT active and payment type is billet");
                throw new IllegalArgumentException("Feature billet payment NOT active");
            }

            PaymentStatus status = paymentGateway.process(payment, sumShoppingCart);

            if(isPaid(status)) {
                order = generateOrder(shoppingCart, sumShoppingCart, status);
            } else {
                throw new NotPaidException("Not paid");
            }
        }

        orderGateway.register(order);
        return order;
    }


    private boolean isPaid(PaymentStatus status){
        return Arrays.asList(TransactionStatus.PAID, TransactionStatus.NOT_SENT).contains(status.getStatus());
    }

    private double getProductPrice(Purchase purchase) {
        verifyProductInStock(purchase);
        return purchase.getValue().doubleValue();
    }

    private void verifyProductInStock(Purchase purchase) {
        Product product = productGateway.findByCode(purchase.getProductCode());
        Stock stock = stockGateway.getStock(product);

        int totalStock = stock.getTotal();
        int quantityToPurchase = purchase.getQuantity();

        if (totalStock >= quantityToPurchase) {
            stockGateway.lowStock(product, quantityToPurchase);
        } else {
            throw new StockWithoutProductException("Product without enough in stock", product, totalStock);
        }
    }

    private Order generateOrder(ShoppingCart shoppingCart, double sumShoppingCart, PaymentStatus status) {
        return Order.builder()
                .id(UUID.randomUUID())
                .orderNumber(generateOrderNumber())
                .username(shoppingCart.getUsername())
                .items(shoppingCart.getItems())
                .value(BigDecimal.valueOf(sumShoppingCart))
                .transactionId(status.getTransactionId())
                .build();
    }

    private Order generateOrder(ShoppingCart shoppingCart, double sumShoppingCart, BilletToPay billetToPay) {
        return Order.builder()
                .id(UUID.randomUUID())
                .orderNumber(generateOrderNumber())
                .username(shoppingCart.getUsername())
                .items(shoppingCart.getItems())
                .value(BigDecimal.valueOf(sumShoppingCart))
                .billet(billetToPay)
                .build();
    }

    private Long generateOrderNumber() {
        return Integer.toUnsignedLong(generator.nextInt());
    }
}
