package de.htwg.se.VierGewinnt.model

import io.AnsiColor._
import scala.math._

case class Playground(grid: Grid, player: List[Player]) {
  def this(size: Int = 7) = this(new Grid(size), List(Player("Player 1", Chip.YELLOW), Player("Player 2", Chip.RED)))
  //top element of the List 'player' is the current player

  val size: Int = grid.size
  var error = ""

  def insertChip(col: Int): Playground = {
    if (getPosition(col) != -1)
      copy(grid.replaceCell(getPosition(col), col, Cell(player(0).chip)), player.reverse)
    else
      error = "This column is full try another one"
      this
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
