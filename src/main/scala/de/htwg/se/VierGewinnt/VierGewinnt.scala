package de.htwg.se.VierGewinnt

import aview.GUI
import aview.Tui
import controller.Controller
import javafx.application.Application
import model.Cell
import model.Chip
import model.Grid
import model.Player
import scala.io.StdIn.readLine

@main def run: Unit =
  val controller = new Controller()

  val gui = GUI(controller).main(Array())

  val tui = Tui(controller)
  tui.run
