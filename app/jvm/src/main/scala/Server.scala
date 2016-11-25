import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Properties

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

object Server{

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val port = Properties.envOrElse("PORT", "8080").toInt
    val route = {
      get {
        pathSingleSlash{
          complete{
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              Page.skeleton.render
            )
          }
        } ~
        getFromResourceDirectory("")
      } ~
      get {
        path("api" / "pokemon" / Segment) { name =>
          complete {
            Api.pokemon(name.toString).map { pokemon =>
              upickle.default.write(pokemon)
            }
          }
        }
      }
    }
    Http().bindAndHandle(route, "0.0.0.0", port = port)
  }

}
