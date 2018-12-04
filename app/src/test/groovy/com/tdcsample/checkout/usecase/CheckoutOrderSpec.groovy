package com.tdcsample.checkout.usecase

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.tdcsample.checkout.conf.ff4j.Features
import com.tdcsample.checkout.domain.BilletToPay
import com.tdcsample.checkout.domain.Payment
import com.tdcsample.checkout.domain.PaymentStatus
import com.tdcsample.checkout.domain.Product
import com.tdcsample.checkout.domain.ShoppingCart
import com.tdcsample.checkout.domain.Stock
import com.tdcsample.checkout.domain.exception.StockWithoutProductException
import com.tdcsample.checkout.domain.exception.NotPaidException
import com.tdcsample.checkout.gateway.FeatureGateway
import com.tdcsample.checkout.gateway.OrderGateway
import com.tdcsample.checkout.gateway.PaymentGateway
import com.tdcsample.checkout.gateway.ProductGateway
import com.tdcsample.checkout.gateway.StockGateway
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class CheckoutOrderSpec extends Specification {

    private CheckoutOrder checkoutOrder

    private GenerateBillet generateBillet
    private StockGateway stockGateway
    private OrderGateway orderGateway
    private PaymentGateway paymentGateway
    private ProductGateway productGateway
    private FeatureGateway featureGateway

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates")
    }

    def setup() {
        generateBillet = Mock(GenerateBillet)
        stockGateway = Mock(StockGateway)
        orderGateway = Mock(OrderGateway)
        paymentGateway = Mock(PaymentGateway)
        productGateway = Mock(ProductGateway)
        featureGateway = Mock(FeatureGateway)

        checkoutOrder = new CheckoutOrder(generateBillet, stockGateway, orderGateway, paymentGateway, productGateway, featureGateway)
    }

    def "test verify shopping cart not null"() {
        when: ""
        def order = checkoutOrder.execute(null, null)

        then: ""
        order == null

        and:
        IllegalArgumentException ex = thrown()
        ex.message == 'Shopping cart cannot be null'
    }

    def "test verify payment method not null"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with one item")

        when: ""
        def order = checkoutOrder.execute(shoppingCart, null)

        then: ""
        order == null

        and:
        IllegalArgumentException ex = thrown()
        ex.message == 'PaymentRequest method cannot be null'
    }

    def "test checkout generate order shopping cart with one product but not approved credit card"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with one item")

        Product product = from(Product.class).gimme("moto x 4")
        productGateway.findByCode(product.id.toString()) >> product

        Stock stock = from(Stock.class).gimme("product 1 with one in stock")
        stockGateway.getStock(product) >> stock

        featureGateway.isFeatureEnable(Features.BILLET_PAYMENT) >> false

        orderGateway.register(_) >> _

        Payment creditCardPayment = from(Payment.class).gimme("mastercard")
        paymentGateway.process(_ as Payment, _ as Double) >> { payment, value ->
            throw new NotPaidException('Error processing credit card payment')
        }

        when: ""
        def order = checkoutOrder.execute(shoppingCart, creditCardPayment)

        then: ""
        order == null

        and:
        NotPaidException ex = thrown()
        ex.message == 'Error processing credit card payment'
    }

    def "test checkout do not generate order shopping cart without itens"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with no item")
        Payment creditCardPayment = from(Payment.class).gimme("mastercard")

        when: ""
        def order = checkoutOrder.execute(shoppingCart, creditCardPayment)

        then: ""
        order == null

        and:
        IllegalArgumentException ex = thrown()
        ex.message == 'Shopping cart must have items'
    }

    def "test checkout do not generate order shopping cart for null itens"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with null item")
        Payment creditCardPayment = from(Payment.class).gimme("mastercard")

        when: ""
        def order = checkoutOrder.execute(shoppingCart, creditCardPayment)

        then: ""
        order == null

        and:
        IllegalArgumentException ex = thrown()
        ex.message == 'Shopping cart must have items'
    }

    def "test checkout generate order shopping cart with one product"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with one item")

        Product product = from(Product.class).gimme("moto x 4")
        productGateway.findByCode(product.id.toString()) >> product

        Payment creditCardPayment = from(Payment.class).gimme("mastercard")

        Stock stock = from(Stock.class).gimme("product 1 with one in stock")
        stockGateway.getStock(_) >> stock

        featureGateway.isFeatureEnable(Features.BILLET_PAYMENT) >> false

        orderGateway.register(_) >> _

        paymentGateway.process(_, _) >> from(PaymentStatus.class).gimme("PAID")

        when: ""
        def order = checkoutOrder.execute(shoppingCart, creditCardPayment)

        then: ""
        order.id != null
        order.orderNumber != null
        order.value == new BigDecimal("999.99")
        order.username == shoppingCart.getUsername()
        order.items.size() == 1
        order.transactionId != ""

        and: ""
        order.items[0].quantity == 1
        order.items[0].value == new BigDecimal("999.99")
        order.items[0].productCode == product.id.toString()
    }

    def "test checkout do not generate order for unaproved payment"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with one item")

        Product product = from(Product.class).gimme("moto x 4")
        productGateway.findByCode(product.id.toString()) >> product

        Payment creditCardPayment = from(Payment.class).gimme("mastercard")

        Stock stock = from(Stock.class).gimme("product 1 with one in stock")
        stockGateway.getStock(_) >> stock

        featureGateway.isFeatureEnable(Features.BILLET_PAYMENT) >> false

        orderGateway.register(_) >> _

        paymentGateway.process(_, _) >> from(PaymentStatus.class).gimme("INSUFFICIENT_FUNDS")

        when: ""
        def order = checkoutOrder.execute(shoppingCart, creditCardPayment)

        then: ""
        NotPaidException ex = thrown()
    }

    def "test checkout without product in stock"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with two items")

        featureGateway.isFeatureEnable(Features.BILLET_PAYMENT) >> false

        Product product1 = from(Product.class).gimme("moto x 4")
        productGateway.findByCode(product1.id.toString()) >> product1

        Product product2 = from(Product.class).gimme("cellphone case moto x 4")
        productGateway.findByCode(product2.id.toString()) >> product2

        Stock product1Stock = from(Stock.class).gimme("product 1 with one in stock")
        stockGateway.getStock(product1) >> product1Stock

        Stock product2Stock = from(Stock.class).gimme("product 2 with one in stock")
        stockGateway.getStock(product2) >> product2Stock

        Payment creditCardPayment = from(Payment.class).gimme("mastercard")

        when: ""
        def order = checkoutOrder.execute(shoppingCart, creditCardPayment)

        then: ""
        order == null

        and:
        StockWithoutProductException ex = thrown()
        ex.message == 'Product without enough in stock'
        ex.product == product2
        ex.quantityInStock == 1
    }

    def "test payment with billet feature active"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with one item")
        Payment billetPayment = from(Payment.class).gimme("billet")

        Product product = from(Product.class).gimme("moto x 4")
        productGateway.findByCode(product.id.toString()) >> product

        Stock stock = from(Stock.class).gimme("product 1 with one in stock")
        stockGateway.getStock(_) >> stock

        orderGateway.register(_) >> _

        featureGateway.isFeatureEnable(Features.BILLET_PAYMENT) >> true

        BilletToPay billetToPay = from(BilletToPay.class).gimme("billet to due in 7 days")
        generateBillet.execute(billetToPay.value.toDouble()) >> billetToPay

        when: ""
        def order = checkoutOrder.execute(shoppingCart, billetPayment)

        then: ""
        order.id != null
        order.orderNumber != null
        order.value == new BigDecimal("999.99")
        order.username == shoppingCart.getUsername()
        order.items.size() == 1

        and: ""
        order.items[0].quantity == 1
        order.items[0].value == new BigDecimal("999.99")
        order.items[0].productCode == product.id.toString()

        and: ""
        order.billet.value != null
        order.billet.number != null
        order.billet.dueDate != null
    }

    def "test payment with billet feature NOT active"() {
        given: ""
        ShoppingCart shoppingCart = from(ShoppingCart.class).gimme("shopping cart with one item")
        Payment billetPayment = from(Payment.class).gimme("billet")

        Product product = from(Product.class).gimme("moto x 4")
        productGateway.findByCode(product.id.toString()) >> product

        Stock stock = from(Stock.class).gimme("product 1 with one in stock")
        stockGateway.getStock(_) >> stock

        orderGateway.register(_) >> _

        featureGateway.isFeatureEnable(Features.BILLET_PAYMENT) >> false

        BilletToPay billetToPay = from(BilletToPay.class).gimme("billet to due in 7 days")
        generateBillet.execute(billetToPay.value.toDouble()) >> billetToPay

        when: ""
        def order = checkoutOrder.execute(shoppingCart, billetPayment)

        then: ""
        IllegalArgumentException ex = thrown()
        ex.message == 'Feature billet payment NOT active'
    }
}
