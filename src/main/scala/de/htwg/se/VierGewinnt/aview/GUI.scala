package de.htwg.se.VierGewinnt.aview


import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface
import de.htwg.se.VierGewinnt.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.VierGewinnt.util.{Move, Observer}
import scalafx.application.JFXApp3
import scalafx.application.Platform
import scalafx.application.Platform.*
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, TextInputDialog}
import scalafx.scene.effect.BlendMode.Blue
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.shape.Rectangle
import scalafx.scene.Scene
import scalafx.Includes.*

import scala.io.AnsiColor.{BLUE_B, RED_B, YELLOW_B}
import java.util.Optional

case class GUI(controller: ControllerInterface) extends JFXApp3 with Observer :
  controller.add(this)
  var chips = emptyChips()
  var chipGrid = emptyGrid()
  var playgroundstatus = new Menu(controller.playgroundState)
  var statestatus = new Menu(controller.printState)

  override def update: Unit =
    chips.zipWithIndex.foreach((subList, i) => {
      for ((element, j) <- subList.zipWithIndex)
        controller.getChipColor(j, i) match {
          case BLUE_B => //Empty
            element.fill = Color.Gray
          case RED_B => //Red
            element.fill = Color.Red
          case YELLOW_B => //Yellow
            element.fill = Color.Yellow
        }
    })

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage :
      title.value = "VierGewinnt"
      scene = new Scene :
        fill = Color.DarkBlue
        //controller.setupGame(0, 7)
        val menu = new MenuBar {
          menus = List(
            new Menu("File") {
              items = List(
                new MenuItem("New PVP") {
                  onAction = (event: ActionEvent) => {
                    val result = new TextInputDialog("7") {
                      initOwner(stage)
                      title = "Set a size"
                      this.setContentText("Enter a number 4+")
                    }.showAndWait()

                    controller.setupGame(0, result.get.toInt)
                    start()
                  }
                }, new MenuItem("New PVE") {
                  onAction = (event: ActionEvent) => {
                    val result = new TextInputDialog("7") {
                      initOwner(stage)
                      title = "Set a size"
                      this.setContentText("Enter a number 4+")
                    }.showAndWait()

                    controller.setupGame(1, result.get.toInt)
                    start()
                  }
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
            },
            playgroundstatus,
            statestatus
          )
        }

        var vBox = new VBox() :
          children = List(menu, chipGrid)

        content = new VBox() {
          children = List(menu, chipGrid)
        }


  def emptyChips(): Vector[Vector[Circle]] = Vector.fill(controller.gridSize, controller.gridSize)(Circle(50, fill = Color.Gray))

  def emptyGrid(): GridPane =
    new GridPane :
      for ((subList, i) <- chips.zipWithIndex) {
        for ((element, j) <- subList.zipWithIndex) {
          element.onMouseClicked = (event: MouseEvent) =>
            controller.doAndPublish(controller.insChip, Move(i));
            playgroundstatus.text = controller.playgroundState;
            statestatus.text = controller.printState
          add(element, i, j)
        }
      }

  override def stopApp(): Unit =
    super.stopApp()
    System.exit(0)
