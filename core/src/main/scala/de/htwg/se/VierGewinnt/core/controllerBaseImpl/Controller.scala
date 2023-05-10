/** Controller base implementation for VierGewinnt.
 *
 * @author
 * Victor Gänshirt & Orkan Yücetag
 */

package de.htwg.se.VierGewinnt.core.controllerBaseImpl

import com.google.inject.name.{Named, Names}
import com.google.inject.{Guice, Inject, Key}
import de.htwg.se.VierGewinnt.core.{ControllerInterface, CoreModule, Util}
import de.htwg.se.VierGewinnt.core.service.CoreRestController
import de.htwg.se.VierGewinnt.model.gridComponent.GridInterface
import de.htwg.se.VierGewinnt.model.playerComponent.{PlayerInterface, playerBaseImpl}
import de.htwg.se.VierGewinnt.model.playgroundComponent.playgroundBaseImpl.{PlaygroundPvE, PlaygroundPvP}
import de.htwg.se.VierGewinnt.model.playgroundComponent.{PlaygroundInterface, playgroundBaseImpl}
import de.htwg.se.VierGewinnt.util.{Command, Move, Observable, UndoManager}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.Http
import de.htwg.se.VierGewinnt.core.service.CoreRestService.getClass
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.Failure
import scala.util.Success

/** Controller base implementation.
 *
 * @param playground
 * Sets the default playground with inject.
 * @param gameType
 * Sets the default gametype with inject.
 */
class Controller @Inject()(@Named("DefaultPlayground") var playground: PlaygroundInterface, @Named("DefaultGameType") var gameType: Int)
  extends Observable
    with ControllerInterface :


  val fileIOServer = "http://localhost:8081/fileio"
  private val logger = LoggerFactory.getLogger(getClass)

  val restController: CoreRestController = new CoreRestController

  /** Returns the size of the grid withing playground. */
  override def gridSize: Int = playground.size

  /** Loads the previously saved playground from a file. */
  override def load: Unit =
    val responseFuture = restController.sendGetRequest(fileIOServer + "/load")

    restController.handleResponse(responseFuture, json => {
      playground = Util.jsonToPlayground(json)
      notifyObservers
    })


  /** Saves the current playground to a file. */
  override def save: Unit =
    val responseFuture = restController.sendPostRequest(fileIOServer + "/save", Util.toJsonString(playground))

    restController.handleResponse(responseFuture, json => {
      logger.info(json)
      notifyObservers
    })

  /** Sets up a new game and switches the GameState to PlayState().
   *
   * @param gameType
   * Choose the gametype (0 -> PVP, 1 -> PVP).
   * @param size
   * Choose the size of the playground.
   */
  override def setupGame(gameType: Int, size: Int): Unit =
    gameType match
      case 0 =>
        playground = new PlaygroundPvP(size)
      case 1 =>
        playground = new PlaygroundPvE(size)
    gamestate.changeState(PlayState())
    winnerChips = None
    notifyObservers

  /** Variable for an instance of the UndoManager. */
  val undoManager = new UndoManager[PlaygroundInterface]

  /** Do a given move with a given function and save it into the UndoManager.
   *
   * @param doThis
   * A function to do and save into the UndoManager.
   * @param move
   * Move with input.
   */
  override def doAndPublish(doThis: Move => PlaygroundInterface, move: Move): Unit =
    gameNotDone match
      case true =>
        playground = doThis(move)
        notifyObservers
      case _ =>

  /** Do a given function and save it into the UndoManager.
   *
   * @param doThis
   * A function to do and save into the UndoManager.
   */
  override def doAndPublish(doThis: => PlaygroundInterface): Unit =
    gameNotDone match
      case true =>
        playground = doThis
        notifyObservers
      case _ =>

  /** Reset the GameState to PrepareState() to restart the game. */
  override def restartGame: Unit =
    gamestate.changeState(PrepareState())
    notifyObservers

  /** Checks if the game is still in preparing phase. */
  override def isPreparing: Boolean =
    gamestate.state.equals(PrepareState())

  /** Checks if the game is not over yet. */
  override def gameNotDone: Boolean =
    !(gamestate.state.equals(WinState()) || gamestate.state.equals(TieState()))

  /** Undo the last move. */
  override def undo: PlaygroundInterface = undoManager.undoStep(playground)

  /** Redo the last undone move. */
  override def redo: PlaygroundInterface = undoManager.redoStep(playground)

  /** Insert a chip into the playground.
   *
   * @param move
   * On which column the chip should be placed on the playground.
   * @return
   * Return a new playground after the chip was inserted.
   */
  override def insChip(move: Move): PlaygroundInterface = {
    val temp = undoManager.doStep(playground, InsertChipCommand(move))
    checkWinner(temp)
    checkFull(temp)
    temp
  }

  /** Check if the playground is full with chips. If true and game not done, change the GameState to TieState(). If false and game not done,
   * change the GameState to PlayState(). When the game is done, do not change the State.
   */
  override def checkFull(pg: PlaygroundInterface): Unit =
    pg.grid.checkFull() match
      case true =>
        if (gameNotDone)
          gamestate.changeState(TieState())
      case false =>
        if (gameNotDone)
          gamestate.changeState(PlayState())

  /** Check if the playground has a winner. If there is a winner, change the GameState to WinState, else do nothing.
   */
  override def checkWinner(pg: PlaygroundInterface): Unit =
    pg.grid.checkWin() match
      case None =>
      case Some(winningchips) => // 1 == Red, 2 == Yellow
        winnerChips = Some(winningchips)
        gamestate.changeState(WinState())

  /** Gets the color of a chip on a certain coordinate.
   *
   * @param row
   * The row (y-coordinate) of the playground.
   * @param col
   * The column (x-coordinate) of the playground.
   * @return
   * Returns the color chip in that position in ANSII.
   */
  override def getChipColor(row: Int, col: Int): String =
    playground.grid.getCell(row, col).chip.getColorCode

  /** Gets the string of the current state.
   *
   * @return
   * Returns the string of the current state.
   */
  override def printState: String = gamestate.displayState()

  /** Gets the status string of the playground.
   *
   * @return
   * Returns the status string of the playground.
   */
  override def playgroundState: String = playground.getStatus()

  /** Prints the playground to a string. */
  override def toString = playground.toString

