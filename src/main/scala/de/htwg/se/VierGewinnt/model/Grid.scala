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

  def checkFull(): Boolean = { //if any of the top rows is not full, return false, if true, grid is completly full
    var result = true
    for (i <- 0 to size - 1) yield {
      result = if (getCell(i, size - 1).value.getValue == 0) false else result
    }
    result
  }

  def checkWin(): Int = { //Return 0 = none, 1 = red, 2 = yel
    var result = 0
    // Horizontal
    result match {
      case 0 => result = checkHorizontalWin()
      case _ => result
    }
    // Vertical
    result match {
      case 0 => result = checkVerticalWin()
      case _ => result
    }
    // Diagonal Up Right
    result match {
      case 0 => result = checkDiagonalUpRightWin()
      case _ => result
    }
    // Diagonal Up Left
    result match {
      case 0 => result = checkDiagonalUpLeftWin()
      case _ => result
    }
    result
  }

  def checkFour(a1:Int, a2:Int, b1:Int, b2:Int, c1:Int, c2:Int, d1:Int, d2:Int): Int = {
    val check = getCell(a1,a2).value.getValue
    if ((getCell(b1,b2).value.getValue == check)
      && (getCell(c1,c2).value.getValue == check)
      && (getCell(d1,d2).value.getValue == check)) {
      check
    } else {
      0
    }
  }

  def checkHorizontalWin(): Int = {
    var result = 0;
    for (y <- 0 to (size - 4)) yield { //Height
      for (x <- 0 to (size - 1)) yield { //Width
        var tempres = checkFour(x,y,x,y+1,x,y+2,x,y+3) //tempres = temporary result
        if (tempres != 0) {
          result = tempres
        }
      }
    }
    result
  }

  def checkVerticalWin(): Int = {
    var result = 0;
    for (x <- 0 to (size - 4)) yield { //Width
      for (y <- 0 to (size - 1)) yield { //Height
        var tempres = checkFour(x,y,x+1,y,x+2,y,x+3,y)
        if (tempres != 0) {
          result = tempres
        }
      }
    }
    result
  }

  def checkDiagonalUpRightWin(): Int = {
    var result = 0;
    for (y <- 0 to (size - 4)) yield { //Height
      for (x <- 0 to (size - 4)) yield { //Width
        var tempres = checkFour(x,y,x+1,y+1,x+2,y+2,x+3,y+3)
        if (tempres != 0) {
          result = tempres
        }
      }
    }
    result
  }

  def checkDiagonalUpLeftWin(): Int = {
    var result = 0;
    for (y <- 0 to (size - 4)) yield { //Height
      for (x <- 3 to (size - 1)) yield { //Width
        var tempres = checkFour(x,y,x-1,y+1,x-2,y+2,x-3,y+3)
        if (tempres != 0) {
          result = tempres
        }
      }
    }
    result
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

