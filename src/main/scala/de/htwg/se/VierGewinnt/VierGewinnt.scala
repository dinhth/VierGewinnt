package de.htwg.se.VierGewinnt

import aview.Tui
import controller.Controller
import model.{Cell, Chip, Grid, Player, Playground}
import scala.io.StdIn.readLine

/*
@main def run: Unit =
  val playground = new Playground(size = 7)
  val controller = Controller(playground)
  val tui = Tui(controller)
  tui.run
*/

/*
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
*/

//Match Version
  val player = List(Player("Player 1", Chip.YELLOW), Player("Player 2", Chip.RED))
  var i = 0

@main def run: Unit =
  val playground = new Playground(size = 7)

  println("Welcome to 'Vier Gewinnt' " + player(0).name + " and " + player(1).name)
  println(playground.toString)
  getInputAndPrintLoop(playground: Playground)

def getInputAndPrintLoop(playground: Playground): Unit =
  println(s"It's your turn ${player(i % 2)}")
  val input = readLine
  val chars = input.toCharArray
  input match {
    case "q" =>

    case _ => {
      val in = chars(0).toString.toInt
      val p = player(i % 2)

      val newplayground = playground.insertChip(in - 1, p)
      i += 1
      println(newplayground)
      getInputAndPrintLoop(newplayground)
    }
  }


