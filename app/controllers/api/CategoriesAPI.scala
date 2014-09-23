package controllers.api

import models.Category
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}

trait CategoriesAPI {
  this: Controller =>

  import models.ComponentRegistry._

  implicit object CategoryJsonFormat extends Writes[Category] {
    def writes(category: Category) = {
      Json.obj(
        "id" -> category.id,
        "name" -> category.name,
        "parentCategoryId" -> category.parentCategoryId
      )
    }
  }

  implicit object CategoryNodeJsonFormat extends Writes[CategoryNode] {
    def writes(categoryNode: CategoryNode) = {
      Json.obj(
        "id" -> categoryNode.category.id,
        "name" -> categoryNode.category.name,
        "parentCategoryId" -> categoryNode.category.parentCategoryId,
        "children" -> Json.toJson(categoryNode.children)
      )
    }
  }

  def index = Action {
    val categories = Categories.findAll

    Ok(Json.toJson(categories))
  }

  def tree = Action {
    Ok(Json.toJson(Categories.tree))
  }


  def show(id: String) = Action {
    request =>
      (for {
        category <- Categories.findById(id)
      } yield {
        Ok(Json.toJson(category))
      }).getOrElse(Forbidden)
  }

}


object CategoriesAPI extends CategoriesAPI with Controller