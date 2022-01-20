package de.htwg.se.VierGewinnt.model.playerComponent

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.*
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.HumanPlayer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor.*

class PlayerInterfaceSpec extends AnyWordSpec {
  "A Player" when {
    "new" should {
      val player = HumanPlayer("player1", Chip.RED)
      "have a name" in {
        player.name should be("player1")
      }
      "have a String representation" in {
        player.toString should be(s"${Chip.RED.getColorCode}player1${RESET}")
      }
      "have a red background color" in {
        player.chip.getColorCode should be(s"${RED_B}")
      }
    }
  }
}
