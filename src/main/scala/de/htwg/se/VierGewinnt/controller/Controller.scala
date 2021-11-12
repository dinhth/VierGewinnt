package de.htwg.se.VierGewinnt
package controller

import model.{Chip, Player, Playground}
import util.Observable

class Controller(var playground: Playground) extends Observable :
  def this(size: Int = 7) = this(new Playground(7))

  def insertChip(col: Int) =    
    playground = playground.insertChip(col)
    notifyObservers

  override def toString = playground.toString

