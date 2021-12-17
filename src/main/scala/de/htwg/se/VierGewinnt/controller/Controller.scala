package de.htwg.se.VierGewinnt
package controller

import de.htwg.se.VierGewinnt.model.*
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Chip, Grid}
import de.htwg.se.VierGewinnt.model.playerComponent.{PlayerInterface, playerBaseImpl}
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.{BotPlayer, HumanPlayer, Player}
import de.htwg.se.VierGewinnt.model.playgroundComponent.{PlaygroundInterface, playgroundBaseImpl}
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.{PlaygroundPvE, PlaygroundPvP}
import de.htwg.se.VierGewinnt.util.{Command, Observable, UndoManager}

class Controller(var playground: PlaygroundInterface, var gameType: Int) extends Observable:
  def this(size: Int = 7) =
    this(
      playgroundBaseImpl
        .PlaygroundPvP(new Grid(size), List(HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED))),
      0
    )
  var gamestate: GameState = GameState()
  var player: List[PlayerInterface] = List()

  def gridSize: Int = playground.size
  def setupGame(gameType: Int, size: Int): Unit =
    gameType match
      case 0 =>
        player = List(playerBaseImpl.HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED))
        playground = playgroundBaseImpl.PlaygroundPvP(new Grid(size), player)
      case 1 =>
        player = List(
          HumanPlayer("Player 1", Chip.YELLOW),
          BotPlayer("Bot 1", Chip.RED)
        )

        playground = PlaygroundPvE(new Grid(size), player)
    gamestate.changeState(PlayState())
    notifyObservers

  val undoManager = new UndoManager[PlaygroundInterface]

  def doAndPublish(doThis: Move => PlaygroundInterface, move: Move): Unit =
    playground = doThis(move)
    notifyObservers

  def doAndPublish(doThis: => PlaygroundInterface): Unit =
    playground = doThis
    notifyObservers

  def undo: PlaygroundInterface = undoManager.undoStep(playground)

  def redo: PlaygroundInterface = undoManager.redoStep(playground)

  def insChip(move: Move): PlaygroundInterface = {
    val temp = undoManager.doStep(playground, InsertChipCommand(move))
    checkWinner(temp)
    checkFull()
    temp
  }

  def checkFull(): Unit =
    playground.grid.checkFull() match {
      case true  => gamestate.changeState(TieState())
      case false => gamestate.changeState(PlayState())
    }

  def checkWinner(pg: PlaygroundInterface): Unit =
    pg.grid.checkWin() match {
      case None =>
      case Some(num) =>
        println("Winner is " + num)
        gamestate.changeState(WinState())
    }

  def getChipColor(row: Int, col: Int): Chip =
    playground.grid.getCell(row, col).chip

  def printState: Unit = gamestate.displayState()

  override def toString = playground.toString
