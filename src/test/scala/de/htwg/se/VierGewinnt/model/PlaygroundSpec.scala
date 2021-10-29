package de.htwg.se.VierGewinnt.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import io.AnsiColor.*
import scala.math.ceil

class PlaygroundSpec extends AnyWordSpec {
  "Playground is the string representation of the gamefield" when {
    "to be constructed" should {
      val p0 = Playground(10)
      "have a custom size" in {
        p0.size should be(10)
      }
      val playground = Playground()
      "have the right default size" in {
        playground.size should be(7)
      }
      "have a line with column names" in {
        playground.colnames() should be(s"${BLUE_B}\t1\t2\t3\t4\t5\t6\t7\t ${RESET}\n")
      }
      "have empty lines/rows" in {
        playground.line() should be(s"${BLUE_B}  |   |   |   |   |   |   |   |  ${RESET}\n")
      }
      "have a bottom border" in {
        playground.border() should be(s"${BLUE_B}  -----------------------------  ${RESET}\n")
      }
      "should print the right representation with an example line" in {
        playground.toString should be(playground.colnames() + playground.line() * playground.size + s"${BLUE_B}  " +
          (s"${BLUE_B}|${YELLOW_B}   ${RESET}") * (playground.size.toFloat / 2).toInt +
          (s"${BLUE_B}|${RED_B}   ${RESET}") * ceil(playground.size.toFloat / 2).toInt +
          s"${BLUE_B}|  ${RESET}\n" + playground.border())
      }
    }
  }
}
