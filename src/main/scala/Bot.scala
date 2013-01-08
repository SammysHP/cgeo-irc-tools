import org.pircbotx.PircBotX

object Bot extends App {
  val DEFAULT_NAME = "cgeo-irct"
  val DEFAULT_SERVER = "irc.freenode.net"
  val DEFAULT_CHANNEL = "#cgeo"

  def parseOptions(args: List[String], options: Map[String, String]): Map[String, String] = {
    args match {
      case Nil => options
      case "-s" :: server :: tail => parseOptions(tail, options + ("server" -> server))
      case "-c" :: channel :: tail => parseOptions(tail, options + ("channel" -> channel))
      case "-n" :: name :: tail => parseOptions(tail, options + ("name" -> name))
      case _ :: tail => parseOptions(tail, options)
    }
  }

  val options = parseOptions(args.toList, Map())

  try {
    val bot = new PircBotX
    bot.getListenerManager().addListener(IssueLinker)
    bot.getListenerManager().addListener(Admin)
    bot.getListenerManager().addListener(Help)
    bot.setName(options.getOrElse("name", DEFAULT_NAME))
    bot.setAutoNickChange(true)

    print("[info] Connecting... ")
    bot.connect(options.getOrElse("server", DEFAULT_SERVER))
    bot.joinChannel(options.getOrElse("channel", DEFAULT_CHANNEL))
    println("ok!")
  } catch {
    case e: Exception => println("ERROR: " + e)
  }
}
