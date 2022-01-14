package de.htwg.se.VierGewinnt.controller

import com.google.inject.Guice
import de.htwg.se.VierGewinnt.VierGewinntModule
import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface
import de.htwg.se.VierGewinnt.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.VierGewinnt.model.*
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.*
import de.htwg.se.VierGewinnt.util.{Move, Observer}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec {
  "A controller" when {
    "observed by an Observer" should {
      val injector = Guice.createInjector(new VierGewinntModule)
      val controller = injector.getInstance(classOf[ControllerInterface])
      val observer = new Observer {
        var updated: Boolean = false

        override def update: Unit = updated = !updated

        override def toString: String = updated.toString
      }

      "notify when insert a chip" in {
        controller.add(observer)
        controller.doAndPublish(controller.insChip, Move(0))
        observer.toString should be("true")
        controller.playground.getPosition(0) should be(5)
      }

      "after remove should not notify" in {
        controller.remove(observer)
        controller.doAndPublish(controller.insChip, Move(0))
        observer.toString should be("true")
      }

      // TODO: new test
      /*"change strat to computer enemy strategy" in {
        controller.changeEnemyStrategy("bot")
        controller.playground.enemStrat should be(EnemyComputerStrategy())
        controller.doAndPublish(controller.insChip, Move(0))
      }
      "change strat to person enemy strategy" in {
        controller.changeEnemyStrategy("person")
        controller.playground.enemStrat should be(EnemyPersonStrategy())
        controller.doAndPublish(controller.insChip, Move(0))
      }

      "checking no one has won" in {
        controller.checkWinner(controller.playground)
        for (y <- 0 to (6)) yield { //Height
          for (x <- 0 to (6)) yield { //Width
            controller.playground = controller.playground.copy(controller.playground.grid.replaceCellRisk(y, x, Cell(Chip.EMPTY)), controller.playground.player, controller.playground.enemStrat)
          }
        }
        controller.checkWinner(controller.playground)
      }
      "checking RED has won" in {
        controller.checkWinner(controller.playground)
        for (y <- 0 to (6)) yield { //Height
          for (x <- 0 to (6)) yield { //Width
            controller.playground = controller.playground.copy(controller.playground.grid.replaceCellRisk(y, x, Cell(Chip.RED)), controller.playground.player, controller.playground.enemStrat)
          }
        }
        controller.checkWinner(controller.playground)
      }
      "checking YELLOW has won" in {
        controller.checkWinner(controller.playground)
        for (y <- 0 to (6)) yield { //Height
          for (x <- 0 to (6)) yield { //Width
            controller.playground = controller.playground.copy(controller.playground.grid.replaceCellRisk(y, x, Cell(Chip.YELLOW)), controller.playground.player, controller.playground.enemStrat)
          }
        }
        controller.checkWinner(controller.playground)
      }
      "checking if the gameboard is full" in {
        for (y <- 0 to (6)) yield { //Height
          for (x <- 0 to (6)) yield { //Width
            controller.playground = controller.playground.copy(controller.playground.grid.replaceCellRisk(y, x, Cell(Chip.EMPTY)), controller.playground.player, controller.playground.enemStrat)
          }
        }
        controller.checkFull()
        for (y <- 0 to (6)) yield { //Height
          for (x <- 0 to (6)) yield { //Width
            controller.playground = controller.playground.copy(controller.playground.grid.replaceCellRisk(y, x, Cell(Chip.RED)), controller.playground.player, controller.playground.enemStrat)
          }
        }
        controller.checkFull()
      }*/
      "undo and redo a move" in {
        val injector = Guice.createInjector(new VierGewinntModule)
        val controller2 = injector.getInstance(classOf[ControllerInterface])
        var field = controller2.playground
        field = controller2.insChip(Move(0))
        field.grid.getCell(6, 0) should be(Cell(Chip.YELLOW))
        field = controller2.undo
        field.grid.getCell(6, 0) should be(gridBaseImpl.Cell(Chip.EMPTY))
        field = controller2.redo
        field.grid.getCell(6, 0) should be(gridBaseImpl.Cell(Chip.YELLOW))
      }
      "return grid size" in {
        controller.gridSize should be(7)
      }
    }
  }
}
