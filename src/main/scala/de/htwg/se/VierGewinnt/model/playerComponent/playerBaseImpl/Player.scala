package de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.playerComponent.PlayerInterface

trait Player() extends PlayerInterface:
  val name: String
  val chip: Chip

  override def getChip(): Chip = chip

  override def getName(): String = name
