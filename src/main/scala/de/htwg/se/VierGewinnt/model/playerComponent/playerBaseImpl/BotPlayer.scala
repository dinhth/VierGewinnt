package de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip

import scala.io.AnsiColor.RESET

class BotPlayer(val name: String, val chip: Chip) extends Player:
  
  override def toString: String = s"${chip.getColorCode}$name${RESET}"
