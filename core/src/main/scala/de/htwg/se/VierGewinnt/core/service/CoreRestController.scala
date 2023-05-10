package de.htwg.se.VierGewinnt.core.service

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.htwg.se.VierGewinnt.core.service.CoreRestService
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class CoreRestController {
  private val logger = LoggerFactory.getLogger(CoreRestService.getClass)

  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "CoreAPI")

  given ActorSystem[Any] = system

  val executionContext: ExecutionContextExecutor = system.executionContext

  given ExecutionContextExecutor = executionContext

  def sendGetRequest(url: String): Future[HttpResponse] = {
    val request = HttpRequest(
      method = HttpMethods.GET,
      uri = url
    )

    Http().singleRequest(request)
  }
  
  def sendPostRequest(url: String, payload: String): Future[HttpResponse] = {
    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = url,
      entity = HttpEntity(ContentTypes.`application/json`, payload)
    )

    Http().singleRequest(request)
  }
  
  def handleResponse(responseFuture: Future[HttpResponse], onSuccess: String => Unit): Unit =
    responseFuture
      .onComplete {
        case Failure(_) =>
          logger.error("Failed getting JSON")
        case Success(response) if response.status == StatusCodes.OK =>
          Unmarshal(response.entity).to[String].onComplete {
            case Failure(_) =>
              logger.error("Failed unmarshalling")
            case Success(jsonString) =>
              onSuccess(jsonString)
          }
        case Success(response) =>
          logger.error(s"Unexpected response status: ${response.status}")
      }

}
