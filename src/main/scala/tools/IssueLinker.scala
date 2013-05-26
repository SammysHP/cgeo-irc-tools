import scala.collection.mutable.Map

import org.pircbotx.PircBotX
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.MessageEvent

import models._
import util.Log

object IssueLinker extends ListenerAdapter[PircBotX] {
  type Message = MessageEvent[PircBotX]

  val issueMatcher = """#(\d+)""".r

  override def onMessage(event: Message): Unit = {
    if (event.getUser.getNick == "cgeo-ci") return

    (issueMatcher findAllIn event.getMessage()).matchData foreach(m => {
      val issueId = m.group(1)
      Issue.findById(issueId.toInt).map( issue => {
        event.getBot().sendMessage(event.getChannel(), issue.summary)
        Log.d("Posted issue " + issue.summary)
      })
    })
  }
}
