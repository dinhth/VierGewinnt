package de.htwg.se.VierGewinnt.view.gui

import com.google.inject.Guice
import de.htwg.se.VierGewinnt.core.ControllerInterface
import de.htwg.se.VierGewinnt.core.CoreModule

import scala.util.{Failure, Success, Try}

object GuiService {
  @main def run(): Unit =
    val injector = Guice.createInjector(new CoreModule)
    val controller = injector.getInstance(classOf[ControllerInterface])


    //Try(FileIOAPI) match
    //  case Success(v) => println("Persistance Rest Server is running!")
    //  case Failure(v) => println("Persistance Server couldn't be started! " + v.getMessage + v.getCause)

    GUI(controller).main(Array())
}
