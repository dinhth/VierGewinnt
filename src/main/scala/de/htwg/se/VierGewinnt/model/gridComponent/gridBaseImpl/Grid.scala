package de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import scala.io.AnsiColor.BLUE_B
import scala.io.AnsiColor.RESET
import scala.util.Failure
import scala.util.Success
import scala.util.Try

case class Grid(grid: Vector[Vector[Cell]]) extends GridInterface:
  def this(size: Int = 7) = this(Vector.tabulate(size, size)((row, col) => Cell())) // call for an empty board

  override def getCell(row: Int, col: Int): Cell = grid(row)(col)
  override def get2DVector(): Vector[Vector[Cell]] = grid

  override def replaceCell(row: Int, col: Int, cell: Cell): Try[GridInterface] = {
    val result = Try(copy(grid.updated(row, grid(row).updated(col, cell))))
    result match {
      case Success(v) => Success(v)
      case Failure(e) => Failure(e)
    }
  }

  override def removeCell(row: Int, col: Int): Try[GridInterface] = {
    val result = Try(copy(grid.updated(row, grid(row).updated(col, Cell()))))
    result match {
      case Success(v) => Success(v)
      case Failure(e) => Failure(e)
    }
  }

  override def replaceCellRisk(row: Int, col: Int, cell: Cell): GridInterface = { // For Testing, only if 100% certain
    copy(grid.updated(row, grid(row).updated(col, cell)))
  }

  override def checkFull(): Boolean = { // if any of the top rows is not full, return false, if true, grid is completly full
    var result = true
    for (i <- 0 to size - 1) yield {
      result = if (getCell(i, size - 1).value.getValue == 0) false else result
    }
    result
  }

  override def checkWin(): Option[Int] = { // Return 0 = none, 1 = red, 2 = yel
    val tupel = (checkHorizontalWin(), checkVerticalWin(), checkDiagonalUpRightWin(), checkDiagonalUpLeftWin())
    tupel match {
      case (0, 0, 0, 0) => None
      case _ => {
        val list = tupel.toList
        val sorted = list.sortWith(_ > _)
        Some(sorted(0))
      }
    }
  }

  override def checkFour(a1: Int, a2: Int, b1: Int, b2: Int, c1: Int, c2: Int, d1: Int, d2: Int): Int = {
    val check = getCell(a1, a2).value.getValue
    if (
      (getCell(b1, b2).value.getValue == check)
      && (getCell(c1, c2).value.getValue == check)
      && (getCell(d1, d2).value.getValue == check)
    ) {
      check
    } else {
      0
    }
  }

  override def checkHorizontalWin(): Int = {
    var result = 0;
    for (y <- 0 to (size - 4)) yield { // Height
      for (x <- 0 to (size - 1)) yield { // Width
        var tempres = checkFour(x, y, x, y + 1, x, y + 2, x, y + 3) // tempres = temporary result
        if (tempres != 0) {
          result = tempres
        }
      }
    }
    result
  }

  override def checkVerticalWin(): Int = {
    var result = 0;
    for (x <- 0 to (size - 4)) yield { // Width
      for (y <- 0 to (size - 1)) yield { // Height
        var tempres = checkFour(x, y, x + 1, y, x + 2, y, x + 3, y)
        if (tempres != 0) {
          result = tempres
        }
      }
    }
    result
  }

  override def checkDiagonalUpRightWin(): Int = {
    var result = 0;
    for (y <- 0 to (size - 4)) yield { // Height
      for (x <- 0 to (size - 4)) yield { // Width
        var tempres = checkFour(x, y, x + 1, y + 1, x + 2, y + 2, x + 3, y + 3)
        if (tempres != 0) {
          result = tempres
        }
      }
    }
    result
  }

  override def checkDiagonalUpLeftWin(): Int = {
    var result = 0;
    for (y <- 0 to (size - 4)) yield { // Height
      for (x <- 3 to (size - 1)) yield { // Width
        var tempres = checkFour(x, y, x - 1, y + 1, x - 2, y + 2, x - 3, y + 3)
        if (tempres != 0) {
          result = tempres
        }
      }
    }
    result
  }

  size = grid.size

  override def toString: String = { // string representation of the grid line-by-line, a 'Cell' calls itselfs toString
    var out = ""
    grid.foreach { (row: Vector[Cell]) =>
      out = out + s"${BLUE_B}  "
      row.foreach { (cell: Cell) =>
        out = out + s"${BLUE_B}|" + cell
      }
      out = out + s"${BLUE_B}|  ${RESET}\n"
    }
    return out
  }
