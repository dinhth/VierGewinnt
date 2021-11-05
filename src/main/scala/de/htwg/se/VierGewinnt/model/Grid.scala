package de.htwg.se.VierGewinnt.model

import io.AnsiColor._

/** This class keeps the data of the game as a 2D Vector
 * auxilary constructor gets called for an empty board.
 * The default constructor is to copy&replace */
case class Grid(grid: Vector[Vector[Cell]]) {
  def this(size: Int) = this(Vector.tabulate(size, size)((row, col) => Cell(Chip.EMPTY))) //call for an empty board

  def cell(row: Int, col: Int): Cell = grid(row)(col) //getter

  val size: Int = grid.size

  override def toString: String = { //string representation of the grid line-by-line, a 'Cell' calls itselfs toString
    var out = ""
    grid.foreach {
      (row: Vector[Cell]) =>
        out = out + s"${BLUE_B}  "
        row.foreach { (cell: Cell) =>
          out = out + "|" + cell
        }
        out = out + s"|  ${RESET}\n"
    }
    return out
  }
}

