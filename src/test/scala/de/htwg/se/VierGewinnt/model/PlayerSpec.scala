package de.htwg.se.VierGewinnt.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayerSpec extends AnyWordSpec {
  "A Player 1" should {
    "have a String representation" in {
      Player("player1").toString should be("player1")
    }
  }
}
