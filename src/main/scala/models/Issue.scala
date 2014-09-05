import scala.io.Source
import scala.util.parsing.json._

case class Issue(id: Int, title: String, url: String, state: String) {
  def summary: String = "#" + id + " [" + state + "] " + title + " <" + url + ">"
}

object Issue {
  def findById(id: Int, apiUrl: String): Option[Issue] = {
    val apiResponse = Source.fromURL(apiUrl + id).mkString

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
