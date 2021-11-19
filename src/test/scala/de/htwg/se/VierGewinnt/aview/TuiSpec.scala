package de.htwg.se.VierGewinnt.aview

import de.htwg.se.VierGewinnt.controller.Controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import java.io.{BufferedReader, ByteArrayInputStream, ByteArrayOutputStream, PrintStream, StringReader}

class TuiSpec extends AnyWordSpec {
  "TUI of VierGewinnt" should {
    val controller = new Controller()
    val tui = new Tui(controller)

    "run" in {
      val in = new BufferedReader(new StringReader("q"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.run
        }
      }
      source.toString should be(s"Welcome to 'Vier Gewinnt'\n$controller\n")
    }

    "valid input" in {
      val in = new BufferedReader(new StringReader("1\nq\n"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.getInputAndPrintLoop()
        }
      }
      tui.size should be(7)
      controller.playground.getPosition(0) should be(5)
    }
    "invalid input: not a number" in {
      val in = new BufferedReader(new StringReader("a\n \n+\n\nq\n"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.getInputAndPrintLoop()
        }
      }

      source.toString should be("doesn't look like a number\n" * 4)
    }
    "invalid input: a number but not in range" in {
      val in = new BufferedReader(new StringReader("0\n8\n-1\nq\n"))
      val source = new ByteArrayOutputStream()
      val printer = new PrintStream(source)
      Console.withOut(printer) {
        Console.withIn(in) {
          tui.getInputAndPrintLoop()
        }
      }
      source.toString should be(("wrong input, try a number from 1 to " + tui.size + "\n") * 3)
    }
  }
}
