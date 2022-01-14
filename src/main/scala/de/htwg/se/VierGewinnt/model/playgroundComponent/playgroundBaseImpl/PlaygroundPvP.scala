package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyBaseImpl.EnemyPersonStrategy
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Chip, Grid}
import de.htwg.se.VierGewinnt.model.gridComponent.{GridInterface, gridBaseImpl}
import de.htwg.se.VierGewinnt.model.playerComponent.{PlayerInterface, playerBaseImpl}
import de.htwg.se.VierGewinnt.model.playgroundComponent.{PlaygroundInterface, playgroundBaseImpl}

case class PlaygroundPvP(val grid: GridInterface, val player: List[PlayerInterface]) extends PlaygroundTemplate {
  def this(size: Int = 7) = this(new Grid(size), List(playerBaseImpl.HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED)))

  val enemStrat = EnemyPersonStrategy()

  override def insertChip(col: Int): PlaygroundInterface = enemStrat.insertChip(this, col)

  override def takeAwayChip(col: Int): PlaygroundInterface =
    PlaygroundPvP(grid.removeCell(getDeletePosition(col), col).get, player.reverse)
}
