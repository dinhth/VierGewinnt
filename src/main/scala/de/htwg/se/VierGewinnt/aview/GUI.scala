package de.htwg.se.VierGewinnt.aview


import de.htwg.se.VierGewinnt.controller.controllerComponent.ControllerInterface
import de.htwg.se.VierGewinnt.util.{Move, Observer}
import javafx.animation
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
import scalafx.animation.*
import scalafx.Includes.*
import scalafx.scene.shape.Circle.sfxCircle2jfx

import scala.io.AnsiColor.{BLUE_B, RED_B, YELLOW_B}
import java.util.Optional
import scala.language.postfixOps

case class GUI(controller: ControllerInterface) extends JFXApp3 with Observer :
  controller.add(this)
  var chips = emptyChips()
  var chipGrid = emptyGrid()
  var playgroundstatus = new Menu(controller.playgroundState)
  var statestatus = new Menu(controller.printState)

  override def update: Unit =
    checkChipSize()
    chips.zipWithIndex.foreach((subList, i) => {
      for ((element, j) <- subList.zipWithIndex)
        controller.getChipColor(j, i) match {
          case BLUE_B => //Empty
            element.fill = Color.Gray
          case RED_B => //Red
            if element.getFill() != Color.sfxColor2jfx(Color.Red) then
              animateDrop(element, Color.Red)
          case YELLOW_B => //Yellow
            if element.getFill() != Color.sfxColor2jfx(Color.Yellow) then
              animateDrop(element, Color.Yellow)
        }
    })


  def animateDrop(element: Circle, color: Color): Unit =
    Timeline(
      Seq(
        at(0.0 s) {
          element.translateY -> -500.0
        }, at(0.0 s) {
          element.fill -> Color.Gray
        },
        at(0.3 s) {
          element.translateY -> 0.0
        },
        at(0.3 s) {
          element.fill -> color
        }
      )
    ).play()

  override def start(): Unit =

    stage = new JFXApp3.PrimaryStage :
      title.value = "VierGewinnt"
      scene = new Scene :
        fill = Color.DarkBlue
        val menu = new MenuBar {
          menus = List(
            new Menu("File") {
              items = List(
                new MenuItem("New PvP") {
                  onAction = (event: ActionEvent) => {
                    val dialog = new TextInputDialog("7") {
                      initOwner(stage)
                      title = "Set a size"
                      this.setContentText("Enter a number min. 4:")
                    }
                    var result = dialog.showAndWait()
                    while (result.get.toInt < 4)
                      result = dialog.showAndWait()

                    controller.setupGame(0, result.get.toInt)
                    chipGrid = emptyGrid() //Update Grid to new Size
                    start()
                  }
                }, new MenuItem("New PvE") {
                  onAction = (event: ActionEvent) => {
                    val dialog = new TextInputDialog("7") {
                      initOwner(stage)
                      title = "Set a size"
                      this.setContentText("Enter a number min. 4:")
                    }
                    var result = dialog.showAndWait()
                    while (result.get.toInt < 4)
                      result = dialog.showAndWait()

                    controller.setupGame(1, result.get.toInt)
                    chipGrid = emptyGrid() //Update Grid to new Size
                    start()
                  }
                },
                new MenuItem("Save") {
                  onAction = (event: ActionEvent) => controller.save
                },
                new MenuItem("Load") {
                  onAction = (event: ActionEvent) =>
                    controller.load
                    start()
                },
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
    playgroundstatus.setText(controller.playgroundState)
    statestatus.setText(controller.printState)


  def emptyChips(): Vector[Vector[Circle]] = Vector.fill(controller.gridSize, controller.gridSize)(Circle(50, fill = Color.Gray))

  //checkChipSize checks if the chips size is the same as the controller size, if not, update the chips
  def checkChipSize(): Unit =
    if (!chips.length.equals(controller.gridSize)) {
      chips = emptyChips()
      chipGrid = emptyGrid()
    }


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
