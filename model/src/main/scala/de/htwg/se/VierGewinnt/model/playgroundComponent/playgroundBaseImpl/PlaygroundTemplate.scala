/** PlaygroundTemplate base implementation for VierGewinnt.
 *
 * @author Victor Gänshirt & Orkan Yücetag */
package de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.{Cell, Chip, Grid}
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.VierGewinnt.model.playgroundComponent.PlaygroundInterface
import play.api.libs.json.JsValue
import play.api.libs.json.{JsArray, JsValue, Json}

import scala.io.AnsiColor.{BLUE_B, RESET}

/** Template to create more playgrounds with different opponents.
 * Player vs Environment (PVE / Computer)
 * Player vs Player (PVP)
 * */
trait PlaygroundTemplate extends PlaygroundInterface {

  /** Returns the size of the playground. */
  override def size = grid.size

  /** Get the position on a specific column, where the most-top chip is placed. */
  override def getDeletePosition(col: Int): Int =
    var i = size - 1
    while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
    i += 1
    i

  /** Get the position one higher than the most-top chip. */
  override def getPosition(col: Int): Int =
    col match {
      case col if col > size => 0
      case col if col < 0 => 0
      case _ =>
        var i = size - 1
        while (i >= 0 && grid.getCell(i, col).value != Chip.EMPTY) i -= 1
        i
    }

  /** Build the playgorund together with status, column names, grid and borders. Then return it to a string. */
  override def toString: String =
    val box = getStatus() + "\n" + colnames() + grid + border()
    if (error != "") error else box // print the col is full error if needed

  /** Return the status of the playground Using hte Option-Monade
   * If noone was won yet, return next players turn string.
   * If someone one, return the string of who has won.
   * */
  override def getStatus(): String =
    grid.checkWin() match { //Option-Monade
      case None =>
        val box = "It's your turn " + player(0).getName()
        if (error != "") error else box // print the col is full error if needed
      case Some(num) =>
        if (num._1 == 1) //1 == Red, 2 == Yellow
          val box = "Player Red has won the game!"
          if (error != "") error else box // print the col is full error if needed
        else
          val box = "Player Yellow has won the game!"
          if (error != "") error else box // print the col is full error if needed
    }

  /** Return the column names of the playground. */
  override def colnames(): String = {
    val cols = for {
      n <- 1 to size
    } yield n
    s"${BLUE_B}\t" + cols.mkString("\t") + s"\t ${RESET}\n"
  }

  /** Return the border of the playground. */
  override def border(): String = {
    s"${BLUE_B}  " + ("----" * size) + s"-  ${RESET}\n"
  }
}

//TODO move the following functions into the right spot
def gameToJson: JsValue =
  Json.obj(
    //TODO refactor this fancy piece of code, with playground type ?
    "grid" -> Json.obj(
      "cells" -> Json.toJson(
        (0 until size - 4).flatMap(col =>
          (3 until size).reverse.map(row => {
            val player = getCell(row, col).chip match
              case Some(s) => s.player
              case None =>
            Json.obj(
              "row" -> row,
              "col" -> col,
              "value" -> player
            )
          }))
      )
    )
  )
override def toJsonString: String =
  Json.prettyPrint(gameToJson)

def toJson: JsValue =
  gameToJson

def jsonToGrid(player1: Player, player2: Player, par_grid: Grid, source: String): Grid =
  val gameJson: JsValue = Json.parse(source)
  val grid = (gameJson \ "grid")

  val cells = (grid \ "cells").as[JsArray]
  recursiveSetGrid(player1, player2, cells, 0, par_grid)


def recursiveSetGrid(player1: Player, player2: Player, cells: JsArray, idx: Int, grid: Grid): Grid =
  if cells.value.length == idx then
    return grid

  val cell = cells.value(idx)

  val row = (cell \ "row").get.as[Int]
  val col = (cell \ "col").get.as[Int]
  val value = (cell \ "value").get.as[Int]
  val optPiece = value match
    case 1 => Some(player1.chip)
    case 2 => Some(player2.chip)
    case _ => None
  recursiveSetGrid(player1, player2, cells, idx + 1, grid.replaceCell(row, col, Cell(optPiece)))
