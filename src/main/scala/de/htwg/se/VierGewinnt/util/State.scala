/** State Interface for VierGewinnt.
 *
 * @author Thu Ha Dinh & Orkan Yücetag */
package de.htwg.se.VierGewinnt.util

trait State[T] {
  def displayState: String
}
