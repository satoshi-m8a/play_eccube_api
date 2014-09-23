package models

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.Play.current

case class Member(id: Long, name: String, loginId: String, password: String, salt: String)

object Member {
  val parser: RowParser[Member] = {
    get[Long]("member_id") ~
      get[String]("name") ~
      get[String]("login_id") ~
      get[String]("password") ~
      get[String]("salt") map {
      case id ~ name ~ loginId ~ password ~ salt => Member(id, name, loginId, password, salt)
    }
  }
}

trait MemberRepositoryComponent {
  val memberRepository: MemberRepository

  class MemberRepository {

    def findByLoginId(loginId: String): Option[Member] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT * FROM dtb_member
              |WHERE login_id = {login_id}
            """.stripMargin).on(
              "login_id" -> loginId
            ).as(Member.parser.singleOpt)
      }
    }

    def findById(id: Long): Option[Member] = {
      DB.withConnection {
        implicit c =>
          SQL(
            """
              |SELECT * FROM dtb_member
              |WHERE member_id = {member_id}
            """.stripMargin).on(
              "member_id" -> id
            ).as(Member.parser.singleOpt)
      }
    }

  }

}

trait MemberServiceComponent extends Crypt {
  this: MemberRepositoryComponent =>

  class MemberService {
    def authenticate(loginId: String, password: String): Option[Member] = {
      for {
        member <- memberRepository.findByLoginId(loginId)
        if checkPassword(password, member.salt, member.password)
      } yield {
        member
      }
    }

    def findById(id: Long): Option[Member] = {
      memberRepository.findById(id)
    }

  }

}