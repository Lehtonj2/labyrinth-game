package Game.Main


import java.io.File
import java.io.PrintWriter
import scala.io.Source
class FileManager {
  def saveLabyrinth(game: Game) {
    val writer = new PrintWriter(new File("SaveFile.txt"))
    for (i <- game.floors) {
    val locationString = i
    writer.write("test")
    }
    writer.close()
  }
  def loadLabyrinth() = {
    val test = Source.fromFile("SaveFile.txt")
      test.getLines.foreach(n => n)
    test.close()
  }

}
