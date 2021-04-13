package Game.Main


import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.scene.Node





object Main extends JFXApp {
val root = new Scene

    stage = new JFXApp.PrimaryStage {
        title.value = "Labyrinth-game"
        height = 400
        width = 600
        scene = root
    }

}




