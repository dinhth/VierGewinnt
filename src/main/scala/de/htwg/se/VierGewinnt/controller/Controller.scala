package de.htwg.se.VierGewinnt
package controller

import model.{Chip, Grid, Player, Playground}
import util.Observable

class Controller(var playground: Playground) extends Observable :
  def this(size: Int = 7) = this(new Playground(7))

  def insertChip(col: Int):Unit =
    playground = playground.insertChip(col)
    checkFull()
    checkWinner()
    notifyObservers

  def checkFull():Unit =
    playground.grid.checkFull() match { //println just FOR DEBUG, DELETE LATER!!! (print ln ONLY in TUI)
      case true => println("Grid is COMPLETELY FULL")
      case false => println("Grid is not full")
    }

  def changeEnemyStrategy(strat:String):Unit =
    playground = playground.setEnemyStrategy(strat)

  def checkWinner():Unit =
    playground.grid.checkWin() match {//println just FOR DEBUG, DELETE LATER!!! (print ln ONLY in TUI)
      case 0 =>
      case 1 => println("Winner is Red")
      case 2 => println("Winner is Yellow")
    }
  override def toString = playground.toString
