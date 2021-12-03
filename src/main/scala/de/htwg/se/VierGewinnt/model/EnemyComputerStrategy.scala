package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.util.EnemyStrategy

import scala.util.{Failure, Random, Success}

case class EnemyComputerStrategy() extends EnemyStrategy {
  override def insertChip(pg:Playground, col: Int): Playground = {
    if (pg.getPosition(col) != -1)
      var temp = pg //Temporary Playground, only for EnemyComputer, because of the Full Grid check. If Grid Full, Computer does NOT play anymore.
      val returnGrid = pg.grid.replaceCell(pg.getPosition(col), col, Cell(pg.player(0).chip))
      returnGrid match {
        case Success(v) => temp = pg.copy(v, pg.player.reverse, pg.enemStrat) //IF Success, return the new playground
        case Failure(_) => temp = pg //If Failure, return the old playground
      }
      temp.grid.checkFull() match {
        case false if ((temp.grid.checkWin() == None) && temp != pg) => ComputerinsertChip(temp) //If temp != pg means change was a success, if failure, try a new move
        case _ => temp
      }
    else
      pg.error = "This column is full try another one"
      pg
  }

  def ComputerinsertChip(pg:Playground): Playground = {
    var chosenCol = Random.between(0,pg.size)

    for (i <- 0 to pg.size - 1)
      if (pg.getPosition(chosenCol) != -1) {
        chosenCol = i
      }

    val returnGrid = pg.grid.replaceCell(pg.getPosition(chosenCol), chosenCol, Cell(pg.player(0).chip))
    returnGrid match {
      case Success(v) => pg.copy(v, pg.player.reverse, pg.enemStrat) //IF Success, return the new playground
      case Failure(_) => ComputerinsertChip(pg) //If Failure, retry inserting a chip!
    }
  }
}
