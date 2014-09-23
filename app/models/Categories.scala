package models


import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.Play.current

import scala.util.control.Exception._

case class Category(id: Long, name: String, parentCategoryId: Long) {
  def findChildren(list: Seq[Category]): Seq[Category] = {
    list.filter(_.parentCategoryId == id)
  }
}

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

  case class CategoryNode(category: Category, children: Seq[CategoryNode])

  class CategoryService {
    def findAll: Seq[Category] = {
      categoryRepository.findAll
    }

    def tree: Seq[CategoryNode] = {

      val all = categoryRepository.findAll

      def buildTree(categories: Seq[Category]): Seq[CategoryNode] = {
        categories.headOption match {
          case Some(category) => {
            buildTree(categories.tail) :+ CategoryNode(category, buildTree(category.findChildren(all)))
          }
          case _ => Seq.empty[CategoryNode]
        }
      }

      buildTree(all.filter(_.parentCategoryId == 0))
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
