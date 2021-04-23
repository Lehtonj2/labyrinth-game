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
        height = 350
        width = 350
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
    content = floorRectangles.toList ++ wallRectangles.toList ++ List(playerRectangle)
    onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
            case KeyCode.Up => game.player.y -= 1
                playerRectangle.y = playerRectangle.y() - 10
            case KeyCode.Down => game.player.y += 1
                playerRectangle.y = playerRectangle.y() + 10
            case KeyCode.Left => game.player.x -= 1
                playerRectangle.x = playerRectangle.x() - 10
            case KeyCode.Right => { game.player.x += 1
            playerRectangle.x = playerRectangle.x() + 10
            }
            case _ =>
        }
        }
        //root.children += playerRectangle


    }



}




