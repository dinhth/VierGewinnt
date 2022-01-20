package de.htwg.se.VierGewinnt.model.enemyStrategyComponent

import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundTemplate

trait EnemyStrategyInterface {
  def insertChip(playground: PlaygroundTemplate, col: Int): PlaygroundTemplate
}
