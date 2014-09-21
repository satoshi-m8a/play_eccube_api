package controllers

import java.io.File
import java.nio.file.Paths

import play.api.Play
import play.api.libs.iteratee.Enumerator
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

trait ContentsPath {
  lazy val contentsDir = Play.current.configuration.getString("ec3.contents.dir").get

  protected val blockDirName = "blocks"
  protected val pageDirName = "pages"
  protected val imageDirName = "images"
}

trait ContentsLoader extends ContentsPath {

  def loadBlock(file: String): Option[Enumerator[Array[Byte]]] = {
    loadFile(Paths.get(contentsDir, blockDirName, file).toFile)
  }

  def loadPage(file: String): Option[Enumerator[Array[Byte]]] = {
    loadFile(Paths.get(contentsDir, pageDirName, file).toFile)
  }

  def loadImage(file: String): Option[Enumerator[Array[Byte]]] = {
    loadFile(Paths.get(contentsDir, imageDirName, file).toFile)
  }

  private def loadFile(file: File): Option[Enumerator[Array[Byte]]] = {
    if (file.exists) {
      Some(Enumerator.fromFile(file))
    } else {
      None
    }
  }


}
