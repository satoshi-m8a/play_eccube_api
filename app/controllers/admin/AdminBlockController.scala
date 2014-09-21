package controllers.admin

import play.api.mvc.{Action, Controller}

object AdminBlockController extends Controller {

  def header = Action {
    Ok(views.html.admin.blocks.header())
  }

  def footer = Action {
    Ok(views.html.admin.blocks.footer())
  }

  def productList = Action {
    Ok(views.html.admin.blocks.product.list())
  }

}
