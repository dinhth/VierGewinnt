package de.htwg.se.VierGewinnt.view.gui

import akka.http.scaladsl.server.Directives.{complete, concat, get, path}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCode}
import akka.http.scaladsl.server.{ExceptionHandler, Route, StandardRoute}
import de.htwg.se.VierGewinnt.core.ControllerInterface

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success, Try}

class UiAPI(controller: ControllerInterface) {
  val routes: String =
    """
        Welcome to the ui-service! Available routes:
          GET   /persistence/load
          POST  /persistence/save
        """.stripMargin

  // needed to run the route
  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
  given ActorSystem[Any] = system
  // needed for the future flatMap/onComplete in the end
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext
  
  val route = concat(
    pathSingleSlash {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>ConnectFour</h1>"))
    },
    path("ui") {
      get {
        playgroundtoJson
      }
    },
    path("undo") {
      concat(
        get {
          controller.undo
          playgroundtoJson
        }
      )
    },
    path("redo") {
      concat(
        get {
          playgroundtoJson
        },
        post {
          controller.redo
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "redo success"))
        })
    },
    path("ui" / Segment) { command =>
    {
      processInputLine(command)
      playgroundtoJson
    }
    }
  )

  def playgroundtoJson: StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>ConnectFour</h1>" + controller.playground.toJsonString))
  }

  def processInputLine(input: String): Unit = {
    controller.insChip(input)
  }

  val bindingFuture = Http().newServerAt("0.0.0.0", 8080).bind(route)
  println(s"View server online at http://localhost:8080/\nPress RETURN to stop...")

  def stop():Unit =
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
}
