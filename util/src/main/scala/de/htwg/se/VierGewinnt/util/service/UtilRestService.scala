package de.htwg.se.VierGewinnt.util.service

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.Http
import de.htwg.se.VierGewinnt.util.Move
import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import scala.concurrent.ExecutionContextExecutor
import scala.util.Failure
import scala.util.Success

object UtilRestService {
  private val logger = LoggerFactory.getLogger(getClass)

  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "UtilAPI")

  given ActorSystem[Any] = system

  val executionContext = system.executionContext

  given ExecutionContextExecutor = executionContext

  val servicePort = 8083

  val route =
    concat(
      post {
        path("observer" / "add") {
          entity(as[String]) {request =>
            val observer = Json.parse(request)

            complete(HttpEntity(ContentTypes.`application/json`, "added "))
          }
        }
      },
      post {
        path("observer" / "remove") {
          entity(as[String]) {request =>
            val observer = Json.parse(request)

            complete(HttpEntity(ContentTypes.`application/json`, "loaded"))
          }
        }
      },
      get {
        path("observer" / "notifyObservers") {
          entity(as[String]) {request =>
            val observer = Json.parse(request)

            complete(HttpEntity(ContentTypes.`application/json`, "loaded"))
          }
        }
      },

    )

  val bindingFuture = Http().newServerAt("localhost", servicePort).bind(route)
  bindingFuture.onComplete {
    case Success(binding) =>
      val address = binding.localAddress
      logger.info(s"Core REST service online at http://localhost:${address.getPort}")

    case Failure(exception) =>
      logger.error(s"Core REST service couldn't be started! Error: {}", exception.getMessage)
  }
}
