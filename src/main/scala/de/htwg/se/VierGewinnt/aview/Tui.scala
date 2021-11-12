package de.htwg.se.VierGewinnt
package aview

import controller.Controller
import model.{Player, Playground, Chip}
import scala.io.StdIn.readLine
import util.Observer

import scala.util.Try


class Tui(controller: Controller) extends Observer :
  controller.add(this)
  val size = 7

  def run =
    println("Welcome to 'Vier Gewinnt' ")
    println(controller.toString)
    getInputAndPrintLoop()

  def getInputAndPrintLoop(): Unit =
    val input = readLine
    val chars = input.toCharArray
    input match {
      case x if x.toIntOption == None =>
        println("doesn't look like a number")
        getInputAndPrintLoop()
      case x if x.toInt < 1 || x.toInt > size =>
        println("wrong input, try a number from 1 to " + size)
        getInputAndPrintLoop()
      case _ =>
        controller.insertChip(input.toInt - 1)
        getInputAndPrintLoop()
    }

  override def update: Unit = println(controller.toString)




