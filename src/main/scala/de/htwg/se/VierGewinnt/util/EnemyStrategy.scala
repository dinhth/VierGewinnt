package de.htwg.se.VierGewinnt.util

import de.htwg.se.VierGewinnt.model.PlaygroundTemplate

/** Strategy Pattern, which decides if you play against a computer or actual person
  */

trait EnemyStrategy {
  def insertChip(playground: PlaygroundTemplate, col: Int): PlaygroundTemplate
}
