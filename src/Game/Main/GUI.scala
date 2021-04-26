package Game.Main


import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.Pane

import scala.collection.mutable.Buffer



object Main extends JFXApp {

    stage = new JFXApp.PrimaryStage {
        title.value = "Labyrinth-game"
        height = 400
        width = 400
    }
    stage.scene = new Scene(350, 350) {

    val game = new Game
    val floorRectangles = Buffer[Rectangle]()
    val wallRectangles = Buffer[Rectangle]()
    for (i <- game.floors) {
    val rectangle = new Rectangle {
    x = i.location._1 * 10
    y = i.location._2 * 10
    width = 10
    height = 10
    fill = Blue //scalafx.scene.paint.Color
    if (i.underLap) fill = Gray
}
    floorRectangles += rectangle

    }
    for (i <- game.walls) {
    val rectangle = new Rectangle {
    x = i.location._1 * 10
    y = i.location._2 * 10
    width = 10
    height = 10
    fill = Black //scalafx.scene.paint.Color
}
    wallRectangles += rectangle

    }
    var playerRectangle = new Rectangle {
    x = game.player.x
    y = game.player.y
    width = 10
    height = 10
    fill = Red
    }//scalafx.scene.paint.Color
    //floorRectangles.foreach(n => root.children += n)
    //wallRectangles.foreach(n => root.children += n)
    //root.children += playerRectangle
    def hidePlayer(hide: Boolean) {
        if (hide) {
            playerHidden = true
            content = floorRectangles ++ wallRectangles
        } else {
            content = floorRectangles ++ wallRectangles ++ Buffer(playerRectangle)
            playerHidden = false
        }
    }
    hidePlayer(false)
    var playerHidden = false

    var fromDirection = ""
    var onBridge = false
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
            }
            case _ =>
        }
        }
        //root.children += playerRectangle

    }

}


