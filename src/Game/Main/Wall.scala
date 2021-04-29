package Game.Main

class Wall(x: Int, y: Int, overlapping: Boolean) extends Object {
  def location = (x, y)
  def overLap = overlapping
}
