package checkout.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Checkout {

  val URI = "/api/v1/order"

  val baseName = "Checkout"
  val requestName = baseName + "-SubmitOrder"

  val checkout = exec(
      http(requestName)
        .post(URI)
        .header("user", "user")
        .header("Content-Type", "application/json")
        .body(ElFileBody("checkout.json"))
        .check(status.is(200))
  )
}
