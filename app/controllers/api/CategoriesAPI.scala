package controllers.api

import controllers.AuthActionComposition
import models.Category
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}

trait CategoriesAPI extends AuthActionComposition {
  this: Controller =>

  import models.ComponentRegistry._

  implicit object CategoryJsonFormat extends Writes[Category] {
    def writes(category: Category) = {
      Json.obj(
        "id" -> category.id,
        "name" -> category.name,
        "parentCategoryId" -> category.parentCategoryId,
        "level" -> category.level,
        "rank" -> category.rank,
        "breadcrumbs" -> category.breadcrumbsString
      )
    }
  }

  implicit object CategoryNodeJsonFormat extends Writes[CategoryNode] {
    def writes(categoryNode: CategoryNode) = {
      Json.obj(
        "id" -> categoryNode.category.id,
        "name" -> categoryNode.category.name,
        "parentCategoryId" -> categoryNode.category.parentCategoryId,
        "level" -> categoryNode.category.level,
        "rank" -> categoryNode.category.rank,
        "children" -> Json.toJson(categoryNode.children)
      )
    }
  }

  def index = Action {
    val categories = Categories.findAll.reverse
    Ok(Json.toJson(categories.sortBy(_.level)))
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

  def delete(id: String) = Action {
    request =>
      (for {
        category <- Categories.findById(id)
        deletedId <- Categories.deleteNode(category.id)
      } yield {
        Ok(Json.toJson(category))
      }).getOrElse(Forbidden)
  }

  def update(id: String) = Action {
    request =>
      (for {
        json <- request.body.asJson
        name <- (json \ "name").asOpt[String]
        category <- Categories.findById(id)
        if !Categories.hasSameName(category.parentCategoryId, name)
        updateId <- Categories.updateName(category.id, name)
      } yield {
        Ok(Json.toJson(category.copy(name = name)))
      }).getOrElse(Forbidden)
  }

  def up(id: String) = Authenticated {
    request =>
      (for {
        category <- Categories.rankUp(id)
      } yield {
        Ok(Json.toJson(category))
      }).getOrElse(Forbidden)
  }

  def down(id: String) = Authenticated {
    request =>
      (for {
        category <- Categories.rankDown(id)
      } yield {
        Ok(Json.toJson(category))
      }).getOrElse(Forbidden)
  }

  def create = Authenticated {
    request =>
      (for {
        json <- request.body.asJson
        name <- (json \ "name").asOpt[String]
        parentCategoryId <- (json \ "parentCategoryId").asOpt[Long]
        member <- request.member
        category <- Categories.add(name, parentCategoryId, member.id)
      } yield {
        Ok(Json.toJson(category))
      }).getOrElse(Forbidden)
  }

}


object CategoriesAPI extends CategoriesAPI with Controller