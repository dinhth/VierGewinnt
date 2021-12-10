package de.htwg.se.VierGewinnt.aview

import de.htwg.se.VierGewinnt.controller.Controller
import de.htwg.se.VierGewinnt.model.Chip
import de.htwg.se.VierGewinnt.model.Move
import de.htwg.se.VierGewinnt.util.Observer
import scalafx.application.JFXApp3
import scalafx.scene.control.Label
import scalafx.scene.effect.BlendMode.Blue
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.GridPane.getRowSpan
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.shape.Rectangle
import scalafx.scene.Scene
import scalafx.Includes.*

case class GUI(controller: Controller) extends JFXApp3 with Observer:
  controller.add(this)
  var chips = emptyChips()
  var chipGrid = emptyGrid()

  def chipChanged() = println("bla")

  override def update: Unit =
    chips.zipWithIndex.foreach((subList, i) => {
      for ((element, j) <- subList.zipWithIndex)
        controller.getChipColor(j, i) match {
          case Chip.EMPTY =>
            element.fill = Color.Gray
          case Chip.RED =>
            element.fill = Color.Red
          case Chip.YELLOW =>
            element.fill = Color.Yellow
        }
    })
  // chips.foreach(sublist => sublist.foreach(element => element.fill = controller.getChipColor()))

  def run = println("GUI starting to run...")

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title.value = "VierGewinnt"
      scene = new Scene:
        val rect = Rectangle(0, 0, 800, 800)
        rect.fill = Color.DarkBlue
        controller.setupGame(0, 7)

        content = List(rect, chipGrid)

  def emptyChips(): Array[Array[Circle]] = Array.fill(controller.gridSize, controller.gridSize)(Circle(50))

  def emptyGrid(): GridPane =
    new GridPane:
      for ((subList, i) <- chips.zipWithIndex) {
        for ((element, j) <- subList.zipWithIndex) {
          element.onMouseClicked = (event: MouseEvent) => controller.doAndPublish(controller.insChip, Move(i))
          add(element, i, j)
        }
      }
