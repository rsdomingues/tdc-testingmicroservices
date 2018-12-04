package checkout.simulation

import checkout.{BaseSimulation, Protocol}
import checkout.scenarios._
import io.gatling.core.Predef._

class DetailOrderSimulation extends BaseSimulation {

  val detailOrder = scenario("Detail orders of user").exec(
    DetailOrder.detail
  )

  setUp(
    detailOrder.inject(
      constantUsersPerSec(noOfUsersPerSec) during (durationTime)
    )
  ).assertions(
    global.responseTime.mean.lte(500),
    forAll.failedRequests.percent.is(0.0)
  ).protocols(Protocol.Application);

}
