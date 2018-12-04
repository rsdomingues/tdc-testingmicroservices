package checkout.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object DetailOrder {

  val URI = "/api/v1/orders/user"

  val baseName = "Checkout"
  val requestName = baseName + "-DetailOrder"

  val detail = exec(
    http(requestName)
      .get(URI)
      .check(status.is(200))
  )
}
