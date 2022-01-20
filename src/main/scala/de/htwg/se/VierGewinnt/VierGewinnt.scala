package de.htwg.se.VierGewinnt

import aview.GUI
import aview.Tui
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.VierGewinnt.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Cell, Chip}
import de.htwg.se.VierGewinnt.*
import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine
import scalafx.application.Platform
import scalafx.application.Platform.runLater


@main def run: Unit =
  val injector = Guice.createInjector(new VierGewinntModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  new Thread {
    override def run(): Unit = GUI(controller).main(Array())
  }.start()
  new Thread {
    override def run(): Unit = Tui(controller).run
  }.start()

