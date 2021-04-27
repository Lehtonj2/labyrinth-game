package Game.Main


import scalafx.Includes._
import scalafx.scene.control.{Label, Menu, MenuBar, MenuItem, TextField}
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyEvent
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.application.JFXApp
import scalafx.beans.property.StringProperty
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, GridPane, VBox}
import scalafx.event.ActionEvent

import scala.collection.mutable.Buffer



object Main extends JFXApp {


    stage = new JFXApp.PrimaryStage {
        title.value = "Labyrinth-game"
        height = 500
        width = 500

    }
    val bottomBox = new VBox //VBox lays out children next to each other vertically.
    val label = new Label("Enter next labyrinth-size (Default = 10)")
    val textInput = new TextField
    bottomBox.children += label
    bottomBox.children += textInput

    //Accessing textinput text.
    val root = new GridPane

    root.add(bottomBox, 1, 1)
    val menuScene = new Scene(root)

    val game = new Game
    var floorRectangles = Buffer[Rectangle]()
    var wallRectangles = Buffer[Rectangle]()
    def makeFloorRectangles() {
    for (i <- game.floors) {
    val rectangle = new Rectangle {
    x = i.location._1 * 10
    y = i.location._2 * 10
    width = 10
    height = 10
    fill = Blue
    if (i.underLap) fill = Gray
}
    floorRectangles += rectangle

    }
    }
    def makeWallRectangles() {
    for (i <- game.walls) {
    val rectangle = new Rectangle {
    x = i.location._1 * 10
    y = i.location._2 * 10
    width = 10
    height = 10
    fill = Black
}
    wallRectangles += rectangle

    }
    }
    def makePlayerRectangle = {
      var playerRectangle = new Rectangle {
      x = game.player.x * 10
      y = game.player.y * 10
      width = 10
      height = 10
      fill = Red
      }
        playerRectangle
      }
    var playerRectangle = makePlayerRectangle
    val exitRectangle = new Rectangle {
    x = 0
    y = 0
    width = 10
    height = 10
    fill = Yellow
    }


