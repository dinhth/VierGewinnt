package de.htwg.se.VierGewinnt.model


import scala.io.AnsiColor.BLUE_B
import scala.io.AnsiColor.RESET

trait PlaygroundTemplate {
  val grid: Grid
  val player: List[Player]

  val size: Int = grid.size
  var error = ""

  def insertChip(col: Int): PlaygroundTemplate
  // enemStrat.insertChip(this, col)

  def takeAwayChip(col: Int): PlaygroundTemplate
  // copy(grid.replaceCell(getDeletePosition(col), col, Cell(Chip.EMPTY)), player.reverse, enemStrat)

  def getDeletePosition(col: Int): Int = { // get the position where the chip should drop
    var i = size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i += 1
    i match {
      case this.size => this.size - 1
      case _         => i
    }
  }

  /*def setEnemyStrategy(enemystrat: String): Playground = {
    enemystrat match {
      case "person" => copy(this.grid, player, EnemyPersonStrategy())
      case "bot"    => copy(this.grid, player, EnemyComputerStrategy())
      case _        => this
    }
  }*/

  def getPosition(col: Int): Int = { // get the position where the chip should drop
    var i = size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i
  }

  override def toString: String = {
    val box = "It's your turn " + player(0) + "\n" + colnames() + grid + border()
    return if (error != "") error else box // print the col is full error if needed
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
