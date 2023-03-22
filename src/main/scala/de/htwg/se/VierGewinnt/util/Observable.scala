/** Observable class for VierGewinnt.
 *
 * @author Thu Ha Dinh & Orkan Yücetag */
package de.htwg.se.VierGewinnt.util

trait Observable {
  val subscribers: scala.collection.mutable.ListBuffer[Observer] = scala.collection.mutable.ListBuffer.empty[Observer]
  def add(s: Observer) = subscribers :+ s

  def remove(s: Observer):Unit = subscribers.filterNot(_ == s)

  def notifyObservers() = subscribers.foreach(_.update)
}
