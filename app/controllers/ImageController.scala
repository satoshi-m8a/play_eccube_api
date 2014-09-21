package controllers

import play.api.mvc.{Action, ResponseHeader, Result, Controller}

object ImageController extends Controller with ContentsLoader {

  def image(file: String) = Action {
    loadImage(file).map {
      content =>
        Result(
          header = ResponseHeader(200),
          body = content
        )
    } getOrElse NotFound
  }

}



