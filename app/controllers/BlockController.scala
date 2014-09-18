package controllers

import play.api.mvc.{ResponseHeader, Result, Action, Controller}

object BlockController extends Controller with ContentsLoader {

  def block(file: String) = Action {
    loadBlock(file).map {
      content =>
        Result(
          header = ResponseHeader(200),
          body = content
        )
    } getOrElse Ok("")
  }

}
