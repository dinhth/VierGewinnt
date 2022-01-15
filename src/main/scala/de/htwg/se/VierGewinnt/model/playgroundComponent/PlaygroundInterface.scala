package de.htwg.se.VierGewinnt.model.playgroundComponent

import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.playerComponent.PlayerInterface

trait PlaygroundInterface:
  def grid: GridInterface
  def player: List[PlayerInterface]
  def size: Int
  var error: String = ""
  def insertChip(col: Int): PlaygroundInterface
  def takeAwayChip(col: Int): PlaygroundInterface
  def getDeletePosition(col: Int): Int
  def getPosition(col: Int): Int
  def getStatus(): String
  def colnames(): String
  def border(): String
