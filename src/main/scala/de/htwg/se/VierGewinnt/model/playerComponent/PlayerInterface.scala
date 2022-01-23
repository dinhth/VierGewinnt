package de.htwg.se.VierGewinnt.model.playerComponent

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.{BotPlayer, HumanPlayer, Player}

trait PlayerInterface:
  def getChip():Chip
  def getName():String
