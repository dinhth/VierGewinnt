package de.htwg.se.VierGewinnt

import scala.io.StdIn.readLine

object VierGewinnt {
  def main(args: Array[String]): Unit = {
    var input:String=""
    input = readLine()
    println("1.Spieler: " + input)
    input = readLine()
    println("2.Spieler: " + input)
    println("Vier Gewinnt")

    val matrix = Vector(
      Vector("a1", "a2", "a3", "a4", "a5", "a6" ),
      Vector("b1", "b2", "b3", "b4", "b5", "b6"),
      Vector("c1", "c2", "c3", "c4", "c5", "c6"),
      Vector("d1", "d2", "d3", "d4", "d5", "d6"),
      Vector("e1", "e2", "e3", "e4", "e5", "e6"),
      Vector("f1", "f2", "f3", "f4", "f5", "f6"),
      Vector("g1", "g2", "g3", "g4", "g5", "g6"))

    matrix.foreach(println)
  }

}
