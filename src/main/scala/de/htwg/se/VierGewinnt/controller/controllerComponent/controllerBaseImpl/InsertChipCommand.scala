package de.htwg.se.VierGewinnt.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.playgroundComponent.{PlaygroundInterface, playgroundBaseImpl}
import de.htwg.se.VierGewinnt.util.{Command, Move}

class InsertChipCommand(move: Move) extends Command[PlaygroundInterface]:
  override def noStep(playground: PlaygroundInterface): PlaygroundInterface = playground
  override def doStep(playground: PlaygroundInterface): PlaygroundInterface = playground.insertChip(move.col)
  override def undoStep(playground: PlaygroundInterface): PlaygroundInterface = playground.takeAwayChip(move.col) //take away chip function
  override def redoStep(playground: PlaygroundInterface): PlaygroundInterface = playground.insertChip(move.col)
