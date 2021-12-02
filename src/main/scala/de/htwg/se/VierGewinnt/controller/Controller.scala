package de.htwg.se.VierGewinnt
package controller

import model.{Chip, Grid, Player, Playground, Move}
import util.Observable
import util.Command
import util.UndoManager

class Controller(var playground: Playground) extends Observable :
  def this(size: Int = 7) = this(new Playground(7))

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

  def checkFull():Unit =
    playground.grid.checkFull() match { //println just FOR DEBUG, DELETE LATER!!! (print ln ONLY in TUI)
      case true => println("Grid is COMPLETELY FULL")
      case false => println("Grid is not full")
    }

  def changeEnemyStrategy(strat:String):Unit =
    playground = playground.setEnemyStrategy(strat)

  def checkWinner(pg: Playground):Unit =
    pg.grid.checkWin() match {
      case None =>
      case Some(num) => println("Winner is " + num)
    }
  override def toString = playground.toString
