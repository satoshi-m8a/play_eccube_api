package controllers

import play.api.mvc.{Action, ResponseHeader, Result, Controller}

object PageController extends Controller with ContentsLoader {

  def page(file: String) = Action {
    Result(
      header = ResponseHeader(200),
      body = loadPage(file)
    )
  }

}
