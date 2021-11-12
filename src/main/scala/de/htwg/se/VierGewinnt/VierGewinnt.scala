package de.htwg.se.VierGewinnt

import aview.Tui
import controller.Controller
import model.{Cell, Chip, Grid, Player, Playground}
import scala.io.StdIn.readLine


@main def run: Unit =
  val controller = new Controller()
  val tui = Tui(controller)
  tui.run






