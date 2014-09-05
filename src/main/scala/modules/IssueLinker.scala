import org.pircbotx.PircBotX
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.MessageEvent

case class IssueLinker(apiUrl: String, logger: Logger) extends ListenerAdapter[PircBotX] {
  val issueMatcher = """#(\d+)""".r

  override def onMessage(event: MessageEvent[PircBotX]): Unit = {
    val message = event.getMessage.toLowerCase
    val user = event.getUser.getNick

    issueMatcher.findAllIn(event.getMessage).matchData foreach(m => {
      val issueId = m.group(1)
      Issue.findById(issueId.toInt, apiUrl).map( issue => {
        val messageEvent = new MessageEvent(
          event.getBot,
          event.getChannel,
          event.getBot.getUserBot,
          issue.summary
        )
        event.getChannel.send.message(messageEvent.getMessage)
        logger.onMessage(messageEvent)
      })
    })
  }
}