    def newGameScene = {
    var gameScene = new Scene {

    def hidePlayer(hide: Boolean) {
        if (hide) {
            playerHidden = true
            content = floorRectangles ++ wallRectangles ++ Buffer(exitRectangle)
        } else {
            content = floorRectangles ++ wallRectangles ++ Buffer(exitRectangle) ++ Buffer(playerRectangle)
          playerHidden = false
        }
    }

    hidePlayer(false)
    var playerHidden = false

    var fromDirection = ""
    var onBridge = false
    def done() {
        if (game.player.x == 0 & game.player.y == 0) {
          stage.scene = menuScene
        }
    }




    onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
            case KeyCode.Up => {
                if (!game.walls.map(n => n.location).contains((game.player.x, game.player.y - 1)) & game.labyrinthLocations.locations.map(n => (n._1, n._2)).contains((game.player.x, game.player.y - 1))) {
                    if (!game.bridges.map(n => (n._1, n._2)).contains((game.player.x, game.player.y - 1)) & !onBridge) {
                        game.player.y -= 1
                        playerRectangle.y = playerRectangle.y() - 10
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y - 1, "N")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y - 1, "S"))) & !onBridge) {
                        game.player.y -= 1
                        playerRectangle.y = playerRectangle.y() - 10
                        fromDirection = "v"
                        onBridge = true
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y - 1, "W")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y - 1, "E"))) & !onBridge) {
                        game.player.y -= 1
                        playerRectangle.y = playerRectangle.y() - 10
                        fromDirection = "v"
                        onBridge = true
                        hidePlayer(true)
                    } else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x, game.player.y - 1))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3) == game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y - 1).map(n => n._3)) {
                            game.player.y -= 1
                            playerRectangle.y = playerRectangle.y() - 10
                            onBridge = true
                        }  else if ((fromDirection == "v")) {
                            game.player.y -= 1
                            playerRectangle.y = playerRectangle.y() - 10
                            fromDirection = "v"
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }
                    }else if ((fromDirection == "v")) {
                        game.player.y -= 1
                        playerRectangle.y = playerRectangle.y() - 10
                        onBridge = false
                        hidePlayer(false)
                    }


            }
              done()
            }


            case KeyCode.Down => {
                if (!game.walls.map(n => n.location).contains((game.player.x, game.player.y + 1)) & game.labyrinthLocations.locations.map(n => (n._1, n._2)).contains((game.player.x, game.player.y + 1))) {
                    if (!game.bridges.map(n => (n._1, n._2)).contains((game.player.x, game.player.y + 1)) & !onBridge) {
                        game.player.y += 1
                        playerRectangle.y = playerRectangle.y() + 10
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y + 1, "N")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y + 1, "S"))) & !onBridge) {
                        game.player.y += 1
                        playerRectangle.y = playerRectangle.y() + 10
                        fromDirection = "v"
                        onBridge = true
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y + 1, "W")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y + 1, "E"))) & !onBridge) {
                        game.player.y += 1
                        playerRectangle.y = playerRectangle.y() + 10
                        fromDirection = "v"
                        onBridge = true
                        hidePlayer(true)
                    } else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x, game.player.y + 1))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3) == game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y + 1).map(n => n._3)) {
                            game.player.y += 1
                            playerRectangle.y = playerRectangle.y() + 10
                            onBridge = true
                        } else if ((fromDirection == "v")) {
                            game.player.y += 1
                            playerRectangle.y = playerRectangle.y() + 10
                            fromDirection = "v"
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }
                    }else if ((fromDirection == "v")) {
                        game.player.y += 1
                        playerRectangle.y = playerRectangle.y() + 10
                        onBridge = false
                        hidePlayer(false)
                    }


            }
              done()
            }
            case KeyCode.Left => {
                if (!game.walls.map(n => n.location).contains((game.player.x - 1, game.player.y)) & game.labyrinthLocations.locations.map(n => (n._1, n._2)).contains((game.player.x - 1, game.player.y))) {
                    if (!game.bridges.map(n => (n._1, n._2)).contains((game.player.x - 1, game.player.y)) & !onBridge) {
                        game.player.x -= 1
                        playerRectangle.x = playerRectangle.x() - 10
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x - 1, game.player.y, "N")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x - 1, game.player.y, "S"))) & !onBridge) {
                        game.player.x -= 1
                        playerRectangle.x = playerRectangle.x() - 10
                        fromDirection = "h"
                        onBridge = true
                        hidePlayer(true)
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x - 1, game.player.y, "W")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x - 1, game.player.y, "E"))) & !onBridge) {
                        game.player.x -= 1
                        playerRectangle.x = playerRectangle.x() - 10
                        fromDirection = "h"
                        onBridge = true
                    }else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x - 1, game.player.y))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3) == game.bridges.filter(n => (n._1 == game.player.x - 1) & n._2 == game.player.y).map(n => n._3)) {
                            game.player.x -= 1
                            playerRectangle.x = playerRectangle.x() - 10
                            onBridge = true
                        }  else if ((fromDirection == "h")) {
                            game.player.x -= 1
                            playerRectangle.x = playerRectangle.x() - 10
                            fromDirection = "h"
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }

                    }else if (fromDirection == "h") {
                        game.player.x -= 1
                        playerRectangle.x = playerRectangle.x() - 10
                        onBridge = false
                        hidePlayer(false)
                    }
                }
              done()
            }
            case KeyCode.Right => {
                if (!game.walls.map(n => n.location).contains((game.player.x + 1, game.player.y)) & game.labyrinthLocations.locations.map(n => (n._1, n._2)).contains((game.player.x + 1, game.player.y))) {
                    if (!game.bridges.map(n => (n._1, n._2)).contains((game.player.x + 1, game.player.y)) & !onBridge) {
                        game.player.x += 1
                        playerRectangle.x = playerRectangle.x() + 10
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + 1, game.player.y, "N")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + 1, game.player.y, "S"))) & !onBridge) {
                        game.player.x += 1
                        playerRectangle.x = playerRectangle.x() + 10
                        fromDirection = "h"
                        onBridge = true
                        hidePlayer(true)
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + 1, game.player.y, "W")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + 1, game.player.y, "E"))) & !onBridge) {
                        game.player.x += 1
                        playerRectangle.x = playerRectangle.x() + 10
                        fromDirection = "h"
                        onBridge = true
                    }else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x + 1, game.player.y))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3) == game.bridges.filter(n => (n._1 == game.player.x + 1) & n._2 == game.player.y).map(n => n._3)) {
                            game.player.x += 1
                            playerRectangle.x = playerRectangle.x() + 10
                            onBridge = true
                        }  else if ((fromDirection == "h")) {
                            game.player.x += 1
                            playerRectangle.x = playerRectangle.x() + 10
                            fromDirection = "h"
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }

                    }else if (fromDirection == "h") {
                        game.player.x += 1
                        playerRectangle.x = playerRectangle.x() + 10
                        onBridge = false
                        hidePlayer(false)
                    }
                }
              done()
            }
            case _ =>
        }
        }
    }
      gameScene
    }

    val menuBar = new MenuBar
    val menu = new Menu("Menu")
    val startGame = new MenuItem("Start game")
    menu.items = List(startGame)
    menuBar.menus = List(menu)
    val rootPane = new BorderPane
    rootPane.top = menuBar
    //this.menuScene.root = rootPane
    root.add(rootPane, 0, 0)
    var newSize = 10
    def updateNewSize(number: StringProperty) {
      if (number.value.nonEmpty & number.value.forall(n => n.isDigit)) {
        this.newSize = number.value.toInt
      } else this.newSize = 10
    }
    startGame.onAction = (ae: ActionEvent) => {

      updateNewSize(textInput.text)
      this.game.newGrid(newSize, newSize)
      this.game.newLabyrinth()
      this.playerRectangle = this.makePlayerRectangle
      this.floorRectangles.clear()
      this.wallRectangles.clear()
      this.makeFloorRectangles()
      this.makeWallRectangles()
      stage.scene = newGameScene
    }
  stage.scene = menuScene

    }




