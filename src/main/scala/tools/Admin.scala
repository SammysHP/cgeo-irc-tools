import org.pircbotx.PircBotX
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.PrivateMessageEvent

object Admin extends ListenerAdapter[PircBotX] {
  type Message = PrivateMessageEvent[PircBotX]

  val ADMIN_PW = "dothatshit"

  override def onPrivateMessage(event: Message) = {
    (event.getMessage().split(" ").toList: @unchecked) match {
      case "admin" :: ADMIN_PW :: command => {
        (command: @unchecked) match {
          case "disconnect" :: _ => {
            event.respond("Disconnect and exit")
            println("INFO: Request disconnect")
            // event.getBot().disconnect()
            // System.exit(0)
          }
        }
      }
    }
  }
}
