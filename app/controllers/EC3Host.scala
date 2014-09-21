package controllers

import java.nio.file.{Path, Paths}

import play.api.Play

object EC3Host extends ContentsPath {
  lazy val host = Play.current.configuration.getString("ec3.host").get

  val protocol = "http://"

  val imageURL: String = {
    protocol + host + "/" + imageDirName + "/"
  }

  case class URL(url: String, http: String, https: String)

  def url(dir: String, path: String) = {
    val url = "//" + host + "/" + dir + "/" + path
    val urlHttp = "http://" + host + "/" + dir + "/" + path
    val urlHttps = "https://" + host + "/" + dir + "/" + path

    URL(url, urlHttp, urlHttps)
  }

  def imageURL(path: String): URL = {
    url(imageDirName, path)
  }

  def imagePath(path: String): Path = {
    Paths.get(imageDirName, path)
  }

}
