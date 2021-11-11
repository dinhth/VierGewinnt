package de.htwg.se.VierGewinnt
package controller

import model.{Chip, Player, Playground}
import util.Observable

case class Controller(var playground: Playground) extends Observable:
  def insertChip(col: Int, player: Player) =
    playground = playground.insertChip(col, player)
    notifyObservers
  override def toString = playground.toString

