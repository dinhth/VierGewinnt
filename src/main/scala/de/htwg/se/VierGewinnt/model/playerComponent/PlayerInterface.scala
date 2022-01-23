/** Player Interface for VierGewinnt.
 *
 * @author Thu Ha Dinh & Orkan Yücetag */
package de.htwg.se.VierGewinnt.model.playerComponent

import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.{BotPlayer, HumanPlayer, Player}

/** Interface for a Player */
trait PlayerInterface:
  
  /** Get the user-specific chip. */
  def getChip():Chip
  
  /** Get the name of the player. */
  def getName():String