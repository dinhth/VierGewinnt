package de.htwg.se.VierGewinnt
package controller

import model.{Chip, Grid, Player, Playground, Move}
import util.Observable
import util.Command
import util.UndoManager

class Controller(var playground: Playground, var gameType: Int)extends Observable :
  def this(size: Int = 7) = this(new Playground(7), 0)

  var gamestate: GameState = GameState()
  var player: List[Player] = List()

  def newGame(gameType: Int, size: Int): Unit =
    playground = new Playground(size)
    setupPlayers(gameType)
    gamestate.changeState(PlayState())
    notifyObservers


  private def setupPlayers(gameType: Int) =
    gameType match
      case 0 => playground = playground.setEnemyStrategy("person")
        player = List(HumanPlayer("Player 1", Chip.YELLOW), HumanPlayer("Player 2", Chip.RED))
      case 1 => playground = playground.setEnemyStrategy("bot")
        player = List(HumanPlayer("Player 1", Chip.YELLOW), BotPlayer("Bot 1", Chip.RED, EnemyComputerStrategy()))

  val undoManager = new UndoManager[Playground]

  def doAndPublish(doThis: Move => Playground, move: Move) =
    playground = doThis(move)
    notifyObservers

  def doAndPublish(doThis: => Playground) =
    playground = doThis
    notifyObservers

  def undo: Playground = undoManager.undoStep(playground)

  def redo: Playground = undoManager.redoStep(playground)

  def insChip(move: Move): Playground = {
    val temp = undoManager.doStep(playground, InsertChipCommand(move))
    checkWinner(temp)
    checkFull()
    temp
  }

  def checkFull(): Unit =
    playground.grid.checkFull() match {
      case true => gamestate.changeState(TieState())
      case false => gamestate.changeState(PlayState())
    }

  def changeEnemyStrategy(strat: String): Unit =
    playground = playground.setEnemyStrategy(strat)

  def checkWinner(pg: Playground): Unit =
    pg.grid.checkWin() match {
      case None =>
      case Some(num) => println("Winner is " + num)
        gamestate.changeState(WinState())
    }

  def printState: Unit = gamestate.displayState()

  override def toString = playground.toString
