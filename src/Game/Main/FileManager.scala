package Game.Main



import java.io.File
import java.io.PrintWriter
import scala.collection.mutable.Buffer
import scala.io.Source
class FileManager {
  def saveLabyrinth(game: Game) {
    val writer = new PrintWriter(new File("SaveFile.txt"))
    writer.write("")
    writer.write(s"${game.size}\n")
    for (i <- game.floors) {
    val location1 = i.location._1.toString
    val location2 = i.location._2.toString
    val lap = i.overLap.toString
    val bridges = game.bridges.filter(n => ((n._1, n._2)) == ((i.location._1, i.location._2))).map(n => n._3)
    var bridgeDirection = "none"
    if (bridges.nonEmpty) bridgeDirection = new String(bridges.head.trim)
    writer.write(s"${location1}\n")
    writer.write(s"${location2}\n")
    writer.write(s"${lap}\n")
    writer.write(s"${bridgeDirection}\n")
    }
    writer.close()
  }

  def loadLabyrinth(game: Game) = {
    val floors = Buffer[Floor]()
    val bridges = Buffer[(Int, Int, String)]()
    var size = 0
    val test = Source.fromFile("SaveFile.txt")
      var currentX = 0
      var currentY = 0
      var currentLap = false
      var currentDir = "none"
      var stepper = 0
      for (line <- test.getLines()) {
        if (stepper == 5) {
          floors += new Floor(currentX, currentY, currentLap)
          if (currentDir != "none") {
            //if (currentDir == "N") println("yes")
            bridges += ((currentX, currentY, currentDir))
          }
          stepper = 1
        }
        stepper match {
          case 0 => {
             size = line.toInt
             stepper += 1
          }
          case 1 => {
            currentX = line.toInt
            stepper += 1
          }
          case 2 => {
            currentY = line.toInt
            stepper += 1
          }
          case 3 => {
            if (line == "true") currentLap = true else currentLap = false
            stepper += 1
          }
          case 4 => {
            currentDir = line
            stepper += 1
          }
        }

      }

    test.close()
    (floors, bridges, size)
  }

}
