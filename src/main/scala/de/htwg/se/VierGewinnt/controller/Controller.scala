package de.htwg.se.VierGewinnt
package controller

import model.{BotPlayer, Chip, EnemyComputerStrategy, EnemyPersonStrategy, Grid, HumanPlayer, Player, Playground}
import util.Observable

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

  def insertChip(col: Int): Unit =
    playground = playground.insertChip(col)
    checkFull()
    checkWinner()
    notifyObservers

  def checkFull(): Unit =
    playground.grid.checkFull() match {
      case true => gamestate.changeState(TieState())
      case false => gamestate.changeState(PlayState())
    }

  def changeEnemyStrategy(strat: String): Unit =
    playground = playground.setEnemyStrategy(strat)

  def checkWinner(): Unit =
    playground.grid.checkWin() match {
      case 0 =>
      case 1 => {
        println("Winner is Red")
        gamestate.changeState(WinState())
      }
      case 2 => {
        println("Winner is Yellow")
        gamestate.changeState(WinState())
      }
    }

  def printState: Unit = gamestate.displayState()

  override def toString = playground.toString
