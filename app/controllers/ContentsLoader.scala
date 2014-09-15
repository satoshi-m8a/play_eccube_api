package controllers

import java.nio.file.Paths

import play.api.Play
import play.api.libs.iteratee.Enumerator
import scala.concurrent.ExecutionContext.Implicits.global

trait ContentsLoader {
  lazy val contentsDir = Play.current.configuration.getString("ec3.contents.dir").get

  val blockDirName = "blocks"
  val pageDirName = "pages"

  def loadBlock(file: String): Enumerator[Array[Byte]] = {
    val filePath = Paths.get(contentsDir, blockDirName, file)
    Enumerator.fromFile(filePath.toFile)
  }

  def loadPage(file: String): Enumerator[Array[Byte]] = {
    val filePath = Paths.get(contentsDir, pageDirName, file)
    Enumerator.fromFile(filePath.toFile)
  }


}
