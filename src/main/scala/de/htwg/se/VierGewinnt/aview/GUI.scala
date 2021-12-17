package de.htwg.se.VierGewinnt.aview

import de.htwg.se.VierGewinnt.controller.{Controller, Move}
import de.htwg.se.VierGewinnt.model.gridComponent.gridBaseImpl.Chip
import de.htwg.se.VierGewinnt.util.Observer
import scalafx.application.JFXApp3
import scalafx.application.Platform
import scalafx.application.Platform.*
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuBar
import scalafx.scene.control.MenuItem
import scalafx.scene.effect.BlendMode.Blue
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.shape.Rectangle
import scalafx.scene.Scene
import scalafx.Includes.*

case class GUI(controller: Controller) extends JFXApp3 with Observer:
  controller.add(this)
  var chips = emptyChips()
  var chipGrid = emptyGrid()

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

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title.value = "VierGewinnt"
      scene = new Scene:
        fill = Color.DarkBlue
        controller.setupGame(0, 7)
        val menu = new MenuBar {
          menus = List(
            new Menu("File") {
              items = List(
                new MenuItem("New...") {
                  onAction = (event: ActionEvent) => controller.setupGame(0, 0)
                },
                new MenuItem("Save"),
                new MenuItem("Load")
              )
            },
            new Menu("Control") {
              items = List(
                new MenuItem("Undo") {
                  onAction = (event: ActionEvent) => controller.doAndPublish(controller.undo)
                },
                new MenuItem("Redo") {
                  onAction = (event: ActionEvent) => controller.doAndPublish(controller.redo)
                }
              )
            },
            new Menu("Help") {
              items = List(
                new MenuItem("About")
              )
            }
          )
        }

        var vBox = new VBox():
          children = List(menu, chipGrid)

        content = new VBox() {
          children = List(menu, chipGrid)
        }

  def emptyChips(): Array[Array[Circle]] = Array.fill(controller.gridSize, controller.gridSize)(Circle(50))

  def emptyGrid(): GridPane =
    new GridPane:
      for ((subList, i) <- chips.zipWithIndex) {
        for ((element, j) <- subList.zipWithIndex) {
          element.onMouseClicked = (event: MouseEvent) => controller.doAndPublish(controller.insChip, Move(i))
          add(element, i, j)
        }
      }

  override def stopApp(): Unit =
    super.stopApp()
    System.exit(0)
