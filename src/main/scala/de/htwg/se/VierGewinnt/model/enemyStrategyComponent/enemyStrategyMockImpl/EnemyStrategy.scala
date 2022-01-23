/** EnemyStrategy mock implementation for VierGewinnt.
 *
 * @author Thu Ha Dinh & Orkan Yücetag */
package de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyMockImpl

import de.htwg.se.VierGewinnt.model.enemyStrategyComponent.EnemyStrategyInterface
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundTemplate

class EnemyStrategy extends EnemyStrategyInterface:
  override def insertChip(playground: PlaygroundTemplate, col: Int): PlaygroundTemplate = playground
