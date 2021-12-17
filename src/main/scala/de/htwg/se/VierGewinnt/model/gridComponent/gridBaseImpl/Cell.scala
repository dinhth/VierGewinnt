package de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl

case class Cell(value: Chip = Chip.EMPTY) {
  val chip: Chip = value

  override def toString: String = s"${value.getColorCode}" + s"   "
}
