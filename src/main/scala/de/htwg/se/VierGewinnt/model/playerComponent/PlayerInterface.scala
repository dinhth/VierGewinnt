package de.htwg.se.VierGewinnt.model.playerComponent

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.{BotPlayer, HumanPlayer, Player}

trait PlayerInterface:
  def getChip():Chip
  def getName():String
  //def getHumanPlayer(name: String, chip: Chip): PlayerInterface = new HumanPlayer(name, chip)
  //def getBotPlayer(name: String, chip: Chip): PlayerInterface = new BotPlayer(name, chip)
