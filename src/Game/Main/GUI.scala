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
import scalafx.scene.layout.{BorderPane, GridPane, Pane, VBox}
import scalafx.event.ActionEvent
import scalafx.scene.text.FontWeight
import scala.collection.mutable.Buffer



object Main extends JFXApp {


    stage = new JFXApp.PrimaryStage {
        title.value = "Labyrinth-game"
        height = 500
        width = 500

    }
    val bottomBox = new VBox
    val label = new Label("Enter next labyrinth-size (Default = 10, Max = 50)")
    val textInput = new TextField
    bottomBox.children += label
    bottomBox.children += textInput


    val root = new GridPane {

    }

    root.add(bottomBox, 1, 1)

    val menuScene = new Scene(root)

    val menuBar = new MenuBar
    val menu = new Menu("Game")
    val startGame = new MenuItem("Start a new game")
    val continue = new MenuItem("Continue current game")
    val quit = new MenuItem("Quit")
    val fileMenu = new Menu("Save/Load")
    val saveGame = new MenuItem("Save current game")
    val loadGame = new MenuItem("Load saved game")
    menu.items = List(startGame, continue, quit)
    fileMenu.items = List(saveGame, loadGame)
    menuBar.menus = List(menu, fileMenu)
    val rootPane = new BorderPane
    rootPane.top = menuBar
    root.add(rootPane, 0, 0)


    var newSize = 15
    def scale(size: Int):Double = {
      if (size <= 20) 10 else 4
    }
    val game = new Game
    var floorRectangles = Buffer[Rectangle]()
    var wallRectangles = Buffer[Rectangle]()
    var solveRectangles = Buffer[Rectangle]()
    def makeFloorRectangles() {
    for (i <- game.floors) {
    val rectangle = new Rectangle {
    x = i.location._1 * scale(newSize)
    y = i.location._2 * scale(newSize)
    width = scale(newSize)
    height = scale(newSize)
    fill = Blue
    if (i.overLap) fill = Gray
    }
    floorRectangles += rectangle

    }
    }
    def makeWallRectangles() {
    for (i <- game.walls) {
    val rectangle = new Rectangle {
    x = i.location._1 * scale(newSize)
    y = i.location._2 * scale(newSize)
    width = scale(newSize)
    height = scale(newSize)
    fill = Black
}
    wallRectangles += rectangle

    }
    }
    def makePlayerRectangle = {
      var playerRectangle = new Rectangle {
      x = game.player.x * scale(newSize)
      y = game.player.y * scale(newSize)
      width = scale(newSize)
      height = scale(newSize)
      fill = Red
      }
        playerRectangle
      }
    var playerRectangle = makePlayerRectangle
    def makeExitRectangle = {
    var exitRectangle = new Rectangle {
    x = 0
    y = 0
    width = scale(newSize)
    height = scale(newSize)
    fill = Yellow
    }
      exitRectangle
    }
    var exitRectangle = makeExitRectangle

    def makeSolveRectangles() = {
    val solveFloors = game.solveLabyrinth(game.player.x, game.player.y)
    for (i <- solveFloors) {
    var solveRectangle = new Rectangle {
    x = i.location._1 * scale(newSize)
    y = i.location._2 * scale(newSize)
    width = scale(newSize)
    height = scale(newSize)
    fill = Yellow
    }
      solveRectangles += solveRectangle
    }
    }

