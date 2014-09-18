package controllers


import play.api.Logger
import play.api.mvc.{Action, ResponseHeader, Result, Controller}

object PageController extends Controller with ContentsLoader {

  def page(file: String) = Action {
    loadPage(file).map {
      content =>
        Result(
          header = ResponseHeader(200),
          body = content
        )
    } getOrElse {
      Logger.info(s"page not found: $file")
//      val template = loadTemplateFile("404.html")
//      Ok(views.html.index(template))

      loadPage("404.html").map {
        content =>
          Result(
            header = ResponseHeader(200),
            body = content
          )
      } getOrElse NotFound
    }
  }

}
