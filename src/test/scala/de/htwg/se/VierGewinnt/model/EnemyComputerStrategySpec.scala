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
      tempplayground.insertChip(1) should not be (Playground(Grid(Vector.tabulate(7, 7)((row, col) => Cell(Chip.EMPTY))).replaceCell(6, 1, Cell(Chip.YELLOW)), List(Player("Player 2", Chip.RED), Player("Player 1", Chip.YELLOW)), EnemyComputerStrategy()))
    }
  }
}
