package controllers.api

import controllers.AuthActionComposition
import controllers.search.Application._
import models.{Image, Product}
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import services.ElasticsearchServer

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

  def preSearch = Action {
    request =>
      val hits = (for {
        query <- request.getQueryString("query")
      } yield {
        val response = ElasticsearchServer.search(query)
        response.getHits.getTotalHits
      }).getOrElse(0L)

      Ok(Json.toJson(Map("hits" -> hits)))
  }

  def search = Action {
    request =>
      (for {
        query <- request.getQueryString("query")
      } yield {
        val response = ElasticsearchServer.search(query)

        import collection.JavaConversions._

        val products = response.getHits.getHits.map(hit => {
          val src = hit.getSource.toMap
          Product(
            src.getOrElse("product_id", 0L).asInstanceOf[Int].toLong,
            src.getOrElse("name", "").asInstanceOf[String],
            src.getOrElse("main_comment", "").asInstanceOf[String],
            src.getOrElse("main_image", "").asInstanceOf[String],
            src.getOrElse("main_large_image", "").asInstanceOf[String],
            src.getOrElse("main_list_image", "").asInstanceOf[String],
            allCatch.opt(Integer.parseInt(src.getOrElse("price01_min", "").asInstanceOf[String]).toLong),
            allCatch.opt(Integer.parseInt(src.getOrElse("price01_max", "").asInstanceOf[String]).toLong),
            allCatch.opt(Integer.parseInt(src.getOrElse("price02_min", "").asInstanceOf[String]).toLong),
            allCatch.opt(Integer.parseInt(src.getOrElse("price02_max", "").asInstanceOf[String]).toLong),
            allCatch.opt(Integer.parseInt(src.getOrElse("stock_min", "").asInstanceOf[String]).toLong),
            allCatch.opt(Integer.parseInt(src.getOrElse("stock_max", "").asInstanceOf[String]).toLong)
          )
        })

        import ProductJsonFormatForUser._
        Ok(Json.toJson(products))
      }).getOrElse(Forbidden)
  }

}

object ProductsAPI extends ProductsAPI with Controller
