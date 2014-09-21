package controllers.admin

import play.api.mvc.{Action, Controller}

object AdminPageController extends Controller {

  def login = Action {
    Ok(views.html.admin.login())
  }

  def dashboard = Action {
    Ok(views.html.admin.dashboard())
  }

  def productsIndex = Action {
    Ok(views.html.admin.pages.products.index())
  }
}
