package de.htwg.se.VierGewinnt.aview

import de.htwg.se.VierGewinnt.model.{Player, Playground}
import scala.io.StdIn.readLine

import scala.util.Try

class Tui() {
  def evaluate(input: String, player: Player, playground: Playground): Playground = {
    var in = input
    while (in.toIntOption == None || in.toInt < 1 || in.toInt > playground.size || playground.getPosition(in.toInt - 1) == -1)
      println("falsche Eingabe")
      in = readLine()
    println(player)
    playground.insertChip(playground.size - 1, in.toInt - 1, player)
  }
}
