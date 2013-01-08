import org.pircbotx.PircBotX

object Bot extends App {
  val NAME = "cgeo-irct"
  val SERVER = "irc.freenode.net"
  val CHANNEL = "#cgeo-test"

  try {
    val bot = new PircBotX
    bot.getListenerManager().addListener(IssueLinker)
    bot.getListenerManager().addListener(Admin)
    bot.setName(NAME)
    bot.setAutoNickChange(true)

    println(bot.hasShutdownHook())

    print("Connecting... ")
    bot.connect(SERVER)
    bot.joinChannel(CHANNEL)
    println("ok!")
  } catch {
    case e: Exception => println("ERROR: " + e)
  }
}
