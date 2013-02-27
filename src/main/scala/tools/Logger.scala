import org.pircbotx.PircBotX
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.{ActionEvent, MessageEvent}

import java.io.FileWriter
import java.util.Date
import util.Log

class Logger(path: String) extends ListenerAdapter[PircBotX] {
  val fileDate = new java.text.SimpleDateFormat("yyyy-MM-dd")
  val logDate = new java.text.SimpleDateFormat("HH:mm:ss")

  override def onMessage(event: MessageEvent[PircBotX]) = {
    logMessage(event.getUser.getNick, event.getMessage)
  }

  override def onAction(event: ActionEvent[PircBotX]) = {
    logMessage(event.getUser.getNick, "***" + event.getUser.getNick + " " + event.getMessage)
  }

  private def logMessage(user: String, message: String) = {
    try {
      val file = new FileWriter(path + "/" + fileDate.format(new Date()) + ".txt", true)

      file.write("%-10s%-15s %s%n".format(
        logDate.format(new Date()),
        user + ":",
        message
      ))

      file.close
    } catch {
      case e: Exception => Log.e("Cannot log message: " + e.toString)
    }
  }
}
