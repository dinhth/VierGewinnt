package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.*
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.HumanPlayer
import de.htwg.se.VierGewinnt.model.playerComponent.{playerBaseImpl, playerMockImpl}
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundPvP
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor.*
import scala.math.ceil

class PlaygroundTemplateSpec extends AnyWordSpec {
  "Playground is the representation of the gamefield" when {
    "initialized without parameter" should {
      val playground = playgroundBaseImpl.PlaygroundPvP(new Grid(7), List(HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED)))
      "have the default size" in {
        playground.size should be(7)
      }
      val playgroundcpy = playground.insertChip(0)
      "Player should insert a chip in a choosen column" in {
        playgroundcpy.grid.getCell(playgroundcpy.size - 1, 0).chip should be(Chip.YELLOW)
      }
      "request how many chips inserted in a column" in {
        playgroundcpy.getPosition(0) should be(playgroundcpy.size - 2)
      }
      "be able to return the last position" in {
        playgroundcpy.getDeletePosition(0) should be(playgroundcpy.size - 1)
      }
    }
    "have a String representation with a head, grid and border" should {
      val playground = playgroundBaseImpl.PlaygroundPvP(new Grid(7), List(playerBaseImpl.HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED)))
      "have a line with column names" in {
        playground.colnames() should be(s"${BLUE_B}\t1\t2\t3\t4\t5\t6\t7\t ${RESET}\n")
      }
      "have a bottom border" in {
        playground.border() should be(s"${BLUE_B}  -----------------------------  ${RESET}\n")
      }
      "should print the whole gamefield" in {
        playground.toString should be("It's your turn " + playground.player(0).getName() + "\n" + playground.colnames() + playground.grid + playground.border())
      }
    }
    "when a column is full" should {
      val playground = new PlaygroundPvP(new Grid(1), List(playerBaseImpl.HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED)))
      val playgroundfull = playground.insertChip(0)
      "nothing happen" in {
        playgroundfull.insertChip(0) should be(playgroundfull)
      }
      "print that the col is full" in {
        playgroundfull.insertChip(0).error = "This column is full try another one"
      }
    }
  }

}
