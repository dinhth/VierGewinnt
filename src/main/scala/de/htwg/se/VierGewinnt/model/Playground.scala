package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.util.EnemyStrategy
import io.AnsiColor.*
import scala.math.*
import scala.util.*

case class Playground(grid: Grid, player: List[Player], enemStrat: EnemyStrategy) {
  def this(size: Int = 7) = this(new Grid(size), List(HumanPlayer("Player 1", Chip.YELLOW), HumanPlayer("Player 2", Chip.RED)), EnemyPersonStrategy())

  //top element of the List 'player' is the current player

  val size: Int = grid.size
  var error = ""

  /*def insertChip(col: Int): Playground = {
    enemStrat.insertChip(this, col)
  }

  def takeAwayChip(col:Int): Playground = {
    val returnGrid = grid.replaceCell(getDeletePosition(col), col, Cell(Chip.EMPTY))
    returnGrid match {
      case Success(v) => copy(v, player.reverse, enemStrat)
      case Failure(_) => this
    }
  }*/

  def getDeletePosition(col: Int): Int = { //get the position where the chip should drop
    var i = this.size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i += 1
    i match {
      case this.size => this.size - 1
      case _ => i
    }
  }

  def setEnemyStrategy(enemystrat:String):Playground = {
    enemystrat match {
      case "person" => copy(this.grid, player, EnemyPersonStrategy())
      case "bot" => copy(this.grid, player, EnemyComputerStrategy())
      case _ => this
    }
  }

  def getPosition(col: Int): Int = { //get the position where the chip should drop
    var i = size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i
  }

  override def toString: String = {
    val box = "It's your turn " + player(0) + "\n" + colnames() + grid + border()
    return if (error != "") error else box //print the col is full error if needed
  }

  def colnames(): String = {
    val cols = for {
      n <- 1 to size
    } yield n
    return s"${BLUE_B}\t" + cols.mkString("\t") + s"\t ${RESET}\n"
  }

  def border(): String = {
    return s"${BLUE_B}  " + ("----" * size) + s"-  ${RESET}\n"
  }
}
