package de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyBaseImpl

import de.htwg.se.VierGewinnt.model.enemyStrategyComponent.EnemyStrategyInterface
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundTemplate

/** Strategy Pattern, which decides if you play against a computer or actual person
  */

trait EnemyStrategy extends EnemyStrategyInterface:
  def insertChip(playground: PlaygroundTemplate, col: Int): PlaygroundTemplate
