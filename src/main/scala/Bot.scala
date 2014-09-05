import java.io.File

import org.ini4j.Ini
import org.pircbotx.{PircBotX, Configuration, Channel}

object Bot extends App {
  val configFile = "config.ini"
  val ini = new Ini(new File(configFile));

  val ircConfig = ini.get("irc")
  val configuration = new Configuration.Builder()
    .setName(ircConfig.get("name"))
    .setLogin(ircConfig.get("name"))
    .setRealName(ircConfig.get("realname"))
    .setAutoNickChange(true)
    .setAutoReconnect(true)
    .setServerHostname(ircConfig.get("server"))
    .addAutoJoinChannel(ircConfig.get("channel"))
    .setEncoding(java.nio.charset.Charset.forName("UTF-8"))
    .buildConfiguration()
  val bot = new PircBotX(configuration)

  val listenerManager = bot.getConfiguration().getListenerManager()
  val logger = Logger(ini.get("logger").get("directory"))
  listenerManager.addListener(IssueLinker(ini.get("issuelinker").get("apiurl"), logger))
  listenerManager.addListener(logger)

  while (true) {
    try {
      bot.startBot()
    } catch {
      case e: Throwable => {
        Thread.sleep(10000)
        println("Error! Restarting...")
      }
    }
  }
}
