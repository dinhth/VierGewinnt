package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.util.EnemyStrategy

import io.AnsiColor._

case class BotPlayer(val name: String, val chip: Chip) extends Player {

  override def toString: String = s"${chip.getColorCode}$name${RESET}"
}
