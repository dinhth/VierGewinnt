package de.htwg.se.VierGewinnt.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.io.AnsiColor._

class ChipSpec extends AnyWordSpec {

  "create chip for empty cell" when {
    val chip = Chip.EMPTY
    "have value 0" in {
      chip.getValue should be(0)
    }
    "have color Blue" in {
      chip.getColorCode should be(BLUE_B)
    }
  }


}
