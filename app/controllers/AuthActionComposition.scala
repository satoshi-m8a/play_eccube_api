package controllers

import models.Member
import play.api.mvc._

import scala.concurrent.Future
import scala.util.control.Exception._

trait AuthActionComposition {
  this: Controller =>

  import models.ComponentRegistry._

  case class AuthenticatedRequest(user: Option[Long], member: Option[Member], request: Request[AnyContent]) extends WrappedRequest(request)

  object Authenticated {

    def mid[A](request: Request[A]): Option[String] = {
      request.session.get("mid")
    }

    def member[A](request: Request[A]): Option[Member] = {
      for {
        aid <- mid(request)
        id <- allCatch.opt(aid.toLong)
        member <- Members.findById(id)
      } yield {
        member
      }
    }

    def apply(f: AuthenticatedRequest => Result) = {
      Action {
        request =>
          f(AuthenticatedRequest(None, member(request), request))
      }
    }

    def async(f: AuthenticatedRequest => Future[Result]) = {
      Action.async {
        request =>
          f(AuthenticatedRequest(None, member(request), request))
      }
    }

  }

}
