import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

class EnduranceTest extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://localhost:8081")
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .userAgentHeader("PostmanRuntime/7.32.2")

  private val headers = Map(
    "Cache-Control" -> "no-cache",
    "Content-Type" -> "application/json",
    "Proxy-Connection" -> "keep-alive"
  )

  private val scn = scenario("EnduranceTest").repeat(10000, "i") {
    exec(
      http("Create DB")
        .get("/db/create")
        .headers(headers)
    )
      .exec(
        http("Update DB")
          .post("/db/update")
          .headers(headers)
          .body(RawFileBody("src/test/resources/game.json"))
      )
      .exec(
        http("Read DB")
          .get("/db/read")
          .headers(headers)
      )
      .exec(
        http("Delete DB")
          .get("/db/delete")
          .headers(headers)
      )
  }
  setUp(scn.inject(atOnceUsers(100))).protocols(httpProtocol)
}
