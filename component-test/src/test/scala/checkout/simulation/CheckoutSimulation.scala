package checkout.simulation

import checkout.{BaseSimulation, Protocol}
import checkout.scenarios._
import io.gatling.core.Predef._

class CheckoutSimulation extends BaseSimulation {

  val checkout = scenario("CheckoutOrder").exec(
    Checkout.checkout
  )

  setUp(
    checkout.inject(
      constantUsersPerSec(noOfUsersPerSec) during (durationTime)
    )
  ).assertions(
    global.responseTime.mean.lte(200),
    forAll.failedRequests.percent.is(0.0)
  ).protocols(Protocol.Application);

}
