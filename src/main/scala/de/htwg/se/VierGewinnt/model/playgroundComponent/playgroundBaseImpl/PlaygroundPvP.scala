package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyBaseImpl.EnemyPersonStrategy
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.playerComponent.PlayerInterface
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface

case class PlaygroundPvP(val grid: GridInterface, val player: List[PlayerInterface]) extends PlaygroundTemplate {
  val enemStrat = EnemyPersonStrategy()
  override def insertChip(col: Int): PlaygroundInterface = enemStrat.insertChip(this, col)

  override def takeAwayChip(col: Int): PlaygroundInterface =
    PlaygroundPvP(grid.removeCell(getDeletePosition(col), col).get, player.reverse)
}
