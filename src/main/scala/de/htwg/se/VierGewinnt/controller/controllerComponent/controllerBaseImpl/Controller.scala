package de.htwg.se.VierGewinnt.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Key}
import com.google.inject.name.{Named, Names}
import de.htwg.se.VierGewinnt.VierGewinntModule
import de.htwg.se.VierGewinnt.controller.*
import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface
import de.htwg.se.VierGewinnt.model.*
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Chip, Grid}
import de.htwg.se.VierGewinnt.model.playerComponent.{PlayerInterface, playerBaseImpl}
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.{BotPlayer, HumanPlayer, Player}
import de.htwg.se.VierGewinnt.model.playgroundComponent.{PlaygroundInterface, playgroundBaseImpl}
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.{PlaygroundPvE, PlaygroundPvP}
import de.htwg.se.VierGewinnt.util.{Command, Move, Observable, UndoManager}

class Controller @Inject()(@Named("DefaultPlayground") var playground: PlaygroundInterface, @Named("DefaultGameType") var gameType: Int) extends Observable with ControllerInterface :
  /*def this(size: Int = 7) =
    this(playgroundBaseImpl.PlaygroundPvP(new Grid(size), List(HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED))),
      0)*/

  val injector = Guice.createInjector(new VierGewinntModule)

  override def gridSize: Int = playground.size

  override def setupGame(gameType: Int, size: Int): Unit =
    gameType match
      case 0 =>
        playground = injector.getInstance(Key.get(classOf[PlaygroundInterface], Names.named("PvP")))
      case 1 =>
        playground = injector.getInstance(Key.get(classOf[PlaygroundInterface], Names.named("PvE")))
    gamestate.changeState(PlayState())
    notifyObservers

  val undoManager = new UndoManager[PlaygroundInterface]

  override def doAndPublish(doThis: Move => PlaygroundInterface, move: Move): Unit =
    playground = doThis(move)
    notifyObservers

  override def doAndPublish(doThis: => PlaygroundInterface): Unit =
    playground = doThis
    notifyObservers

  override def undo: PlaygroundInterface = undoManager.undoStep(playground)

  override def redo: PlaygroundInterface = undoManager.redoStep(playground)

  override def insChip(move: Move): PlaygroundInterface = {
    val temp = undoManager.doStep(playground, InsertChipCommand(move))
    checkWinner(temp)
    checkFull()
    temp
  }

  override def checkFull(): Unit =
    playground.grid.checkFull() match {
      case true => gamestate.changeState(TieState())
      case false => gamestate.changeState(PlayState())
    }

  override def checkWinner(pg: PlaygroundInterface): Unit =
    pg.grid.checkWin() match {
      case None =>
      case Some(num) =>
        println("Winner is " + num)
        gamestate.changeState(WinState())
    }

  override def getChipColor(row: Int, col: Int): Chip =
    playground.grid.getCell(row, col).chip

  override def printState: Unit = gamestate.displayState()

  override def toString = playground.toString
