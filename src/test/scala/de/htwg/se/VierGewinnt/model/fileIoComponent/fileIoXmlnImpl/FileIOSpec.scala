package de.htwg.se.VierGewinnt.model.fileIoComponent.fileIoXmlnImpl

import de.htwg.se.VierGewinnt.model.gridComponent
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.*
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.*

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.io.Source

class FileIOSpec extends AnyWordSpec {
  "A File IO Xml Implementation" when {
    val fileIo = new FileIO
    "when used on a PVP playground" should {
      "save" in {
        val playground = playgroundBaseImpl.PlaygroundPvP(new Grid(7), List(HumanPlayer("Player 1", Chip.YELLOW), HumanPlayer("Player 2", Chip.RED)))
        val playground2 = playground.insertChip(0)
        val playground3 = playground.insertChip(1)

        fileIo.save(playground3)
        val result = Source.fromFile("playground.xml").getLines().mkString

        result should include("<playground")
        result should include(s"size=\"7\"")
        result should include(s"gameType=\"0\"")
      }

      "load" in {
        val playground = playgroundBaseImpl.PlaygroundPvP(new Grid(7), List(HumanPlayer("Player 1", Chip.YELLOW), HumanPlayer("Player 2", Chip.RED)))
        val playground2 = playground.insertChip(0)
        val playground3 = playground.insertChip(1)

        fileIo.save(playground3)
        fileIo.load.size should be(7)
        fileIo.load.toString should include(playground3.toString)
      }
    }

    "when used on a PVE playground" should {
      "save" in {
        val playground = playgroundBaseImpl.PlaygroundPvE(new Grid(7), List(HumanPlayer("Player 1", Chip.YELLOW), BotPlayer("Bot 1", Chip.RED)))
        val playground2 = playground.insertChip(0)
        val playground3 = playground.insertChip(1)

        fileIo.save(playground3)
        val result = Source.fromFile("playground.xml").getLines().mkString

        result should include("<playground")
        result should include(s"size=\"7\"")
        result should include(s"gameType=\"1\"")
      }

      "load" in {
        val playground = playgroundBaseImpl.PlaygroundPvE(new Grid(7), List(HumanPlayer("Player 1", Chip.YELLOW), BotPlayer("Bot 1", Chip.RED)))
        val playground2 = playground.insertChip(0)
        val playground3 = playground.insertChip(1)

        fileIo.save(playground3)
        fileIo.load.size should be(7)
        fileIo.load.toString should include(playground3.toString)
      }
    }
  }
}
