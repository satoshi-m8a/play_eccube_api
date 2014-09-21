package models

import java.nio.file.{Path, Paths}

import controllers.{EC3Host, ContentsPath}
import play.api.libs.json.{Json, Writes}

case class Image(url: String, urlHttp: String, urlHttps: String, path: Path)

object Image extends ContentsPath {
  def apply(path: String): Image = {

    val url = EC3Host.imageURL(path)
    val paths = EC3Host.imagePath(path)

    Image(url.url, url.http, url.https, paths)
  }


  implicit object ImageJsonFormat extends Writes[Image] {
    def writes(image: Image) = {
      Json.obj(
        "url" -> image.url,
        "urlHttp" -> image.urlHttp,
        "urlHttps" -> image.urlHttps,
        "path" -> image.path.toString,
        "name" -> image.path.getFileName.toString
      )
    }
  }

}

