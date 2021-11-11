package de.htwg.se.VierGewinnt
package aview

import controller.Controller
import model.{Player, Playground, Chip}
import scala.io.StdIn.readLine
import util.Observer

import scala.util.Try


class Tui() {
  def evaluate(input: String, player: Player, playground: Playground): Playground = {
    var in = input
    while (in.toIntOption == None || in.toInt < 1 || in.toInt > playground.size || playground.getPosition(in.toInt - 1) == -1)
      println("falsche Eingabe")
      in = readLine()
    println(player)
    playground.insertChip(in.toInt - 1, player)
  }
}


/*
class Tui(controller: Controller) extends Observer:
  controller.add(this)
  
  val playground = new Playground(size = 7)
  val player = List(Player("Player 1", Chip.YELLOW), Player("Player 2", Chip.RED))
  var i = 0

  def run =
    println("Welcome to 'Vier Gewinnt' " + player(0).name + " and " + player(1).name)
    println(playground.toString)
    getInputAndPrintLoop()

  override def update: Unit = ???

  def getInputAndPrintLoop(): Unit =
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
      getInputAndPrintLoop()
    }
  }
*/    




