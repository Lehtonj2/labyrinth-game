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
        height = 350
        width = 350
    }

    val root = new Pane //Simple pane component
    val scene = new Scene(root) //Scene acts as a container for the scene graph
    stage.scene = scene

    val grid = new Grid(10, 10)
    val gridLocations = grid.locations
    val labyrinth = new Labyrinth
    val floors = labyrinth.createLabyrinth(grid)
    val walls = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1)).locations.filter(n => !floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
    val floorRectangles = Buffer[Rectangle]()
    val wallRectangles = Buffer[Rectangle]()
    for (i <- floors) {
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
    for (i <- walls) {
    val rectangle = new Rectangle {
    x = i.location._1 * 10
    y = i.location._2 * 10
    width = 10
    height = 10
    fill = Black //scalafx.scene.paint.Color
}
    wallRectangles += rectangle

    }
    floorRectangles.foreach(n => root.children += n)
    wallRectangles.foreach(n => root.children += n)


}




