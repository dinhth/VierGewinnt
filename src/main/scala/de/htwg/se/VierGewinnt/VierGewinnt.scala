package de.htwg.se.VierGewinnt

import aview.GUI
import aview.Tui
import controller.Controller
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Cell, Chip}

import scala.io.StdIn.readLine
import scalafx.application.Platform
import scalafx.application.Platform.runLater

@main def run: Unit =
  val controller = new Controller()
  new Thread {
    override def run(): Unit = GUI(controller).main(Array())
  }.start()
  new Thread {
    override def run(): Unit = Tui(controller).run
  }.start()
