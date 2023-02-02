package util

import scala.io.Source.fromFile

object FileUtil {

  def loadGraphqlResource(path: String): String = {
    val query = path
    val lines = fromFile(query).getLines().mkString("")
    lines
  }

  def loadGraphqlVariables(path: String): String = {
    val variables = path
    val source = fromFile(variables)
    val lines = try source.getLines() mkString "\n" finally source.close()
    lines
  }

}
