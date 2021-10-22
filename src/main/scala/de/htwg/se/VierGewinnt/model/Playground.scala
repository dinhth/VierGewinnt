package de.htwg.se.VierGewinnt.model

import io.AnsiColor._
import scala.math._

class Playground(size: Int) {
  override def toString: String = {
    val titles = for {
      n <- 1 to size
    } yield n

    val titleStr = s"${BLUE_B}\t" + titles.mkString("\t") + s"\t ${RESET}\n"
    val line = s"${BLUE_B}  " + ("|   " * size) + s"|  ${RESET}\n"
    val lineex = s"${BLUE_B}  " +
      (s"${BLUE_B}|${YELLOW_B}   ${RESET}") * (size.toFloat / 2).toInt +
      (s"${BLUE_B}|${RED_B}   ${RESET}") * ceil(size.toFloat / 2).toInt +
      s"${BLUE_B}|  ${RESET}\n"
    val border = s"${BLUE_B}  " + ("----" * size) + s"-  ${RESET}\n"
    val box = titleStr + line * size + lineex + border
    return box
  }
}
