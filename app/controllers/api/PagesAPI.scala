package controllers.api

import models.Page
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}

trait PagesAPI {
  this: Controller =>

  import models.ComponentRegistry._

  implicit object PageJsonFormat extends Writes[Page] {
    def writes(page: Page) = {
      Json.obj(
        "id" -> page.id,
        "name" -> page.pageName,
        "fileName" -> page.fileName,
        "hasHeader" -> page.hasHeader,
        "hasFooter" -> page.hasFooter
      )
    }
  }

  def showFile(fileName: String) = Action {
    Pages.findByFileName(fileName).map {
      page =>
        Ok(Json.toJson(page))
    } getOrElse {
      Ok(Json.toJson(Pages.findByFileName(fileName + "/index")))
    }
  }

}


object PagesAPI extends PagesAPI with Controller
