package models

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.Play.current

case class Product(id: Long, name: String, mainComment: String, mainImage: String)

object Product {
  val parser: RowParser[Product] = {
    get[Long]("product_id") ~
      get[String]("name") ~
      get[String]("main_comment") ~
      get[String]("main_image") map {
      case id ~ name ~ mainComment ~ mainImage => Product(id, name, mainComment, mainImage)
    }
  }
}

trait ProductRepositoryComponent {
  val productRepository: ProductRepository

  class ProductRepository {

    def count: Long = {
      DB.withConnection {
        implicit c =>
          SQL("SELECT COUNT(*) FROM dtb_products").as(scalar[Long].single)
      }
    }

    val selectProductsQuery =
      """
        |SELECT
        |  dtb_products.product_id
        |  ,dtb_products.name
        |  ,dtb_products.maker_id
        |  ,dtb_products.status
        |  ,dtb_products.comment1
        |  ,dtb_products.comment2
        |  ,dtb_products.comment3
        |  ,dtb_products.comment4
        |  ,dtb_products.comment5
        |  ,dtb_products.comment6
        |  ,dtb_products.note
        |  ,dtb_products.main_list_comment
        |  ,dtb_products.main_list_image
        |  ,dtb_products.main_comment
        |  ,dtb_products.main_image
        |  ,dtb_products.main_large_image
        |  ,dtb_products.sub_title1
        |  ,dtb_products.sub_comment1
        |  ,dtb_products.sub_image1
        |  ,dtb_products.sub_large_image1
        |  ,dtb_products.sub_title2
        |  ,dtb_products.sub_comment2
        |  ,dtb_products.sub_image2
        |  ,dtb_products.sub_large_image2
        |  ,dtb_products.sub_title3
        |  ,dtb_products.sub_comment3
        |  ,dtb_products.sub_image3
        |  ,dtb_products.sub_large_image3
        |  ,dtb_products.sub_title4
        |  ,dtb_products.sub_comment4
        |  ,dtb_products.sub_image4
        |  ,dtb_products.sub_large_image4
        |  ,dtb_products.sub_title5
        |  ,dtb_products.sub_comment5
        |  ,dtb_products.sub_image5
        |  ,dtb_products.sub_large_image5
        |  ,dtb_products.sub_title6
        |  ,dtb_products.sub_comment6
        |  ,dtb_products.sub_image6
        |  ,dtb_products.sub_large_image6
        |  ,dtb_products.del_flg
        |  ,dtb_products.creator_id
        |  ,dtb_products.create_date
        |  ,dtb_products.update_date
        |  ,dtb_products.deliv_date_id
        |  ,T4.product_code_min
        |  ,T4.product_code_max
        |  ,T4.price01_min
        |  ,T4.price01_max
        |  ,T4.price02_min
        |  ,T4.price02_max
        |  ,T4.stock_min
        |  ,T4.stock_max
        |  ,T4.stock_unlimited_min
        |  ,T4.stock_unlimited_max
        |  ,T4.point_rate
        |  ,T4.deliv_fee
        |  ,T4.class_count
        |  ,dtb_maker.name AS maker_name
        |FROM dtb_products
        |  JOIN (
        |         SELECT product_id,
        |           MIN(product_code) AS product_code_min,
        |           MAX(product_code) AS product_code_max,
        |           MIN(price01) AS price01_min,
        |           MAX(price01) AS price01_max,
        |           MIN(price02) AS price02_min,
        |           MAX(price02) AS price02_max,
        |           MIN(stock) AS stock_min,
        |           MAX(stock) AS stock_max,
        |           MIN(stock_unlimited) AS stock_unlimited_min,
        |           MAX(stock_unlimited) AS stock_unlimited_max,
        |           MAX(point_rate) AS point_rate,
        |           MAX(deliv_fee) AS deliv_fee,
        |           COUNT(*) as class_count
        |         FROM dtb_products_class
        |         GROUP BY product_id
        |       ) AS T4
        |    ON dtb_products.product_id = T4.product_id
        |  LEFT JOIN dtb_maker
        |    ON dtb_products.maker_id = dtb_maker.maker_id
      """.stripMargin

    def findAll(offset: Option[Long] = None, limit: Option[Long] = None): Seq[Product] = {
      DB.withConnection {
        implicit c =>
          val offsetCondition = offset.fold("")(o => s"OFFSET $o")
          val limitCondition = limit.fold("")(l => s"LIMIT $l")

          SQL(
            """
              |%s
              |%s %s
            """.stripMargin.format(selectProductsQuery, offsetCondition, limitCondition)).as(Product.parser.*)
      }
    }

    def findById(id: Long): Option[Product] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |%s
              |WHERE dtb_products.product_id = {product_id}
            """.stripMargin.format(selectProductsQuery)).on(
              "product_id" -> id
            ).as(Product.parser.singleOpt)
      }
    }

  }

}

trait ProductServiceComponent {
  this: ProductRepositoryComponent =>

  class ProductService {
    def count: Long = {
      productRepository.count
    }

    def findAll(offset: Option[Long] = None, limit: Option[Long] = None): Seq[Product] = {
      productRepository.findAll(offset, limit)
    }

    def findById(id: Long): Option[Product] = {
      productRepository.findById(id)
    }

  }

}