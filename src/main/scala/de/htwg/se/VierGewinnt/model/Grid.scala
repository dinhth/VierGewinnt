package de.htwg.se.VierGewinnt.model

import io.AnsiColor._

/** This class keeps the data of the game as a 2D Vector
 * auxilary constructor gets called for an empty board.
 * The default constructor is to copy&replace */
case class Grid(grid: Vector[Vector[Cell]]) {
  def this(size: Int = 7) = this(Vector.tabulate(size, size)((row, col) => Cell(Chip.EMPTY))) //call for an empty board

  def getCell(row: Int, col: Int): Cell = grid(row)(col) //getter

  def replaceCell(row: Int, col: Int, cell: Cell): Grid = {
    copy(grid.updated(row, grid(row).updated(col, cell)))
  }

  val size: Int = grid.size

  override def toString: String = { //string representation of the grid line-by-line, a 'Cell' calls itselfs toString
    var out = ""
    grid.foreach {
      (row: Vector[Cell]) =>
        out = out + s"${BLUE_B}  "
        row.foreach { (cell: Cell) =>
          out = out + s"${BLUE_B}|" + cell
        }
        out = out + s"${BLUE_B}|  ${RESET}\n"
    }
    return out
  }
}

