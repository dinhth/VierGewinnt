package de.htwg.se.VierGewinnt.controller

import de.htwg.se.VierGewinnt.util.Observer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ControllerSpec extends AnyWordSpec {
  "A controller" when {
    "observed by an Observer" should {
      val controller = new Controller()
      val observer = new Observer {
        var updated: Boolean = false

        override def update: Unit = updated = !updated

        override def toString: String = updated.toString
      }

      "notify when insert a chip" in {
        controller.add(observer)
        controller.insertChip(0)
        observer.toString should be("true")
        controller.playground.getPosition(0) should be(5)
      }

      "after remove should not notify" in {
        controller.remove(observer)
        controller.insertChip(0)
        observer.toString should be("true")
      }
    }
  }
}
