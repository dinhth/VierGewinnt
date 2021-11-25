package de.htwg.se.VierGewinnt.util

import de.htwg.se.VierGewinnt.model.Playground

/**
 * Strategy Pattern, which decides if you play against a computer or actual person
 *
 */

trait EnemyStrategy {
  def insertChip(test:Playground, col:Int):Playground
}