package controllers.api

import play.api.mvc.{AnyContent, Request}

import scala.util.control.Exception._

trait Pagination {
  case class Page(itemNum: Long, page: Option[Long], limit: Option[Long], offset: Option[Long]) {
    def firstPage = 1

    def lastPage = {
      for {
        count <- limit
      } yield {
        val last = itemNum / count
        if (last == 1 && count != itemNum) {
          last + 1
        } else {
          last
        }
      }
    }

  }

  def pageRequest(itemNum: Long, request: Request[AnyContent]) = {
    val limit = for {
      count <- request.getQueryString("count")
      count <- allCatch opt count.toLong
    } yield {
      count
    }

    val page = for {
      limit <- limit
      page <- request.getQueryString("page")
      page <- allCatch opt page.toLong
    } yield {
      page
    }

    val offset = for {
      page <- page
      limit <- limit
    } yield {
      (page - 1) * limit
    }

    Page(itemNum, page, limit, offset)
  }
}
