/** EnemyStrategyInterface for VierGewinnt.
 *
 * @author Thu Ha Dinh & Orkan Yücetag */
package de.htwg.se.VierGewinnt.model.enemyStrategyComponent

import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundTemplate

/** Interface of the EnemyStrategy. */
trait EnemyStrategyInterface {
  /** Insert a chip and return the new playground. */
  def insertChip(playground: PlaygroundTemplate, col: Int): PlaygroundTemplate
}
