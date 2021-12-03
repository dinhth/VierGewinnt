package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.controller.Controller
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor.*

class EnemyComputerStrategySpec extends AnyWordSpec {
  "An Enemy Computer Spec should work random" when {
    var playground = new Playground()
    "Inserting a Chip" in {
      val tempplayground = playground.setEnemyStrategy("computer")
      tempplayground.insertChip(1) should not be (Playground(Grid(Vector.tabulate(7, 7)((row, col) => Cell(Chip.EMPTY))).replaceCellRisk(6, 1, Cell(Chip.YELLOW)), List(HumanPlayer("Player 2", Chip.RED), HumanPlayer("Player 1", Chip.YELLOW)), EnemyComputerStrategy()))
    }
    "not inserting a Chip manually" in {
      var tempplayground = playground.setEnemyStrategy("computer")
      for (i <- 0 to 8) {
        tempplayground = tempplayground.insertChip(1)
      }
      tempplayground.insertChip(1) should be (tempplayground)
    }
    "not inserting a Chip by computer" in {
      var tempplayground = playground.setEnemyStrategy("person")
      for (i <- 1 to 7) yield {
        for (j <- 0 to 6) yield {
          tempplayground = tempplayground.insertChip(j)
        }
      }
      tempplayground.insertChip(1) should be (tempplayground)
    }

    "not insert when full by computer" in {
      var pl = Playground(Grid(Vector.tabulate(4, 4)((row, col) => Cell(Chip.EMPTY)))
        .replaceCellRisk(0, 0, Cell(Chip.RED))
        .replaceCellRisk(0, 1, Cell(Chip.RED))
        .replaceCellRisk(0, 2, Cell(Chip.RED))
        .replaceCellRisk(0, 3, Cell(Chip.YELLOW))

        .replaceCellRisk(1, 0, Cell(Chip.YELLOW))
        .replaceCellRisk(1, 1, Cell(Chip.YELLOW))
        .replaceCellRisk(1, 2, Cell(Chip.RED))
        .replaceCellRisk(1, 3, Cell(Chip.YELLOW))

        .replaceCellRisk(2, 0, Cell(Chip.YELLOW))
        .replaceCellRisk(2, 1, Cell(Chip.RED))
        .replaceCellRisk(2, 2, Cell(Chip.RED))
        .replaceCellRisk(2, 3, Cell(Chip.YELLOW))

        .replaceCellRisk(3, 0, Cell(Chip.EMPTY))
        .replaceCellRisk(3, 1, Cell(Chip.EMPTY))
        .replaceCellRisk(3, 2, Cell(Chip.YELLOW))
        .replaceCellRisk(3, 3, Cell(Chip.RED)),
        List(HumanPlayer("Player 1", Chip.YELLOW), HumanPlayer("Player 2", Chip.RED)), EnemyComputerStrategy())
      pl = pl.insertChip(1)
      pl = pl.insertChip(0)
    }

  }
}
