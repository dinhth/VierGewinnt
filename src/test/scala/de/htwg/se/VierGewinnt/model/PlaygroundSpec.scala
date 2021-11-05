package de.htwg.se.VierGewinnt.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import io.AnsiColor.*
import scala.math.ceil

class PlaygroundSpec extends AnyWordSpec {
  "Playground is the representation of the gamefield" when {
    "initialized without parameter" should {
      val playground = new Playground()
      val player = new Player("Player 1", Chip.RED)
      "have the default size" in {
        playground.size should be(7)
      }
      val playgroundcpy = playground.insertChip(0, player)
      "Player should insert a chip in a choosen column" in {
        playgroundcpy.grid.getCell(playgroundcpy.size - 1, 0).chip should be(Chip.RED)
      }
      "request how many chips inserted in a column" in {
        playgroundcpy.getPosition(0) should be(playgroundcpy.size - 2)
      }

    }
    "have a String representation with a head, grid and border" should {
      val playground = new Playground(7)
      "have a line with column names" in {
        playground.colnames() should be(s"${BLUE_B}\t1\t2\t3\t4\t5\t6\t7\t ${RESET}\n")
      }
      "have a bottom border" in {
        playground.border() should be(s"${BLUE_B}  -----------------------------  ${RESET}\n")
      }
      "should print the whole gamefield" in {
        playground.toString should be(playground.colnames() + playground.grid + playground.border())
      }
    }
  }
}
