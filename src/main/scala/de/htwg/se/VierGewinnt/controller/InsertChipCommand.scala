package de.htwg.se.VierGewinnt
package controller

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl
import util.Command
import util.UndoManager

class InsertChipCommand(move: Move) extends Command[PlaygroundInterface]:
  override def noStep(playground: PlaygroundInterface): PlaygroundInterface = playground
  override def doStep(playground: PlaygroundInterface): PlaygroundInterface = playground.insertChip(move.col)
  override def undoStep(playground: PlaygroundInterface): PlaygroundInterface = playground.takeAwayChip(move.col) //take away chip function
  override def redoStep(playground: PlaygroundInterface): PlaygroundInterface = playground.insertChip(move.col)
