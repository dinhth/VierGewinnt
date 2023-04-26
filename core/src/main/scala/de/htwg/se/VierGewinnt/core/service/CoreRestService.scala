package de.htwg.se.VierGewinnt.core.service

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import akka.http.scaladsl.server.Directives.*
import com.google.inject.{Guice, Injector}
import de.htwg.se.VierGewinnt.core.*

import scala.util.{Failure, Success}

object CoreRestService {
  val injector: Injector = Guice.createInjector(CoreModule())
  val controller = injector.getInstance(classOf[ControllerInterface])

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext
    val servicePort = 8080
    val route =
      concat(
        get {
          path("load") {
            controller.load
            complete(HttpEntity(ContentTypes.`application/json`, "loaded"))
          }
        },
        post {
          path("save") {
            controller.save
            complete(HttpEntity(ContentTypes.`application/json`, "saved"))
          }
        }
      )
    val bound = Http().newServerAt("localhost", 8080).bind(route)
    bound.onComplete {
      case Success(bind) => print(bind)
      case Failure(exception) => {}
    }
  }
}
