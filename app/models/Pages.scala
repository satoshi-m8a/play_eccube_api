package models

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import scala.util.control.Exception._
import play.api.Play.current

case class Page(id: Long, pageName: String, fileName: String, hasHeader: Boolean, hasFooter: Boolean)

object Page {
  val parser: RowParser[Page] = {
    get[Long]("page_id") ~
      get[String]("page_name") ~
      get[String]("filename") ~
      get[Int]("header_chk") ~
      get[Int]("footer_chk") map {
      case id ~ pageName ~ fileName ~ headerChk ~ footerChk => {

        //TODO implicitly convert 0/1 to false/true
        val hasHeader = headerChk match {
          case 0 =>
            false
          case 1 =>
            true
          case _ =>
            false
        }

        //TODO implicitly convert 0/1 to false/true
        val hasFooter = footerChk match {
          case 0 =>
            false
          case 1 =>
            true
          case _ =>
            false
        }

        Page(id, pageName, fileName, hasHeader, hasFooter)
      }
    }
  }
}

trait PageRepositoryComponent {
  val pageRepository: PageRepository

  class PageRepository {
    def findAll: Seq[Page] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT * FROM dtb_pagelayout
            """.stripMargin).as(Page.parser.*)
      }
    }

    def findById(id: Long): Option[Page] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT * FROM dtb_pagelayout
              |WHERE page_id = {page_id}
            """.stripMargin).on(
              "page_id" -> id
            ).as(Page.parser.singleOpt)
      }
    }

    def findByFileName(fileName: String): Option[Page] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT * FROM dtb_pagelayout
              |WHERE filename = {filename}
            """.stripMargin).on(
              "filename" -> fileName
            ).as(Page.parser.*).headOption
      }
    }
  }

}

trait PageServiceComponent {
  this: PageRepositoryComponent =>

  class PageService {
    def findAll: Seq[Page] = {
      pageRepository.findAll
    }

    def findById(id: String): Option[Page] = {
      for {
        id <- allCatch.opt(id.toLong)
        category <- pageRepository.findById(id)
      } yield {
        category
      }
    }

    def findById(id: Long): Option[Page] = {
      pageRepository.findById(id)
    }

    def findByFileName(fileName: String): Option[Page] = {
      pageRepository.findByFileName(fileName)
    }
  }

}