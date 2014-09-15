package controllers

import play.api.mvc.{ResponseHeader, Result, Action, Controller}

object BlockController extends Controller with ContentsLoader {

  def block(file: String) = Action {
    Result(
      header = ResponseHeader(200),
      body = loadBlock(file)
    )
  }

}
