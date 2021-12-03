package de.htwg.se.VierGewinnt.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.io.AnsiColor._

class PlayerSpec extends AnyWordSpec {
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
