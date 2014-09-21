package controllers.admin

import play.api.mvc.{Action, Controller}

object AdminApplication extends Controller {

  def adminIndex = Action {
    Ok(views.html.admin.index())
  }

  def all(path: String) = Action {
    Ok(views.html.admin.index())
  }


}
