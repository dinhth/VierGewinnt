package de.htwg.se.VierGewinnt.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlaygroundSpec extends AnyWordSpec {
  "VierGewinnt" should {
    "have a String representation" in {
      val matrix = Playground(7)
      matrix.toString should be("")
    }
  }
}
