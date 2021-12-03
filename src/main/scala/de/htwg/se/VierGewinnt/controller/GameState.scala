package de.htwg.se.VierGewinnt.controller

import de.htwg.se.VierGewinnt.util.State

case class GameState() {
  var state: State[GameState] = PrepareState()

  def displayState(): Unit = state.displayState

  def changeState(state: State[GameState]): Unit = this.state = state
}

case class PrepareState()extends State[GameState] :
  override def displayState: Unit = println("Game is setting up")


case class PlayState()extends State[GameState] :
  override def displayState: Unit = println("Game is on")


case class WinState()extends State[GameState] :
  override def displayState: Unit = println("Game is won")


case class TieState()extends State[GameState] :
  override def displayState: Unit = println("Game ends with a tie")


case class EndState()extends State[GameState] :
  override def displayState: Unit = println("Game over. Restart?")

