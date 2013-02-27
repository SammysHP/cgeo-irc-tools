import org.pircbotx.PircBotX
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.DisconnectEvent

import util.Log

class Reconnector(channel: String) extends ListenerAdapter[PircBotX] {
  override def onDisconnect(event: DisconnectEvent[PircBotX]) = {
    if (!Reconnector.disconnectIntended) {
      Log.e("Disconnected. Trying to reconnect…")

      var success = false

      while (!success) {
        try {
          val bot = event.getBot()

          bot.reconnect()
          success = true
          Log.i("Reconnected")

          Log.i("Joining channel " + channel + "…")
          bot.joinChannel(channel)
          Log.i("Joined")
        } catch {
          case e: Exception => {
            Log.e("Reconnect failed, will try again in one minute: " + e.toString)
            Thread.sleep(60*1000)
          }
        }
      }
    }
  }
}

object Reconnector {
  var disconnectIntended = false
}
