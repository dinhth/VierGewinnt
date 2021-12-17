package de.htwg.se.VierGewinnt.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.VierGewinnt.controller.*
import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface
import de.htwg.se.VierGewinnt.model.*
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.BotPlayer
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.HumanPlayer
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.VierGewinnt.model.playerComponent.PlayerInterface
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundPvE
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundPvP
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface
import de.htwg.se.VierGewinnt.util.{Command, Move, Observable, UndoManager}

class Controller(var playground: PlaygroundInterface, var gameType: Int) extends Observable with ControllerInterface:
  def this(size: Int = 7) =
    this(
      playgroundBaseImpl
        .PlaygroundPvP(new Grid(size), List(HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED))),
      0
    )

  override def gridSize: Int = playground.size
  override def setupGame(gameType: Int, size: Int): Unit =
    gameType match
      case 0 =>
        player = List(playerBaseImpl.HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED))
        playground = PlaygroundPvP(new Grid(size), player)
      case 1 =>
        player = List(
          HumanPlayer("Player 1", Chip.YELLOW),
          BotPlayer("Bot 1", Chip.RED)
        )

        playground = PlaygroundPvE(new Grid(size), player)
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
      case true  => gamestate.changeState(TieState())
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
