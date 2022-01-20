package de.htwg.se.VierGewinnt.model.gridComponent.gridMockImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import scala.util.Success
import scala.util.Try

class Grid(grid: Vector[Vector[Cell]]) extends GridInterface {
  override def getCell(row: Int, col: Int): Cell = grid(row)(col)

  override def get2DVector(): Vector[Vector[Cell]] = grid

  override def removeCell(row: Int, col: Int): Try[GridInterface] = Success[GridInterface](this)

  override def replaceCell(row: Int, col: Int, cell: Cell): Try[GridInterface] = Success[GridInterface](this)

  override def replaceCellRisk(row: Int, col: Int, cell: Cell): GridInterface = this

  override def checkFull(): Boolean = true

  override def checkWin(): Option[Int] = Some(0)

  override def checkFour(a1: Int, a2: Int, b1: Int, b2: Int, c1: Int, c2: Int, d1: Int, d2: Int): Int = 0

  override def checkHorizontalWin(): Int = 0

  override def checkVerticalWin(): Int = 0

  override def checkDiagonalUpRightWin(): Int = 0

  override def checkDiagonalUpLeftWin(): Int = 0
}
