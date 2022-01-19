package de.htwg.se.VierGewinnt.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.{Named, Names}
import com.google.inject.{Guice, Inject, Key}
import de.htwg.se.VierGewinnt.VierGewinntModule
import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Chip, Grid}
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.{BotPlayer, HumanPlayer, Player}
import de.htwg.se.VierGewinnt.model.playerComponent.{PlayerInterface, playerBaseImpl}
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.{PlaygroundPvE, PlaygroundPvP}
import de.htwg.se.VierGewinnt.model.playgroundComponent.{PlaygroundInterface, playgroundBaseImpl}
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
        playground = new PlaygroundPvP(size)
      case 1 =>
        playground = new PlaygroundPvE(size)
    gamestate.changeState(PlayState())
    notifyObservers

  val undoManager = new UndoManager[PlaygroundInterface]

  override def doAndPublish(doThis: Move => PlaygroundInterface, move: Move): Unit =
    if (gameNotDone)
      playground = doThis(move)
      notifyObservers

  override def restartGame: Unit =
    gamestate.changeState(PrepareState())
    notifyObservers

  override def isPreparing : Boolean =
    gamestate.state.equals(PrepareState())

  override def gameNotDone : Boolean =
    !(gamestate.state.equals(WinState()) || gamestate.state.equals(TieState()))

  override def doAndPublish(doThis: => PlaygroundInterface): Unit =
    if (gameNotDone)
      playground = doThis
      notifyObservers

  override def undo: PlaygroundInterface = undoManager.undoStep(playground)

  override def redo: PlaygroundInterface = undoManager.redoStep(playground)

  override def insChip(move: Move): PlaygroundInterface = {
    val temp = undoManager.doStep(playground, InsertChipCommand(move))
    checkWinner(temp)
    checkFull(temp)
    temp
  }

  override def checkFull(pg: PlaygroundInterface): Unit =
    pg.grid.checkFull() match {
      case true => if (gameNotDone) { gamestate.changeState(TieState()) }
      case false => if (gameNotDone) { gamestate.changeState(PlayState()) }
    }

  override def checkWinner(pg: PlaygroundInterface): Unit =
    pg.grid.checkWin() match {
      case None =>
      case Some(num) => //1 == Red, 2 == Yellow
        gamestate.changeState(WinState())
    }

  override def getChipColor(row: Int, col: Int): String =
    playground.grid.getCell(row, col).chip.getColorCode

  override def printState: String = gamestate.displayState()

  override def playgroundState: String = playground.getStatus()

  override def toString = playground.toString
