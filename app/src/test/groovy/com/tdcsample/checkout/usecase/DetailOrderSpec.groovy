package com.tdcsample.checkout.usecase

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.tdcsample.checkout.domain.Order
import com.tdcsample.checkout.gateway.OrderGateway
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class DetailOrderSpec extends Specification {

    private DetailOrder orderDetail

    private OrderGateway orderGateway

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates")
    }

    def setup() {
        orderGateway = Mock(OrderGateway)

        orderDetail = new DetailOrder(orderGateway)
    }

    def "test verify gateway is call to find order by id"() {
        given: "A valid order"
        Order order = from(Order.class).gimme('order 1')
        def orderId = order.id

        when: "get the order detail"
        orderDetail.execute(orderId)

        then: "it calls the gateway to reatrive the order"
        1 * orderGateway.findById(orderId)
    }

    def "test verify order is returning"() {
        given: "A valid order"
        Order order = from(Order.class).gimme('order 1')
        def orderId = order.id
        orderGateway.findById(orderId) >> order

        when: "get the order"
        def orderActual = orderDetail.execute(orderId)

        then: "it return an valid order"
        orderActual != null
        orderActual == order
    }
}
