package de.htwg.se.VierGewinnt.aview

import de.htwg.se.VierGewinnt.controller.Controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class TuiSpec extends AnyWordSpec {
  "TUI of VierGewinnt" should {
    val controller = new Controller()
    val tui = new Tui(controller)

    "valid input" in {
      controller.insertChip(0)
      tui.size should be(7)
      controller.playground.getPosition(0) should be(5)
    }
  }
}
