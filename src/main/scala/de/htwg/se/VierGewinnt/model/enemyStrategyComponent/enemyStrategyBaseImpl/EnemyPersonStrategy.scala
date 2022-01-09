package de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundTemplate

import scala.util.Failure
import scala.util.Success

case class EnemyPersonStrategy() extends EnemyStrategy {
  override def insertChip(pg: PlaygroundTemplate, col: Int): PlaygroundTemplate = {
    val returnGrid = pg.grid.replaceCell(pg.getPosition(col), col, Cell(pg.player(0).getChip()))
    returnGrid match {
      case Success(v) => playgroundBaseImpl.PlaygroundPvP(v, pg.player.reverse) // IF Success, return the new playground
      case Failure(_) => {
        // pg.error = "This column is full try another one"
        pg
      } // If Failure, return the old playground
    }
  }
}
