package de.htwg.se.VierGewinnt

import de.htwg.se.VierGewinnt.aview.Tui
import de.htwg.se.VierGewinnt.model.{Cell, Chip, Grid, Player, Playground}

import scala.io.StdIn.readLine


object VierGewinnt {
  var playground = new Playground(size = 7)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    val player = List(Player("Player 1", Chip.YELLOW), Player("Player 2", Chip.RED))
    println("Welcome to 'Vier Gewinnt' " + player(0).name + " and " + player(1).name)

    var input = ""
    var i = 0
    println(playground)


    while {
      println(s"It's your turn ${player(i % 2)}")
      input = readLine();
      playground = tui.evaluate(input, player(i % 2), playground)
      println(playground)
      i += 1;
      input != "q"
    }
    do ()

  }

}
