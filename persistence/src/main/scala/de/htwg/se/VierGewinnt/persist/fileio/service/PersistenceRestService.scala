package de.htwg.se.VierGewinnt.persist.fileio

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.event.Logging
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
import com.google.inject.Guice
import com.google.inject.Injector
import org.slf4j.LoggerFactory
import scala.concurrent.*
import scala.concurrent.ExecutionContextExecutor
import scala.util.Failure
import scala.util.Success

object PersistenceRestService extends App {
  val injector: Injector = Guice.createInjector(PersistenceModule())
  val fileIo: FileIOInterface = injector.getInstance(classOf[FileIOInterface])

  private val logger = LoggerFactory.getLogger(getClass)

  // needed to run the route
  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "PersistenceService")
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
    path("fileio" / "json" / "load") {
      get {
        val result = fileIo.load
        logger.info("Received load request: {}", result)
        complete(HttpEntity(ContentTypes.`application/json`, result))
      }
    },
    path("fileio" / "json" / "save") {
      concat(
        post {
          entity(as[String]) { game =>
            logger.info("Received save request with game: {}", game)
            fileIo.save(game)
            complete("game saved")
          }
        }
      )
    }
  )

  val bindingFuture = Http().newServerAt("localhost", 8081).bind(route)

  println(s"File IO REST service online at http://localhost:8081/")
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}
