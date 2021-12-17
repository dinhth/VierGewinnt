package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyBaseImpl.EnemyComputerStrategy
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.playerComponent.PlayerInterface
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface

case class PlaygroundPvE(val grid: GridInterface, val player: List[PlayerInterface]) extends PlaygroundTemplate:
  val enemStrat = EnemyComputerStrategy()
  override def insertChip(col: Int): PlaygroundInterface = enemStrat.insertChip(this, col)

  override def takeAwayChip(col: Int): PlaygroundInterface =
    PlaygroundPvE(grid.removeCell(getDeletePosition(col), col).get, player.reverse)
