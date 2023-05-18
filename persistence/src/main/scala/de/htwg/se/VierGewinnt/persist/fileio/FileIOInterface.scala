package de.htwg.se.VierGewinnt.persist.fileio


/** Interface for FileIO, to save and load the state of a game. */
trait FileIOInterface {

  /** Load the game from a file and return the playground. */
  def load: String

  /** Save the game to a file.
    *
    * @param playground
    *   The playground to save.
    */
  def save(playground: String): Unit
}
