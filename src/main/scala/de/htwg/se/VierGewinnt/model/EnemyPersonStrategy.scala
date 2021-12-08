package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.util.EnemyStrategy

import scala.util.{Failure, Success}

case class EnemyPersonStrategy() extends EnemyStrategy {
  override def insertChip(pg:PlaygroundTemplate, col: Int): PlaygroundTemplate = {
    if (pg.getPosition(col) != -1)
      val returnGrid = pg.grid.replaceCell(pg.getPosition(col), col, Cell(pg.player(0).chip))
      returnGrid match {
        case Success(v) => PlaygroundPvP(v, pg.player.reverse) //IF Success, return the new playground
        case Failure(_) => pg //If Failure, return the old playground
      }
    else
      pg.error = "This column is full try another one"
      pg
  }
}
