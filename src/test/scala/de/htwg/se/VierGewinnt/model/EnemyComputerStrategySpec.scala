package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.controller.Controller
import de.htwg.se.VierGewinnt.model.gridComponent.*
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.*
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.{BotPlayer, HumanPlayer}
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundPvE
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor.*

class EnemyComputerStrategySpec extends AnyWordSpec {
  "An Enemy Computer Spec should work random" when {
    var playground = PlaygroundPvE(new Grid(7), List(HumanPlayer("Player 1", Chip.YELLOW), BotPlayer("Bot 1", Chip.RED)))
    "Inserting a Chip" in {
      playground.insertChip(1) should not be (
        playgroundBaseImpl.PlaygroundPvE(
          Grid(Vector.tabulate(7, 7)((row, col) => Cell(Chip.EMPTY))).replaceCellRisk(6, 1, gridBaseImpl.Cell(Chip.YELLOW)),
          List(playerBaseImpl.HumanPlayer("Player 2", Chip.RED), playerBaseImpl.HumanPlayer("Player 1", Chip.YELLOW))
        )
      )
    } /*
    "not inserting a Chip manually" in {
      for (i <- 0 to 8) {
        playground = playground.insertChip(1)
      }
      playground.insertChip(1) should be(playground)
    }
    "not inserting a Chip by computer" in {
      for (i <- 1 to 7) yield {
        for (j <- 0 to 6) yield {
          playground = playground.insertChip(j)
        }
      }
      playground.insertChip(1) should be(playground)
    }

    "not insert when full by computer" in {
      var pl = PlaygroundPvE(
        Grid(Vector.tabulate(4, 4)((row, col) => Cell(Chip.EMPTY)))
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
        List(HumanPlayer("Player 1", Chip.YELLOW), BotPlayer("Bot 1", Chip.RED))
      )
      pl = pl.insertChip(1)
      pl = pl.insertChip(0)
    }*/

  }
}
