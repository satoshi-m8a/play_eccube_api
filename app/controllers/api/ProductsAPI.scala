package controllers.api

import controllers.AuthActionComposition
import models.{Image, Product}
import play.api.libs.json.{Json, Writes}
import play.api.mvc._

import scala.util.control.Exception._

trait ProductsAPI extends Pagination with AuthActionComposition {
  this: Controller =>

  import models.ComponentRegistry._

  object ProductJsonFormatForUser {

    implicit object ProductJsonFormat extends Writes[Product] {
      def writes(product: Product) = {
        Json.obj(
          "id" -> product.id,
          "name" -> product.name,
          "mainComment" -> product.mainComment,
          "mainImage" -> Image(product.mainImage),
          "mainLargeImage" -> Image(product.mainLargeImage),
          "mainListImage" -> Image(product.mainListImage),
          "price01Min" -> product.price01Min,
          "price01Max" -> product.price01Max,
          "price02Min" -> product.price02Min,
          "price02Max" -> product.price02Max
        )
      }
    }

  }

  object ProductJsonFormatForMember {

    implicit object ProductJsonFormat extends Writes[Product] {
      def writes(product: Product) = {
        Json.obj(
          "id" -> product.id,
          "name" -> product.name,
          "mainComment" -> product.mainComment,
          "mainImage" -> Image(product.mainImage),
          "mainLargeImage" -> Image(product.mainLargeImage),
          "mainListImage" -> Image(product.mainListImage),
          "price01Min" -> product.price01Min,
          "price01Max" -> product.price01Max,
          "price02Min" -> product.price02Min,
          "price02Max" -> product.price02Max,
          "stockMin" -> product.stockMin,
          "stockMax" -> product.stockMax
        )
      }
    }

  }

  implicit object PageJsonFormat extends Writes[Page] {
    def writes(page: Page) = {
      Json.obj(
        "total" -> page.itemNum,
        "page" -> page.page,
        "count" -> page.limit,
        "firstPage" -> page.firstPage,
        "lastPage" -> page.lastPage
      )
    }
  }


  def index = Authenticated {
    request =>
      val page = pageRequest(Products.count, request)
      val products = Products.findAll(page.offset, page.limit)

      val productsJson = if (request.member.isDefined) {
        import ProductJsonFormatForMember._
        Json.toJson(products)
      } else {
        import ProductJsonFormatForUser._
        Json.toJson(products)
      }

      Ok(Json.obj(
        "page" -> Json.toJson(page),
        "products" -> productsJson
      ))

  }

  def show(id: String) = Action {
    request =>
      import ProductJsonFormatForUser._
      (for {
        id <- allCatch.opt(id.toLong)
        product <- Products.findById(id)
      } yield {
        Ok(Json.toJson(product))
      }).getOrElse(Forbidden)
  }

}

object ProductsAPI extends ProductsAPI with Controller
