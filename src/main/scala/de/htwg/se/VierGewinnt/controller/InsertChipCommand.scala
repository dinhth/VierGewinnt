package de.htwg.se.VierGewinnt
package controller

import model.Playground
import model.Move
import model.Chip
import util.Command
import util.UndoManager

class InsertChipCommand(move: Move) extends Command[Playground]:
  override def noStep(playground: Playground): Playground = playground
  override def doStep(playground: Playground): Playground = playground.insertChip(move.col)
  override def undoStep(playground: Playground): Playground = playground.takeAwayChip(move.col) //take away chip function
  override def redoStep(playground: Playground): Playground = playground.insertChip(move.col)
