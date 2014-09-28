package models


import java.sql.Timestamp
import java.util.Date

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.Play.current

import scala.util.control.Exception._

case class Category(id: Long, name: String, parentCategoryId: Long, level: Int, rank: Int) {

  import ComponentRegistry._

  def findChildren(list: Seq[Category]): Seq[Category] = {
    list.filter(_.parentCategoryId == id)
  }

  def ancestors: Seq[Category] = {
    def trace(id: Long): Seq[Category] = {
      Categories.findById(id) match {
        case Some(category) => trace(category.parentCategoryId) :+ category
        case _ => Seq.empty[Category]
      }
    }
    trace(parentCategoryId) :+ this
  }

  def breadcrumbsString: String = {
    ancestors.map(_.name).mkString(">")
  }

}

object Category {
  val parser: RowParser[Category] = {
    get[Long]("category_id") ~
      get[String]("category_name") ~
      get[Long]("parent_category_id") ~
      get[Int]("level") ~
      get[Int]("rank") map {
      case id ~ name ~ parentCategoryId ~ level ~ rank => Category(id, name, parentCategoryId, level, rank)
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
              |WHERE del_flg = 0
              |ORDER BY rank ASC
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
              |AND del_flg = 0
            """.stripMargin).on(
              "category_id" -> id
            ).as(Category.parser.singleOpt)
      }
    }

    def findByParentId(parentCategoryId: Long): Seq[Category] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT * FROM dtb_category
              |WHERE parent_category_id = {parent_category_id}
              |AND del_flg = 0
              |ORDER BY rank ASC
            """.stripMargin).on(
              "parent_category_id" -> parentCategoryId
            ).as(Category.parser.*)
      }
    }

    /**
     * Only for postgresql
     * @return
     */
    private def nextId(): Option[Long] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT NEXTVAL('dtb_category_category_id_seq')
            """.stripMargin).as(scalar[Long].singleOpt)
      }
    }

    def add(name: String, parentCategoryId: Long, level: Int, rank: Int, creatorId: Long): Option[Long] = {
      DB.withConnection {
        implicit c =>
          val timeStamp = new Timestamp(new Date().getTime)
          val params = Seq[NamedParameter](
            "category_id" -> nextId,
            "category_name" -> name,
            "parent_category_id" -> parentCategoryId,
            "level" -> level,
            "rank" -> rank,
            "creator_id" -> creatorId,
            "create_date" -> timeStamp,
            "update_date" -> timeStamp
          )

          SQL(
            """
              |INSERT INTO dtb_category(category_id,category_name,parent_category_id,level,rank,creator_id,create_date,update_date)
              |VALUES ({category_id},{category_name},{parent_category_id},{level},{rank},{creator_id},{create_date},{update_date})
            """.stripMargin).on(params: _*).executeInsert(scalar[Long].singleOpt)
      }
    }

    def swapRank(sourceCategory: Category, targetCategory: Category): Option[Category] = {
      DB.withTransaction {
        implicit c =>
          val timeStamp = new Timestamp(new Date().getTime)

          val params1 = Seq[NamedParameter](
            "rank" -> targetCategory.rank,
            "category_id" -> sourceCategory.id,
            "update_date" -> timeStamp
          )
          val params2 = Seq[NamedParameter](
            "rank" -> sourceCategory.rank,
            "category_id" -> targetCategory.id,
            "update_date" -> timeStamp
          )
          SQL("UPDATE dtb_category SET rank = {rank}, update_date = {update_date} WHERE category_id = {category_id}")
            .on(params1: _*).executeUpdate()
          SQL("UPDATE dtb_category SET rank = {rank}, update_date = {update_date} WHERE category_id = {category_id}")
            .on(params2: _*).executeUpdate()

          Some(sourceCategory.copy(rank = targetCategory.rank))
      }
    }

    def delete(id: Long): Option[Long] = {
      DB.withConnection {
        implicit c =>
          val params = Seq[NamedParameter](
            "category_id" -> id,
            "update_date" -> new Timestamp(new Date().getTime)
          )

          SQL(
            """
              |UPDATE dtb_category SET del_flg = 1, update_date = {update_date} WHERE category_id = {category_id}
            """.stripMargin).on(params: _*).executeUpdate()
          Some(id)
      }
    }

    def updateName(id: Long, name: String): Option[Long] = {
      DB.withConnection {
        implicit c =>
          val params = Seq[NamedParameter](
            "category_name" -> name,
            "category_id" -> id,
            "update_date" -> new Timestamp(new Date().getTime)
          )

          SQL(
            """
              |UPDATE dtb_category SET category_name = {category_name}, update_date = {update_date} WHERE category_id = {category_id}
            """.stripMargin).on(params: _*).executeUpdate()
          Some(id)
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
          case Some(category) =>
            buildTree(categories.tail) :+ CategoryNode(category, buildTree(category.findChildren(all)))
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

    def findChildren(id: Long): Seq[Category] = {
      categoryRepository.findByParentId(id)
    }

    def rankUp(id: String): Option[Category] = {
      for {
        category <- findById(id)
        above <- findChildren(category.parentCategoryId).find(_.rank > category.rank)
        category <- categoryRepository.swapRank(category, above)
      } yield {
        category
      }
    }

    def rankDown(id: String): Option[Category] = {
      for {
        category <- findById(id)
        below <- findChildren(category.parentCategoryId).reverse.find(_.rank < category.rank)
        category <- categoryRepository.swapRank(category, below)
      } yield {
        category
      }
    }

    def add(name: String, parentCategoryId: Long, creatorId: Long): Option[Category] = {
      val (level, parentId) = findById(parentCategoryId) match {
        case Some(parent) => (parent.level + 1, parent.id)
        case _ => (1, 0L)
      }

      val rank = allCatch.opt(findChildren(parentId).maxBy(_.rank).rank).getOrElse(0) + 1

      for {
        id <- categoryRepository.add(name, parentCategoryId, level, rank, creatorId)
      } yield {
        Category(id, name, parentCategoryId, level, rank)
      }
    }

    def deleteNode(id: Long): Option[Long] = {
      categoryRepository.findByParentId(id) match {
        case Nil => categoryRepository.delete(id)
        case list => {
          list.foreach(i => deleteNode(i.id))
          Some(id)
        }
      }
    }

    def hasSameName(parentCategoryId: Long, name: String): Boolean = {
      val children = findChildren(parentCategoryId)
      children.exists(_.name == name)
    }

    def updateName(id: Long, name: String): Option[Long] = {
      categoryRepository.updateName(id, name)
    }

  }

}
