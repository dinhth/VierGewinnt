package de.htwg.se.VierGewinnt.model

import io.AnsiColor._

case class Player(name: String, chip: Chip) {
  override def toString: String = s"${chip.getColorCode}$name${RESET}"
}
