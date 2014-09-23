package controllers.api

import play.api.mvc.{Action, Controller}

trait MembersAPI {
  this: Controller =>

  import models.ComponentRegistry._

  def login = Action {
    request =>
      (for {
        json <- request.body.asJson
        loginId <- (json \ "loginId").asOpt[String]
        password <- (json \ "password").asOpt[String]
        member <- Members.authenticate(loginId, password)
      } yield {
        Ok("").withSession("mid" -> member.id.toString)
      }).getOrElse(Forbidden)
  }

}

object MembersAPI extends MembersAPI with Controller