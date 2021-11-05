package de.htwg.se.VierGewinnt.model

import io.AnsiColor._
import scala.math._

case class Playground(grid: Grid) {

  def this(size: Int = 7) = this(new Grid(size))

  val size: Int = grid.size

  def insertChip(row: Int, col: Int, player: Player): Playground = {
    copy(grid.replaceCell(getPosition(col), col, Cell(player.chip)))
  }

  def getPosition(col: Int): Int = { //get the position where the chip should drop
    var i = size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i
  }


  override def toString: String = {
    val box = colnames() + grid + border()
    return box
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
