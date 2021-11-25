package de.htwg.se.VierGewinnt.model

import de.htwg.se.VierGewinnt.util.EnemyStrategy

case class EnemyPersonStrategy() extends EnemyStrategy {
  override def insertChip(pg:Playground, col: Int): Playground = {
    if (pg.getPosition(col) != -1)
      pg.copy(pg.grid.replaceCell(pg.getPosition(col), col, Cell(pg.player(0).chip)), pg.player.reverse, pg.enemStrat)
    else
      pg.error = "This column is full try another one"
      pg
  }
}