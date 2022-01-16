package de.htwg.se.VierGewinnt.aview

import com.google.inject.Guice
import de.htwg.se.VierGewinnt.VierGewinntModule
import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface
import de.htwg.se.VierGewinnt.controller.controllerComponent.controllerBaseImpl.{Controller, GameState, PlayState}

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.io.StringReader
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec {
  "TUI of VierGewinnt" should {
    val injector = Guice.createInjector(new VierGewinntModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    val tui = new Tui(controller)

    "run" in {
      val in = new BufferedReader(new StringReader("\nq"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.run
        }
      }
      //source.toString should be(
      //  s"Welcome to 'Vier Gewinnt'\n\nPlease select one of the game type you want to play. For default settings ('Player vs Player', grid size=7) press ENTER\n0:'Player vs. Player', 1:'Player vs. Bot', 2:'Bot vs. Bot'\n${controller.toString}\n\nGame is on"
      //)
    }

    "choose enemy mode" in {
      val in = new BufferedReader(new StringReader("\n0\n7\nredo\nundo\nk\n4\nq"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.run
        }
      }
    }

    "choose no mode" in {
      val in = new BufferedReader(new StringReader("\n2\n7\nq"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.run
        }
      }
    }

    /*
    "be playable against a Computer" in {
      val in = new BufferedReader(new StringReader("computer"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.getInputAndPrintLoop()
        }
      }

    }

    "be playable against a Person" in {
      val in = new BufferedReader(new StringReader("1\nq\n"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.getInputAndPrintLoop()
        }
      }
    }
     */

    "valid input" in {
      val in = new BufferedReader(new StringReader("\n1\nq\n"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.getInputAndPrintLoop()
        }
      }
      // tui.size should be(7)
      controller.playground.getPosition(0) should be(5)
    }
    "invalid input: not a number" in {
      val in = new BufferedReader(new StringReader("\na\n \n+\n\nq\n"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.getInputAndPrintLoop()
        }
      }

      //source.toString should be("doesn't look like a number\n" * 5 + "\n")
    }
    "invalid input: a number but not in range" in {
      val in = new BufferedReader(new StringReader("\n0\n8\n-1\nq\n"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.getInputAndPrintLoop()
        }
      }
      //source.toString should be(
      //  "doesn't look like a number" + ("wrong input, try a number from 1 to " + controller.playground.size + "\n") * 3
      //)
    }

    "update with update()" in {
      tui.update
    }
  }
}
