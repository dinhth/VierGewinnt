package de.htwg.se.VierGewinnt.model

import io.AnsiColor._

case class HumanPlayer(val name: String, val chip: Chip) extends Player {
  override def toString: String = s"${chip.getColorCode}$name${RESET}"
}
