package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.playerComponent.PlayerInterface
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface

import scala.io.AnsiColor.BLUE_B
import scala.io.AnsiColor.RESET

trait PlaygroundTemplate extends PlaygroundInterface {

  override def size = grid.size

  override def getDeletePosition(col: Int): Int = { // get the position where the chip should drop
    var i = size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i += 1
    i match {
      case size => size - 1
      case _    => i
    }
  }

  override def getPosition(col: Int): Int = { // get the position where the chip should drop
    var i = size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i
  }

  override def toString: String = {
    val box = "It's your turn " + player(0) + "\n" + colnames() + grid + border()
    return if (error != "") error else box // print the col is full error if needed
  }

  override def colnames(): String = {
    val cols = for {
      n <- 1 to size
    } yield n
    return s"${BLUE_B}\t" + cols.mkString("\t") + s"\t ${RESET}\n"
  }

  override def border(): String = {
    return s"${BLUE_B}  " + ("----" * size) + s"-  ${RESET}\n"
  }

}
