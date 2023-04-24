package fileIOComponent.service
import akka.http.scaladsl.server.Directives.{complete, concat, get, path}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCode}
import akka.http.scaladsl.server.{ExceptionHandler, Route}

import scala.concurrent.ExecutionContextExecutor


class FileIOAPI {
  val routes: String =
    """
        persistence-service! Available routes:
          GET   /fileio/load
          POST  /fileio/save
        """.stripMargin

  // needed to run the route
  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
  given ActorSystem[Any] = system
  // needed for the future flatMap/onComplete in the end
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext

  val route = concat(
    pathSingleSlash {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>FileIO</h1>"))
    },
    path("fileio" / "load") {
      get {
        complete(HttpEntity(ContentTypes.`application/json`, PersistenceController.load()))
      }
    },
    path("fileio" / "save") {
      concat(
        post {
          entity(as[String]) { game =>
            PersistenceController.save(game)
            complete("game saved")
          }
        }
      )
    }
  )

  val bindingFuture = Http().newServerAt("0.0.0.0", 8081).bind(route)
  println(s"FileIO server online at http://localhost:8081/\nPress RETURN to stop...")

  def stop():Unit =
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
}