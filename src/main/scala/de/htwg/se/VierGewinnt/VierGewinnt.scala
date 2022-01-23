package de.htwg.se.VierGewinnt

import aview.GUI
import aview.TUI
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine

@main def run(args:String*): Unit =
  val injector = Guice.createInjector(new VierGewinntModule)
  val controller = injector.getInstance(classOf[ControllerInterface])

  args match
    case x if x.contains("tui") =>
      TUI(controller).run
    case x if x.contains("gui") =>
      GUI(controller).main(Array())
    case _ =>
      new Thread {
        override def run(): Unit = GUI(controller).main(Array())
      }.start()
      TUI(controller).run
