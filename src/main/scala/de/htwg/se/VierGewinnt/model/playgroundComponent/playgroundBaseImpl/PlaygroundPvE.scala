package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.playerComponent.PlayerInterface
import de.htwg.se.VierGewinnt.model.EnemyComputerStrategy
import de.htwg.se.VierGewinnt.model.gridComponent.{GridInterface, gridBaseImpl}
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Cell, Chip}
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface

case class PlaygroundPvE(val grid: GridInterface, val player: List[PlayerInterface]) extends PlaygroundTemplate:
  val enemStrat = EnemyComputerStrategy()
  override def insertChip(col: Int): PlaygroundInterface = enemStrat.insertChip(this, col)

  override def takeAwayChip(col: Int): PlaygroundInterface =
    PlaygroundPvE(grid.replaceCell(getDeletePosition(col), col, gridBaseImpl.Cell(Chip.EMPTY)).get, player.reverse)

