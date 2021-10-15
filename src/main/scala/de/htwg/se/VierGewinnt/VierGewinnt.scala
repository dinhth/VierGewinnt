package de.htwg.se.VierGewinnt

import scala.io.StdIn.readLine

object VierGewinnt {
  def main(args: Array[String]): Unit = {
    var input:String=""
    input = readLine()
    println("1.Spieler: " + input)
    input = readLine()
    println("2.Spieler: " + input)
    println("Vier Gewinnt")
  }

}
