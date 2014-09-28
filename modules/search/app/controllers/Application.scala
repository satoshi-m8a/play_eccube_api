package controllers.search

import play.api.mvc._
import services.ElasticsearchServer

object Application extends Controller {

  def search = Action {
    request =>
      (for {
        query <- request.getQueryString("query")
      } yield {
        val response = ElasticsearchServer.search(query)
        Ok(response.toString).as("application/json")
      }).getOrElse(Forbidden)

  }
}