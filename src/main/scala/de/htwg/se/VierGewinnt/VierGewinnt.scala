package de.htwg.se.VierGewinnt

import de.htwg.se.VierGewinnt.model.{Player, Playground}

import scala.io.StdIn.readLine

object VierGewinnt {
  def main(args: Array[String]): Unit = {
    println("Player 1 tell us your name: ")
    val player1 = Player(readLine())
    println("Player 2 tell us your name: ")
    val player2 = Player(readLine())
    println("Welcome to 'Vier Gewinnt' " + player1.name + " and " + player2.name)


    val playground = Playground(size = 7)
    println(playground)
  }

}
