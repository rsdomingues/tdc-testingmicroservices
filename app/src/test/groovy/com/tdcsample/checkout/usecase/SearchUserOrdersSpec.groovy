package com.tdcsample.checkout.usecase

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.tdcsample.checkout.domain.Order
import com.tdcsample.checkout.gateway.OrderGateway
import spock.lang.Specification

import static br.com.six2six.fixturefactory.Fixture.from

class SearchUserOrdersSpec extends Specification {

    private SearchUserOrders searchOrder;

    private OrderGateway orderGateway

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates")
    }

    def setup() {
        orderGateway = Mock(OrderGateway)

        searchOrder = new SearchUserOrders(orderGateway)
    }

    def "test search all orders by username"() {
        given: ""
        String username = 'carlosz'

        List<Order> orders = from(Order.class).gimme(2, 'order 1', 'order 2')

        orderGateway.findAllByUsername(username) >> orders

        when: ""
        def listOfOrders = searchOrder.execute(username)

        then: ""
        assert listOfOrders.size() == 2
    }
}
