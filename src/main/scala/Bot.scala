import org.pircbotx.PircBotX

import util.Log

object Bot extends App {
  val DEFAULT_NAME = "cgeo-irct"
  val DEFAULT_SERVER = "irc.freenode.net"
  val DEFAULT_CHANNEL = "#cgeo"
  val DEFAULT_LEVEL = 0

  def parseOptions(args: List[String], options: Map[String, String]): Map[String, String] = {
    args match {
      case Nil => options
      case "-s" :: server :: tail => parseOptions(tail, options + ("server" -> server))
      case "-c" :: channel :: tail => parseOptions(tail, options + ("channel" -> channel))
      case "-n" :: name :: tail => parseOptions(tail, options + ("name" -> name))
      case "-v" :: level :: tail => parseOptions(tail, options + ("level" -> level))
      case "-l" :: path :: tail => parseOptions(tail, options + ("logpath" -> path))
      case _ :: tail => parseOptions(tail, options)
    }
  }

  val options = parseOptions(args.toList, Map())

  try {
    Log.logLevel = options.getOrElse("level", DEFAULT_LEVEL).toString.toInt

    val bot = new PircBotX

    val server = options.getOrElse("server", DEFAULT_SERVER)
    val channel = options.getOrElse("channel", DEFAULT_CHANNEL)

    bot.getListenerManager().addListener(IssueLinker)
    bot.getListenerManager().addListener(Admin)
    bot.getListenerManager().addListener(Help)
    options.get("logpath").foreach (path => {
      bot.getListenerManager().addListener(new Logger(path))
    })

    bot.setName(options.getOrElse("name", DEFAULT_NAME))
    bot.setLogin("cgeo-irct")
    bot.setAutoNickChange(true)

    Log.i("Connecting to " + server + "…")
    bot.connect(server)
    Log.i("Connected")

    Log.i("Joining channel " + channel + "…")
    bot.joinChannel(channel)
    Log.i("Joined")
  } catch {
    case e: Exception => Log.e(e.toString)
  }
}
