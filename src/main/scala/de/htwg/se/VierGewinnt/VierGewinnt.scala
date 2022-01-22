package de.htwg.se.VierGewinnt

import aview.GUI
import aview.TUI
import com.google.inject.{Guice, Inject, Injector}
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
    override def run(): Unit = TUI(controller).run
  }.start()

