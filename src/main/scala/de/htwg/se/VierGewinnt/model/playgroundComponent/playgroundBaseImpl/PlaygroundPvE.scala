package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyBaseImpl.EnemyComputerStrategy
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Chip, Grid}
import de.htwg.se.VierGewinnt.model.gridComponent.{GridInterface, gridBaseImpl}
import de.htwg.se.VierGewinnt.model.playerComponent.{PlayerInterface, playerBaseImpl}
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface

case class PlaygroundPvE(val grid: GridInterface, val player: List[PlayerInterface]) extends PlaygroundTemplate :
  def this(size: Int = 7) = this(new Grid(size), List(playerBaseImpl.HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.BotPlayer("Bot 1", Chip.RED)))
  val enemStrat = EnemyComputerStrategy()

  override def insertChip(col: Int): PlaygroundInterface = enemStrat.insertChip(this, col)

  override def takeAwayChip(col: Int): PlaygroundInterface =
    PlaygroundPvE(grid.removeCell(getDeletePosition(col), col).get, player.reverse)
