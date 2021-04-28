package Game.Main

import scala.collection.mutable.Buffer



class Game {
    var grid = new Grid(10, 10)
    var gridLocations = grid.locations
    var labyrinth = new Labyrinth
    var floors = labyrinth.createLabyrinth(grid)
    var bridges = this.labyrinth.bridgeLocations
    var labyrinthLocations = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1))
    var walls = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1)).locations.filter(n => !floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
    var player = new Character(15, 15)
    def newGrid(width: Int, height: Int) {
        grid = new Grid(width, height)
        gridLocations = grid.locations
    }
    def newLabyrinth() {
        labyrinth = new Labyrinth
        floors = labyrinth.createLabyrinth(grid)
        bridges = labyrinth.bridgeLocations
        labyrinthLocations = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1))
        walls = new Grid((grid.width * 3 - 1), (grid.height * 3 - 1)).locations.filter(n => !floors.map(_.location).contains(n)).map(n => new Wall(n._1, n._2, false))
        player = new Character(grid.width + (grid.width / 2), grid.height + (grid.height / 2))
    }
    def solveLabyrinth(x: Int, y: Int) = {
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
        while (!(x2 == 0 & y2 == 0) & counter > 0) {
            dir match {
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

            //if (!walls.map(n => (n.location._1, n.location._2)).contains((x2, y2 - 1)) & labyrinthLocations.) {
            solveFloors += new Floor(x2, y2, false)
            counter -= 1
            println(x2, y2)
        }
        solveFloors.filter(n => solveFloors.count(_ == n) == 1)

    }
}
