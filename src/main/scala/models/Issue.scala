package models

import scala.io.Source
import scala.util.parsing.json._

case class Issue(id: Int, title: String, url: String, state: String) {
  def summary: String = "#" + id + " [" + state + "] " + title + " <" + url + ">"
}

object Issue {
  val ISSUE_URL = "https://github.com/cgeo/c-geo-opensource/issues/"
  val API_URL = "https://api.github.com/repos/cgeo/c-geo-opensource/issues/"

  def findById(id: Int): Option[Issue] = {
    val apiResponse = Source.fromURL(API_URL + id).mkString

    JSON.parseFull(apiResponse) match {
      case Some(data: Map[String,Any] @unchecked) => Some(Issue(
        id,
        data("title").toString,
        data("html_url").toString,
        data("state").toString
      ))
      case _ => None
    }
  }
}
