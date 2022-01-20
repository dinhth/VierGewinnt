package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface

import scala.io.AnsiColor.{BLUE_B, RESET}

trait PlaygroundTemplate extends PlaygroundInterface {

  override def size = grid.size

  override def getDeletePosition(col: Int): Int = { // get the position where the chip should drop
    var i = size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i += 1
    i
  }

  override def getPosition(col: Int): Int = { // get the position where the chip should drop
    col match {
      case col if col > size => 0
      case col if col < 0 => 0
      case _ =>
        var i = size - 1
        while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
        i
    }
  }

  override def toString: String = {
    val box = getStatus() + "\n" + colnames() + grid + border()
    return if (error != "") error else box // print the col is full error if needed
  }

  override def getStatus(): String = {
    grid.checkWin() match { //Option-Monade
      case None =>
        val box = "It's your turn " + player(0).getName()
        return if (error != "") error else box // print the col is full error if needed
      case Some(num) =>
        if (num == 1) //1 == Red, 2 == Yellow
          val box = "Player Red has won the game!"
          return if (error != "") error else box // print the col is full error if needed
        else
          val box = "Player Yellow has won the game!"
          return if (error != "") error else box // print the col is full error if needed
    }
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
