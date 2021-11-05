package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import scala.io.AnsiColor._

import java.lang.IndexOutOfBoundsException

class GridSpec extends AnyWordSpec {
  "Grid keeps the 2D data structure" when {
    "initialized an empty grid without a parameter" should {
      val grid = new Grid()
      "have a size of 7 (default)" in {
        grid should have size 7
      }
      "fill a 2D Vector with empty Chips" in {
        grid.grid should be(Vector.tabulate(7, 7)((row, col) => Cell(Chip.EMPTY)))
      }
      "returns any cell from the grid" in {
        grid.getCell(0, 0) should be(Cell(Chip.EMPTY))
      }
      "do not return anything with negative indices" in {
        an[IndexOutOfBoundsException] should be thrownBy grid.getCell(-1, -1)
      }
      "do not return anything with too high indices" in {
        an[IndexOutOfBoundsException] should be thrownBy grid.getCell(grid.size + 1, 0)
      }
    }
    "initialized an empty grid with custom size" should {
      val grid = new Grid(15)
      "have a size of 7 (default)" in {
        grid should have size 15
      }
      "fill a 2D Vector with empty Chips" in {
        grid.grid should be(Vector.tabulate(15, 15)((row, col) => Cell(Chip.EMPTY)))
      }
      "returns any cell from the grid" in {
        grid.getCell(0, 0) should be(Cell(Chip.EMPTY))
      }
      "do not return anything with negative indices" in {
        an[IndexOutOfBoundsException] should be thrownBy grid.getCell(-1, -1)
      }
      "do not return anything with too high indices" in {
        an[IndexOutOfBoundsException] should be thrownBy grid.getCell(grid.size + 1, 0)
      }
    }
    "replace a Cell" should {
      val grid = new Grid()
      val gridcpy0 = grid.replaceCell(0, 0, Cell(Chip.RED))
      "should change the cell Color from BLUE to RED" in {
        gridcpy0.getCell(0, 0) should be(Cell(Chip.RED))
      }
      val gridcpy1 = grid.replaceCell(0, 0, Cell(Chip.YELLOW))
      "should change the cell Color from RED to YELLOW" in {
        gridcpy1.getCell(0, 0) should be(Cell(Chip.YELLOW))
      }
    }
    "have a string representation" in {
      val grid = new Grid(1)
      grid.toString should be(s"${BLUE_B}  ${BLUE_B}|" + grid.getCell(0, 0) + s"${BLUE_B}|  ${RESET}\n")
    }
  }

}