    def newGameScene = {
    var gameScene = new Scene {
    var notYetSolved = true
    var drawSolver = false
    def hidePlayer(hide: Boolean) {
        if (drawSolver) {
          if (hide) {
            playerHidden = true
            content = floorRectangles ++ wallRectangles ++ Buffer(exitRectangle) ++ solveRectangles
        } else {
            content = floorRectangles ++ wallRectangles ++ Buffer(exitRectangle) ++ solveRectangles ++ Buffer(playerRectangle)
          playerHidden = false
        }

        }else{
        if (hide) {
            playerHidden = true
            content = floorRectangles ++ wallRectangles ++ Buffer(exitRectangle)
        } else {
            content = floorRectangles ++ wallRectangles ++ Buffer(exitRectangle) ++ Buffer(playerRectangle)
          playerHidden = false
        }
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
                        playerRectangle.y = playerRectangle.y() - scale(newSize)
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y - 1, "N")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y - 1, "S"))) & !onBridge) {
                        game.player.y -= 1
                        playerRectangle.y = playerRectangle.y() - scale(newSize)
                        fromDirection = "v"
                        onBridge = true
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y - 1, "W")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y - 1, "E"))) & !onBridge) {
                        game.player.y -= 1
                        playerRectangle.y = playerRectangle.y() - scale(newSize)
                        fromDirection = "v"
                        onBridge = true
                        hidePlayer(true)
                    } else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x, game.player.y - 1))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3) == game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y - 1).map(n => n._3)) {
                            game.player.y -= 1
                            playerRectangle.y = playerRectangle.y() - scale(newSize)
                            onBridge = true
                        }  else if ((fromDirection == "v")) {
                            game.player.y -= 1
                            playerRectangle.y = playerRectangle.y() - scale(newSize)
                            fromDirection = "v"
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }
                    }else if ((fromDirection == "v")) {
                        game.player.y -= 1
                        playerRectangle.y = playerRectangle.y() - scale(newSize)
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
                        playerRectangle.y = playerRectangle.y() + scale(newSize)
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y + 1, "N")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y + 1, "S"))) & !onBridge) {
                        game.player.y += 1
                        playerRectangle.y = playerRectangle.y() + scale(newSize)
                        fromDirection = "v"
                        onBridge = true
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y + 1, "W")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x, game.player.y + 1, "E"))) & !onBridge) {
                        game.player.y += 1
                        playerRectangle.y = playerRectangle.y() + scale(newSize)
                        fromDirection = "v"
                        onBridge = true
                        hidePlayer(true)
                    } else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x, game.player.y + 1))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3) == game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y + 1).map(n => n._3)) {
                            game.player.y += 1
                            playerRectangle.y = playerRectangle.y() + scale(newSize)
                            onBridge = true
                        } else if ((fromDirection == "v")) {
                            game.player.y += 1
                            playerRectangle.y = playerRectangle.y() + scale(newSize)
                            fromDirection = "v"
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }
                    }else if ((fromDirection == "v")) {
                        game.player.y += 1
                        playerRectangle.y = playerRectangle.y() + scale(newSize)
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
                        playerRectangle.x = playerRectangle.x() - scale(newSize)
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x - 1, game.player.y, "N")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x - 1, game.player.y, "S"))) & !onBridge) {
                        game.player.x -= 1
                        playerRectangle.x = playerRectangle.x() - scale(newSize)
                        fromDirection = "h"
                        onBridge = true
                        hidePlayer(true)
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x - 1, game.player.y, "W")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x - 1, game.player.y, "E"))) & !onBridge) {
                        game.player.x -= 1
                        playerRectangle.x = playerRectangle.x() - scale(newSize)
                        fromDirection = "h"
                        onBridge = true
                    }else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x - 1, game.player.y))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3) == game.bridges.filter(n => (n._1 == game.player.x - 1) & n._2 == game.player.y).map(n => n._3)) {
                            game.player.x -= 1
                            playerRectangle.x = playerRectangle.x() - scale(newSize)
                            onBridge = true
                        }  else if ((fromDirection == "h")) {
                            game.player.x -= 1
                            playerRectangle.x = playerRectangle.x() - scale(newSize)
                            fromDirection = "h"
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }

                    }else if (fromDirection == "h") {
                        game.player.x -= 1
                        playerRectangle.x = playerRectangle.x() - scale(newSize)
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
                        playerRectangle.x = playerRectangle.x() + scale(newSize)
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + 1, game.player.y, "N")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + 1, game.player.y, "S"))) & !onBridge) {
                        game.player.x += 1
                        playerRectangle.x = playerRectangle.x() + scale(newSize)
                        fromDirection = "h"
                        onBridge = true
                        hidePlayer(true)
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + 1, game.player.y, "W")) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + 1, game.player.y, "E"))) & !onBridge) {
                        game.player.x += 1
                        playerRectangle.x = playerRectangle.x() + scale(newSize)
                        fromDirection = "h"
                        onBridge = true
                    }else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x + 1, game.player.y))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3) == game.bridges.filter(n => (n._1 == game.player.x + 1) & n._2 == game.player.y).map(n => n._3)) {
                            game.player.x += 1
                            playerRectangle.x = playerRectangle.x() + scale(newSize)
                            onBridge = true
                        }  else if ((fromDirection == "h")) {
                            game.player.x += 1
                            playerRectangle.x = playerRectangle.x() + scale(newSize)
                            fromDirection = "h"
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }

                    }else if (fromDirection == "h") {
                        game.player.x += 1
                        playerRectangle.x = playerRectangle.x() + scale(newSize)
                        onBridge = false
                        hidePlayer(false)
                    }
                }
              done()
            }
            case KeyCode.Space => {
                  if (notYetSolved & !game.bridges.map(n =>(n._1, n._2)).contains((game.player.x, game.player.y))) {
                  makeSolveRectangles()
                  drawSolver = true
                  if (playerHidden) {
            content = floorRectangles ++ wallRectangles ++ Buffer(exitRectangle) ++ solveRectangles
        } else {
            content = floorRectangles ++ wallRectangles ++ Buffer(exitRectangle) ++ solveRectangles ++ Buffer(playerRectangle)
        }
                  if (game.labyrinthSolved) notYetSolved = false

                  }
            }
            case KeyCode.Escape => {
                  stage.scene = menuScene
            }
            case _ =>
        }
        }
    }
      gameScene
    }

    /*val menuBar = new MenuBar
    val menu = new Menu("Menu")
    val startGame = new MenuItem("Start game")
    menu.items = List(startGame)
    menuBar.menus = List(menu)
    val rootPane = new BorderPane
    rootPane.top = menuBar
    //this.menuScene.root = rootPane
    root.add(rootPane, 0, 0)*/
    def updateNewSize(number: StringProperty) {
      if (number.value.nonEmpty & number.value.forall(n => n.isDigit)) {
        if (number.value.toInt <= 50 & number.value.toInt > 0) this.newSize = number.value.toInt else this.newSize = 50
      } else this.newSize = 10
    }
    var gameStarted = false
    startGame.onAction = (ae: ActionEvent) => {
      gameStarted = true
      updateNewSize(textInput.text)
      this.game.newGrid(newSize, newSize)
      this.game.newLabyrinth()
      this.playerRectangle = this.makePlayerRectangle
      this.exitRectangle = this.makeExitRectangle
      this.solveRectangles.clear()
      this.floorRectangles.clear()
      this.wallRectangles.clear()
      this.makeFloorRectangles()
      this.makeWallRectangles()
      stage.scene = newGameScene
    }
    continue.onAction = (ae: ActionEvent) => {
      if (gameStarted) stage.scene = newGameScene
    }
    saveGame.onAction = (ae: ActionEvent) => {
      if (gameStarted) this.game.fileManager.saveLabyrinth(this.game)
    }
    quit.onAction = (ae: ActionEvent) => {
      sys.exit()
    }


  stage.scene = menuScene

    }




