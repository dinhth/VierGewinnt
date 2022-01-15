package de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundPvE
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundTemplate
import scala.util.Failure
import scala.util.Random
import scala.util.Success

case class EnemyComputerStrategy() extends EnemyStrategy {

  override def insertChip(pg: PlaygroundTemplate, col: Int): PlaygroundTemplate = {
    var temp =
      pg // Temporary Playground, only for EnemyComputer, because of the Full Grid check. If Grid Full, Computer does NOT play anymore.
    val returnGrid = pg.grid.replaceCell(pg.getPosition(col), col, Cell(pg.player(0).getChip()))
    returnGrid match {
      case Success(v) => temp = PlaygroundPvE(v, pg.player.reverse) // IF Success, return the new playground
      case Failure(_) =>
        temp = {
          pg.error = "This column is full try another one"
          pg
        } // If Failure, return the old playground
    }
    temp.grid.checkFull() match {
      case false if ((temp.grid.checkWin() == None) && temp != pg) =>
        ComputerinsertChip(temp) // If temp != pg means change was a success, if failure, try a new move
      case _ => temp
    }
  }

  def ComputerinsertChip(pg: PlaygroundTemplate): PlaygroundTemplate = {
    var chosenCol = Random.between(0, pg.size)
    for (i <- 0 to pg.size - 1)
      if (pg.getPosition(chosenCol) == -1) {
        chosenCol = i
      }

    val returnGrid = pg.grid.replaceCell(pg.getPosition(chosenCol), chosenCol, gridBaseImpl.Cell(pg.player(0).getChip()))
    returnGrid match {
      case Success(v) => playgroundBaseImpl.PlaygroundPvE(v, pg.player.reverse) // IF Success, return the new playground
      case Failure(_) => ComputerinsertChip(pg) // If Failure, retry inserting a chip!
    }
  }
}
