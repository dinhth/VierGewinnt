package de.htwg.se.VierGewinnt

import aview.{Tui, GUI}
import controller.Controller
import model.{Cell, Chip, Grid, Player}
import scala.io.StdIn.readLine


@main def run: Unit =
  val controller = new Controller()
  //val gui = GUI(controller)
  //gui.run
  val tui = Tui(controller)
  tui.run






