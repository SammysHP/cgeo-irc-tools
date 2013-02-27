import org.pircbotx.PircBotX
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.PrivateMessageEvent

import util.Log

object Admin extends ListenerAdapter[PircBotX] {
  type Message = PrivateMessageEvent[PircBotX]

  var ADMIN_PW = "admin"

  override def onPrivateMessage(event: Message) = {
    (event.getMessage().split(" ").toList: @unchecked) match {
      case "admin" :: password :: command => {
        if (ADMIN_PW != password) {
          event.respond("Wrong password!")
          Log.i("Failed authentication")
        } else {
          command match {
            case "shutdown" :: _ => {
              event.respond("Shutting down bot...")
              Log.w("Shutdown requested, shutting down")
              Reconnector.disconnectIntended = true
              event.getBot().shutdown(true)
            }
            case "setpw" :: password :: _ => {
              ADMIN_PW = password
              event.respond("Password set to \"" + password + "\"")
              Log.w("Password set to \"" + password + "\"")
            }
            case _ => event.respond("Unknown command, try 'help'")
          }
        }
      }
    }
  }
}
