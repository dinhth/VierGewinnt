package de.htwg.se.VierGewinnt
package controller

import model.{Chip, Grid, Player, Playground}
import util.Observable

class Controller(var playground: Playground)extends Observable :
  def this(size: Int = 7) = this(new Playground(7))

  var gamestate: GameState = GameState()

  def insertChip(col: Int): Unit =
    playground = playground.insertChip(col)
    checkFull()
    checkWinner()
    notifyObservers

  def checkFull(): Unit =
    playground.grid.checkFull() match {
      case true => gamestate.state = TieState()
      case false => gamestate.state = IdleState()
    }

  def changeEnemyStrategy(strat: String): Unit =
    playground = playground.setEnemyStrategy(strat)

  def checkWinner(): Unit =
    playground.grid.checkWin() match {
      case 0 =>
      case 1 => {
        println("Winner is Red")
        gamestate.state = WinState()
      }
      case 2 => {
        println("Winner is Yellow")
        gamestate.state = WinState()
      }
    }

  def printState(): Unit = gamestate.displayState()

  override def toString = playground.toString
