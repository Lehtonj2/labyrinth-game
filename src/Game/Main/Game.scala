package Game.Main

import scala.collection.mutable.Buffer



class Game {
    var size = 10 //default values for variables
    var grid = new Grid(10, 10)
    var gridLocations = grid.locations
    var labyrinth = new Labyrinth
    var floors = labyrinth.createLabyrinth(grid)
    var bridges = this.labyrinth.bridgeLocations
    var labyrinthLocations = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1))
    var walls = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1)).locations.filter(n => !floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
    var player = new Character(15, 15)
    val fileManager = new FileManager
    var labyrinthSolved = false

    def newGrid(width: Int, height: Int) { //Creates a new grid.
        grid = new Grid(width, height)
        gridLocations = grid.locations
    }
    def newLabyrinth() { //Creates new values for variables.
        labyrinth = new Labyrinth
        floors = labyrinth.createLabyrinth(grid)
        bridges = labyrinth.bridgeLocations
        labyrinthLocations = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1))
        walls = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1)).locations.filter(n => !floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
        player = new Character(grid.width + (grid.width / 2), grid.height + (grid.height / 2))
    }
    def solveLabyrinth(x: Int, y: Int) = { // Solves the current labyrinth for the player.
        var x2 = x
        var y2 = y
        val solveFloors = Buffer[Floor]()
        while (!walls.map(n => (n.location._1, n.location._2)).contains((x2, y2 - 1)) & x2 >= 0 & y2 >= 0) {
            y2 -= 1
            solveFloors += new Floor(x2, y2, false)
        }
        var dir = "N"
        var wallDir = "W"
        var counter = 50
        while ((!(x2 == 0 & y2 == 0) & solveFloors.size <= labyrinthLocations.gridSize)) {
            if (bridges.map(n => (n._1, n._2)).contains((x2, y2))) {
                dir match {
                    case "N" => y2 += 1
                    case "S" => y2 -= 1
                    case "W" => x2 += 1
                    case "E" => x2 -= 1
                }
            }else dir match {
                case "N" => {
                    if (!walls.map(n => (n.location._1, n.location._2)).contains((x2 - 1, y2)) & floors.map(n => (n.location._1, n.location._2)).contains((x2 - 1, y2)) & wallDir == "W") {
                        x2 -= 1
                        dir = "E"
                        wallDir = "N"
                    }else if (!walls.map(n => (n.location._1, n.location._2)).contains((x2, y2 + 1)) & floors.map(n => (n.location._1, n.location._2)).contains((x2, y2 + 1))) {
                        y2 += 1
                        dir = "N"

                    }else {
                        x2 += 1
                        dir = "W"
                        wallDir = "S"
                    }
                }
                case "S" => {
                    if (!walls.map(n => (n.location._1, n.location._2)).contains((x2 + 1, y2)) & floors.map(n => (n.location._1, n.location._2)).contains((x2 + 1, y2)) & wallDir == "E") {
                        x2 += 1
                        dir = "W"
                        wallDir = "S"
                    }else if (!walls.map(n => (n.location._1, n.location._2)).contains((x2, y2 - 1)) & floors.map(n => (n.location._1, n.location._2)).contains((x2, y2 - 1))) {
                        y2 -= 1
                        dir = "S"

                    }else {
                        x2 -= 1
                        dir = "E"
                        wallDir = "N"
                    }
                }
                case "W" => {
                    if (!walls.map(n => (n.location._1, n.location._2)).contains((x2, y2 + 1)) & floors.map(n => (n.location._1, n.location._2)).contains((x2, y2 + 1)) & wallDir == "S") {
                        y2 += 1
                        dir = "N"
                        wallDir = "W"
                    }else if (!walls.map(n => (n.location._1, n.location._2)).contains((x2 + 1, y2)) & floors.map(n => (n.location._1, n.location._2)).contains((x2 + 1, y2))) {
                        x2 += 1
                        dir = "W"
                    }else {
                        y2 -= 1
                        dir = "S"
                        wallDir = "E"
                    }
                }
                case "E"=> {
                    if (!walls.map(n => (n.location._1, n.location._2)).contains((x2, y2 - 1)) & floors.map(n => (n.location._1, n.location._2)).contains((x2, y2 - 1)) & wallDir == "N") {
                        y2 -= 1
                        dir = "S"
                        wallDir = "E"
                    }else if (!walls.map(n => (n.location._1, n.location._2)).contains((x2 - 1, y2)) & floors.map(n => (n.location._1, n.location._2)).contains((x2 - 1, y2))) {
                        x2 -= 1
                        dir = "E"
                    }else {
                        y2 += 1
                        dir = "N"
                        wallDir = "W"
                    }
                }
            }

            solveFloors += new Floor(x2, y2, false)

            counter -= 1
        }
        val solvedBuffer = Buffer[Floor]()
        if (solveFloors.size <= labyrinthLocations.gridSize) { //Removes parts that shouldn't be displayed.
        val solved = solveFloors.filter(n => !((((solveFloors.map(m => m.location).contains((n.location._1 + 1, n.location._2))) | (solveFloors.map(m => m.location).contains((n.location._1 - 1, n.location._2)))) & solveFloors.map(m => m.location).contains((n.location._1, n.location._2 + 1)) & solveFloors.map(m => m.location).contains((n.location._1, n.location._2 - 1))) |
          (((solveFloors.map(m => m.location).contains((n.location._1, n.location._2 + 1))) | (solveFloors.map(m => m.location).contains((n.location._1, n.location._2 - 1)))) & solveFloors.map(m => m.location).contains((n.location._1 + 1, n.location._2)) & solveFloors.map(m => m.location).contains((n.location._1 - 1, n.location._2)))))

        for (i <- solved) {
            if (solveFloors.indexOf(i) > 0 & solveFloors.indexOf(i) < solveFloors.size - 1) {
                if (solved.contains(solveFloors(solveFloors.indexOf(i) + 1)) & solved.contains(solveFloors(solveFloors.indexOf(i) - 1))) {
                    solvedBuffer += i
                }
            }else solvedBuffer += i

        }
            labyrinthSolved = true
        }else solvedBuffer.empty

        solvedBuffer
    }

}
