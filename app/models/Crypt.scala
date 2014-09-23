package models

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

trait Crypt {

  //TODO マジカル!!(configから読み出す)
  val AUTH_MAGIC = "jaesliogiowraifrouveacliocaehegateasliaw"
  //TODO sha256-> HmacSHA256
  val PASSWORD_HASH_ALGOS = "HmacSHA256"

  object Hex {
    def valueOf(buf: Array[Byte]): String = buf.map("%02X" format _).mkString.toLowerCase
  }

  def hash(plain: String, salt: String): Option[String] = {
    val input = plain + ":" + AUTH_MAGIC

    val sk = new SecretKeySpec(salt.getBytes, PASSWORD_HASH_ALGOS)
    val mac = Mac.getInstance(sk.getAlgorithm)
    mac.init(sk)
    val bytes = mac.doFinal(input.getBytes)
    Option(bytes).map(Hex.valueOf)
  }

  def checkPassword(plain: String, salt: String, passwordHash: String): Boolean = {
    (for {
      hashed <- hash(plain, salt)
      if hashed == passwordHash
    } yield {
      true
    }).getOrElse(false)
  }

}
