package Game.Main


import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
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

    val root = new Pane //Simple pane component
    val scene = new Scene(root) //Scene acts as a container for the scene graph
    stage.scene = scene

    val grid = new Grid(10, 10)

    val labyrinth = new Labyrinth
    val floors = labyrinth.createLabyrinth(grid)
    val floorRectangles = Buffer[Rectangle]()
    for (i <- floors) {
    val rectangle = new Rectangle {
    x = i._1 * 15
    y = i._2 * 15
    width = 10
    height = 10
    fill = Blue //scalafx.scene.paint.Color
}
    floorRectangles += rectangle

    }
    floorRectangles.foreach(n => root.children += n)

}




