package Game.Main


import scalafx.Includes._
import scalafx.scene.control.{Label, Menu, MenuBar, MenuItem, TextField}
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyEvent
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.application.JFXApp
import scalafx.beans.property.{DoubleProperty, StringProperty}
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
    stage.scene = menuScene


    def scale(size: Int):Double = {
      if (size <= 20) 10 else 4
    }


    val game = new Game
    def newSize = {
      this.game.size
    }

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

      def movePlayer(x: Int, y: Int, z: Int, playerInt: Int, recDouble: DoubleProperty, dir1: String, dir2: String, dir3: String, dir4: String, bdir1: String, locationx: Boolean) {
        var player = playerInt
        var rectangle = recDouble.value
        if (!game.walls.map(n => n.location).contains((game.player.x + x, game.player.y + y)) & game.labyrinthLocations.locations.map(n => (n._1, n._2)).contains((game.player.x + x, game.player.y + y))) {
                    if (!game.bridges.map(n => (n._1, n._2)).contains((game.player.x + x, game.player.y + y)) & !onBridge) {
                        player += z
                        rectangle = rectangle + z * scale(newSize)
                    } else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + x, game.player.y + y, dir1)) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + x, game.player.y + y, dir2))) & !onBridge) {
                        player += z
                        rectangle = rectangle + z * scale(newSize)
                        fromDirection = bdir1
                        onBridge = true
                    }else if ((game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + x, game.player.y + y, dir3)) | game.bridges.map(n => (n._1, n._2, n._3)).contains((game.player.x + x, game.player.y + y, dir4))) & !onBridge) {
                        player += z
                        rectangle = rectangle + z * scale(newSize)
                        fromDirection = bdir1
                        onBridge = true
                        hidePlayer(true)
                        println("noooo")
                    } else if (game.bridges.map(n => (n._1, n._2)).contains((game.player.x + x, game.player.y + y))) {
                        if (game.bridges.filter(n => (n._1 == game.player.x) & n._2 == game.player.y).map(n => n._3).head == game.bridges.filter(n => (n._1 == game.player.x + x) & n._2 == game.player.y + y).map(n => n._3).head) {
                            player += z
                            rectangle = rectangle + z * scale(newSize)
                            onBridge = true
                        }  else if ((fromDirection == bdir1)) {
                            player += z
                            rectangle = rectangle + z * scale(newSize)
                            fromDirection = bdir1
                            onBridge = true
                            hidePlayer(!playerHidden)
                        }
                    }else if ((fromDirection == bdir1)) {
                        player += z
                        rectangle = rectangle + z * scale(newSize)
                        onBridge = false
                        hidePlayer(false)
                    }
                    if (locationx) {
                      game.player.x = player
                      playerRectangle.x = rectangle
                    }else{
                      game.player.y = player
                      playerRectangle.y = rectangle



            }
              done()
            }
      }




    onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
            case KeyCode.Up => {

              movePlayer(0, -1, -1, game.player.y, playerRectangle.y, "N", "S", "W", "E", "v", false)
            }


            case KeyCode.Down => {

              movePlayer(0, 1, 1, game.player.y, playerRectangle.y, "N", "S", "W", "E", "v", false)
            }
            case KeyCode.Left => {

              movePlayer(-1, 0, -1,  game.player.x, playerRectangle.x, "W", "E", "N", "S", "h", true)
            }
            case KeyCode.Right => {

              movePlayer(1, 0, 1, game.player.x, playerRectangle.x, "W", "E", "N", "S", "h", true)
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


    def updateNewSize(number: StringProperty) {
      if (number.value.nonEmpty & number.value.forall(n => n.isDigit)) {
        if (number.value.toInt <= 50 & number.value.toInt > 0) this.game.size = number.value.toInt else this.game.size = 50
      } else this.game.size = 10
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
    loadGame.onAction = (ae: ActionEvent) => {

      val loadedLabyrinth = this.game.fileManager.loadLabyrinth(this.game)
      this.game.size = loadedLabyrinth._3
      this.game.newGrid(newSize, newSize)
      this.game.floors = loadedLabyrinth._1
      this.game.bridges = loadedLabyrinth._2
      println(this.game.bridges)
      this.game.labyrinthLocations = new Grid((this.game.grid.width * 3 - 1), (this.game.grid.height * 3 - 1))
      this.game.walls = new Grid((this.game.grid.width * 3 - 1), (this.game.grid.height * 3 - 1)).locations.filter(n => !this.game.floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
      this.game.player = new Character(this.game.grid.width + (this.game.grid.width / 2), this.game.grid.height + (this.game.grid.height / 2))
      this.playerRectangle = this.makePlayerRectangle
      this.exitRectangle = this.makeExitRectangle
      this.solveRectangles.clear()
      this.floorRectangles.clear()
      this.wallRectangles.clear()
      this.makeFloorRectangles()
      this.makeWallRectangles()
      gameStarted = true
      stage.scene = newGameScene

    }
    quit.onAction = (ae: ActionEvent) => {
      sys.exit()
    }

    }




