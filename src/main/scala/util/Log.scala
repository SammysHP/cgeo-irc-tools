package util

import java.util.Date

object Log {
  val dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  var logLevel = 4

  def d(message: String) = {
    if (logLevel >= 4)
      println("DEBUG\t" + dateFormat.format(new Date()) + "\t" + message)
  }

  def i(message: String) = {
    if (logLevel >= 3)
      println("INFO\t" + dateFormat.format(new Date()) + "\t" + message)
  }

  def w(message: String) = {
    if (logLevel >= 2)
      println("WARN\t" + dateFormat.format(new Date()) + "\t" + message)
  }

  def e(message: String) = {
    if (logLevel >= 1)
      println("ERROR\t" + dateFormat.format(new Date()) + "\t" + message)
  }
}
