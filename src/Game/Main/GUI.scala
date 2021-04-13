package Game.Main

import scalafx.application.JFXApp
import scalafx.scene.Scene





object Main extends JFXApp {
val root = new Scene

    stage = new JFXApp.PrimaryStage {
        title.value = "Labyrinth-game"
        height = 400
        width = 400
        scene = root
    }

}




