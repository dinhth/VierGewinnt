/** FileIO JSON Implementation for VierGewinnt.
 *
 * @author Victor Gänshirt & Orkan Yücetag */
package de.htwg.se.VierGewinnt.persist.fileio.fileIoJsonImpl

import de.htwg.se.VierGewinnt.persist.fileio.FileIOInterface
import play.api.libs.json.{JsNumber, JsString, JsValue, Json}

import java.io.PrintWriter
import scala.io.Source
import scala.util.{Failure, Success}

/** FileIO JSON Implementation, to save and load the state of a game with a JSON file. */
class FileIO extends FileIOInterface {

  /** Load the game from a "playground.json" file and return the playground. */
  override def load: String =
    val file = scala.io.Source.fromFile("game.json")
    try file.mkString finally file.close()


  /** Save the game to a "playground.json" file.
    *
    * @param playground
    *   The playground to save.
    */
  override def save(playground: String): Unit =
    import java.io._
    val pw = new PrintWriter(new File("game.json"))
    pw.write(playground)
    pw.close()
}
