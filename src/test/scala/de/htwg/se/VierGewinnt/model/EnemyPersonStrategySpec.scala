package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.controller.Controller
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.io.AnsiColor.*

class EnemyPersonStrategySpec extends AnyWordSpec {
  "An Enemy Person Spec should work deterministic" when {
    var playground = PlaygroundPvP(new Grid(7), List(HumanPlayer("Player 1", Chip.YELLOW), BotPlayer("Bot 1", Chip.RED)))
    "Inserting a Chip" in {
      playground.insertChip(1) should be(
        PlaygroundPvP(
          Grid(Vector.tabulate(7, 7)((row, col) => Cell(Chip.EMPTY))).replaceCellRisk(6, 1, Cell(Chip.YELLOW)),
          List(HumanPlayer("Player 2", Chip.RED), HumanPlayer("Player 1", Chip.YELLOW))
        )
      )
    }
  }
}
