package de.htwg.se.VierGewinnt.persist.fileio

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.Directives.concat
import akka.http.scaladsl.server.Directives.get
import akka.http.scaladsl.server.Directives.path
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.Http
import scala.concurrent.*
import scala.concurrent.ExecutionContextExecutor
import scala.util.Failure
import scala.util.Success

object FileIOAPI {
  def main(args: Array[String]): Unit =
    // needed to run the route
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")

    given ActorSystem[Any] = system

    // needed for the future flatMap/onComplete in the end
    val executionContext: ExecutionContextExecutor = system.executionContext

    given ExecutionContextExecutor = executionContext

    val routes: String =
      """
          Welcome to the Persistence REST service! Available routes:
            GET   /fileio/load
            POST  /fileio/save
          """.stripMargin

    val route = concat(
      pathSingleSlash {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, routes))
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

    val bindingFuture = Http().newServerAt("localhost", 8081).bind(route)

    bindingFuture.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        println(s"File IO REST service online at http://localhost:${address.getPort}\nPress RETURN to stop...")

      case Failure(exception) =>
        println(s"File IO REST service couldn't be started! Error:${exception}\n")

    }

  // def stop():Unit =
  //  bindingFuture
  //    .flatMap(_.unbind()) // trigger unbinding from the port
  //    .onComplete(_ => system.terminate()) // and shutdown when done

}
