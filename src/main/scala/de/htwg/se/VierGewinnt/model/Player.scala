package de.htwg.se.VierGewinnt.model

case class Player(name: String, chip: Chip) {
  override def toString: String = name
}
