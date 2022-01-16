package de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyMockImpl

import de.htwg.se.VierGewinnt.model.enemyStrategyComponent.enemyStrategyMockImpl
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Chip, Grid}
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.PlaygroundTemplate
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class EnemyStrategySpec extends AnyWordSpec {
  "An EnemyStrategySpec Mock Implementation" when {
    "initialized" should {
      val enemStrat = new EnemyStrategy
      val playtemp = playgroundBaseImpl.PlaygroundPvP(new Grid(7), List(playerBaseImpl.HumanPlayer("Player 1", Chip.YELLOW), playerBaseImpl.HumanPlayer("Player 2", Chip.RED)))
      "return playground" in {
        enemStrat.insertChip(playtemp, 0) should be (playtemp)
      }
    }
  }
}
