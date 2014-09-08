package controllers.api

import models.Product
import play.api.libs.json.{Json, Writes}
import play.api.mvc._

import scala.util.control.Exception._

trait ProductsAPI extends Pagination {
  this: Controller =>

  import models.ComponentRegistry._

  implicit object ProductJsonFormat extends Writes[Product] {
    def writes(product: Product) = {
      Json.obj(
        "id" -> product.id,
        "name" -> product.name,
        "mainComment" -> product.mainComment,
        "mainImage" -> product.mainImage
      )
    }
  }

  case class ProductsPage(products: Seq[Product], page: Page)

  implicit object ProductPageJsonFormat extends Writes[ProductsPage] {

    def writes(productPage: ProductsPage) = {
      Json.obj(
        "total" -> productPage.page.itemNum,
        "page" -> productPage.page.page,
        "count" -> productPage.page.limit,
        "firstPage" -> productPage.page.firstPage,
        "lastPage" -> productPage.page.lastPage,
        "products" -> Json.toJson(productPage.products)
      )
    }
  }


  def index = Action {
    request =>
      val page = pageRequest(Products.count, request)
      val products = Products.findAll(page.offset, page.limit)
      Ok(Json.toJson(ProductsPage(products, page)))
  }

  def show(id: String) = Action {
    request =>
      (for {
        id <- allCatch.opt(id.toLong)
        product <- Products.findById(id)
      } yield {
        Ok(Json.toJson(product))
      }).getOrElse(Forbidden)
  }

}

object ProductsAPI extends ProductsAPI with Controller
