package sample

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.RequestEntityAcceptance.Expected
import akka.http.scaladsl.model._
import akka.http.scaladsl.settings.{ParserSettings, ServerSettings}
import akka.stream.ActorMaterializer

import scala.io.StdIn

object WebServer {
  def run() {

    implicit val system = ActorSystem("webserver-actor-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    // define custom media type:
    val BOLT = HttpMethod.custom("BOLT", safe = false, idempotent = true, requestEntityAcceptance = Expected)

    // add custom method to parser settings:
    val parserSettings = ParserSettings(system).withCustomMethods(BOLT)
    val serverSettings = ServerSettings(system).withParserSettings(parserSettings)

    def requestHandler: HttpRequest => HttpResponse = {

      case HttpRequest(BOLT, uri, _, _, _) =>
        //sys.error("nothing implemented")
        HttpResponse(200, entity = "HTTP BOLD")

      case HttpRequest(GET, uri, _, _, _) =>
        HttpResponse(200, entity = "Hallo World!")

      case r: HttpRequest =>
        r.discardEntityBytes() // important to drain incoming HTTP Entity stream
        HttpResponse(404, entity = "Unknown resource!")
    }

    val bindingFuture = Http().bindAndHandleSync(requestHandler, "localhost", 8080, settings = serverSettings)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}