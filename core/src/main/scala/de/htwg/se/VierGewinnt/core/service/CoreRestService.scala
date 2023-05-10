package de.htwg.se.VierGewinnt.core.service

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import akka.http.scaladsl.server.Directives.*
import com.google.inject.{Guice, Injector}
import de.htwg.se.VierGewinnt.core.*
import de.htwg.se.VierGewinnt.util.Move
import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}
import spray.json.DefaultJsonProtocol.IntJsonFormat
import spray.json.enrichAny

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object CoreRestService extends App{
  val injector: Injector = Guice.createInjector(CoreModule())
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

  private val logger = LoggerFactory.getLogger(getClass)

  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "CoreAPI")
  given ActorSystem[Any] = system

  val executionContext = system.executionContext
  given ExecutionContextExecutor = executionContext
  val servicePort = 8080

  val route =
    concat(
      get {
        path("core" / "load") {
          controller.load
          complete(HttpEntity(ContentTypes.`application/json`, "loaded"))
        }
      },
      post {
        path("core" / "save") {
          controller.save
          complete(HttpEntity(ContentTypes.`application/json`, "saved"))
        }
      },
      post {
        path("core" / "setupGame") {
          entity(as[String]) { request =>
            val json = Json.parse(request)
            val gameType = (json \ "gameType").get.toString.replaceAll("\"", "").toInt
            val size = (json \ "size").get.toString.replaceAll("\"", "").toInt
            controller.setupGame(gameType, size)
            complete(HttpEntity(ContentTypes.`application/json`, "game set up"))
          }
        }
      },
      post {
        path("core" / "insChip") {
          entity(as[String]) { request =>
            val json = Json.parse(request)
            val move = (json \ "size").get.toString.replaceAll("\"", "").toInt
            val updatedPlayground = controller.insChip(Move(move))
            complete(HttpEntity(ContentTypes.`application/json`, Util.toJson(updatedPlayground).toString))
          }
        }
      },
      get {
        path("core" / "undo") {
          val updatedPlayground = controller.undo
          complete(HttpEntity(ContentTypes.`application/json`, Util.toJson(updatedPlayground).toString))
        }
      },
      get {
        path("core" / "redo") {
          val updatedPlayground = controller.redo
          complete(HttpEntity(ContentTypes.`application/json`, Util.toJson(updatedPlayground).toString))
        }
      },
      get {
        path("core" / "status") {
          complete(HttpEntity(ContentTypes.`application/json`, controller.playgroundState))
        }
      },
      get {
        path("core" / "state") {
          complete(HttpEntity(ContentTypes.`application/json`, controller.printState))
        }
      }
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
