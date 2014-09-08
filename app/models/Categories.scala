package models


import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.Play.current

import scala.util.control.Exception._

case class Category(id: Long, name: String, parentCategoryId: Long)

object Category {
  val parser: RowParser[Category] = {
    get[Long]("category_id") ~
      get[String]("category_name") ~
      get[Long]("parent_category_id") map {
      case id ~ name ~ parentCategoryId => Category(id, name, parentCategoryId)
    }
  }
}


trait CategoryRepositoryComponent {
  val categoryRepository: CategoryRepository

  class CategoryRepository {
    def findAll: Seq[Category] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT * FROM dtb_category
            """.stripMargin).as(Category.parser.*)
      }
    }

    def findById(id: Long): Option[Category] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT * FROM dtb_category
              |WHERE category_id = {category_id}
            """.stripMargin).on(
              "category_id" -> id
            ).as(Category.parser.singleOpt)
      }
    }
  }

}

trait CategoryServiceComponent {
  this: CategoryRepositoryComponent =>

  class CategoryService {
    def findAll: Seq[Category] = {
      categoryRepository.findAll
    }

    def findById(id: String): Option[Category] = {
      for {
        id <- allCatch.opt(id.toLong)
        category <- categoryRepository.findById(id)
      } yield {
        category
      }
    }

    def findById(id: Long): Option[Category] = {
      categoryRepository.findById(id)
    }

  }

}
