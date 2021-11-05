package de.htwg.se.VierGewinnt.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayerSpec extends AnyWordSpec {
  "A Player" when {
    "new" should {
      val player = Player("player1", Chip.RED)
      "have a name" in {
        player.name should be("player1")
      }
      "have a String representation" in {
        player.toString should be("player1")
      }
    }
  }
}
