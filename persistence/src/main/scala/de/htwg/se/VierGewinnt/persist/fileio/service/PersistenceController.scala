package fileIOComponent.service
import com.google.inject.{Guice, Inject}
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.VierGewinnt.model.playerComponent.playerBaseImpl.Player


import java.io._
import play.api.libs.json.{JsValue, Json}
import scala.io.Source

object PersistenceController {

  def load(): String = {
    val file = scala.io.Source.fromFile("game.json")
    try file.mkString finally file.close()
  }

  def save(gameAsJson: String): Unit = {
    val pw = new PrintWriter(new File("." + File.separator + "game.json"))
    pw.write(gameAsJson)
    pw.close
  }

}
