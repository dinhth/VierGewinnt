package de.htwg.se.VierGewinnt.model

import io.AnsiColor._
import scala.math._

case class Playground(grid: Grid) {

  def this(size: Int = 7) = this(new Grid(size))

  val size: Int = grid.size


  override def toString: String = {
    val box = colnames() + grid + border()
    return box
  }

  def colnames(): String = {
    val cols = for {
      n <- 1 to size
    } yield n
    return s"${BLUE_B}\t" + cols.mkString("\t") + s"\t ${RESET}\n"
  }

  def border(): String = {
    return s"${BLUE_B}  " + ("----" * size) + s"-  ${RESET}\n"
  }
}
