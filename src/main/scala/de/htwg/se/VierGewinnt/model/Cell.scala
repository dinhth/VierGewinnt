package de.htwg.se.VierGewinnt.model
import io.AnsiColor.*

case class Cell(value: Chip = Chip.EMPTY) {
  override def toString: String = s"${value.getColorCode}" + s"   "
}

/** instead of an int value give the chips a more readable and useful structure */
enum Chip(value: Int, colorCode: String):
  def getValue: Int = value
  def getColorCode: String = colorCode

  case EMPTY extends Chip(0, BLUE_B)
  case RED extends Chip(1, RED_B)
  case YELLOW extends Chip(2, YELLOW_B)






