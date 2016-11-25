import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scalacache._
import guava._

import gigahorse._

object Api {
  implicit val scalaCache = ScalaCache(GuavaCache())

  def pokemon(name: String): Future[String] =
    get[String, NoSerialization](name).flatMap {
      case Some(pokemon) => Future.successful(pokemon)
      case _ =>
        for {
          pokemon <-
            Gigahorse.withHttp(Gigahorse.config) { http =>
              http.run(
                Gigahorse.url(s"http://pokeapi.co/api/v2/pokemon/$name").get,
                Gigahorse.asString
              )
            }
          // _ <- put(name)(pokemon)
        } yield pokemon
    }
}
