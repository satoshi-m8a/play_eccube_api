package controllers.admin

import controllers.AuthActionComposition
import play.api.mvc.{Action, Controller}

object AdminPageController extends Controller with AuthActionComposition {

  def login = Action {
    Ok(views.html.admin.login())
  }

  def dashboard = Authenticated {
    request =>
      Ok(views.html.admin.dashboard())
  }

  def productsIndex = Authenticated {
    request =>
      Ok(views.html.admin.pages.products.index())
  }

  def productRegistration = Authenticated {
    request =>
      Ok(views.html.admin.pages.products.product())
  }

  def categoryRegistration = Authenticated {
    request =>
      Ok(views.html.admin.pages.products.catagory())
  }

}
