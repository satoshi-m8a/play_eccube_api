package controllers

import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def all(path: String) = Action {
    Ok(views.html.index())
  }

  def unTrail(path: String) = Action {
    MovedPermanently("/" + path)
  }

}