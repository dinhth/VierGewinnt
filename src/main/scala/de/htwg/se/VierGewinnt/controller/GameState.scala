package de.htwg.se.VierGewinnt.controller

import de.htwg.se.VierGewinnt.util.State

case class GameState() {
  var state: State[GameState] = IdleState()

  def displayState(): Unit = state.displayState
}

case class IdleState() extends State[GameState] {
  override def displayState: Unit = println("Game is on")
}

case class WinState() extends State[GameState] {
  override def displayState: Unit = println("Game is won")
}

case class TieState() extends State[GameState] {
  override def displayState: Unit = println("Game ends with a tie")
}
