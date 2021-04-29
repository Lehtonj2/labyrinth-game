package Game.Main


import java.io.File
import java.io.PrintWriter
import scala.io.Source
class FileManager {
  def saveLabyrinth(game: Game) {
    val writer = new PrintWriter(new File("SaveFile.txt"))
    writer.write("")
    for (i <- game.floors) {
    val location1 = i.location._1.toString
    val location2 = i.location._2.toString
    val lap = i.overLap.toString
    val bridges = game.bridges.filter(n => (n._1, n._2) == (i.location._1, i.location._2)).map(n => n._3)
    var bridgeDirection = "none"
    if (bridges.nonEmpty) bridgeDirection = bridges.head
    writer.write(s"${location1}\n")
    writer.write(s"${location2}\n")
    writer.write(s"${lap}\n")
    writer.write(s"${bridgeDirection}\n")
    }
    writer.close()
  }
  def loadLabyrinth() = {
    val test = Source.fromFile("SaveFile.txt")
      test.getLines.foreach(n => n)
    test.close()
  }

}
