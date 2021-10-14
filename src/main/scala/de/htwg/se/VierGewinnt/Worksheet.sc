val a = List(19, 50, 8, 1, 365).sorted

val colors = List("green", "red", "yellow", "blue").sorted

colors(1)

val b = ("Thu Ha", 21)
val c = ("Orkan", "AIN")

b._1
b._2

c._2

def add5(num:Int):Int = {
  num + 5
}
val d = add5(9)

case class Cell(value:Int) {
  def isSet:Boolean = value != 0
}

val cell1= Cell(2)
cell1.isSet

val cell2= Cell(0)
cell2.isSet

trait IceCream{
  def flavour():Any         // Abstract method
  def category():Any={         // Non-abstract method
    println("Ice cream")
  }
}

class StrawberryIceCream extends IceCream{
  def flavour():Any={
    println("Strawberry")
  }
}

var v = new StrawberryIceCream()
v.flavour()
v.category()

var number = 10;                       // Initialization
while( number<=20 ){                // Condition
  println(number);
  number = number+2                        // Incrementation
}

var map = Map("A"->"Auto","B"->"Banane")             // Creating map
println(map("A"))                            // Accessing value by using key
var newMap = map+("C"->"Computer")                  // Adding a new element to map
println(newMap)
var removeElement = newMap - ("B") // Removing an element from map

println(removeElement)
println(newMap("C"))

for (i <- 1 to 5) yield i //Vector

case class Field(cells: Array[Cell])

val field1 = Field(Array.ofDim[Cell](1))
field1.cells(0)=cell1

case class House(cells:Vector[Cell])

val house = House(Vector(cell1,cell2))

house.cells(0).value
house.cells(0).isSet

case class Player(name: String)
val player = Player("Thu Ha")
player.name
val playerList = List(player)
val player2 = Player("Orkan")
playerList :+ player2
playerList

val matrix = Vector(
  Vector("a1", "a2", "a3", "a4", "a5", "a6" ),
  Vector("b1", "b2", "b3", "b4", "b5", "b6"),
  Vector("c1", "c2", "c3", "c4", "c5", "c6"),
  Vector("d1", "d2", "d3", "d4", "d5", "d6"),
  Vector("e1", "e2", "e3", "e4", "e5", "e6"),
  Vector("f1", "f2", "f3", "f4", "f5", "f6"),
  Vector("g1", "g2", "g3", "g4", "g5", "g6"))

//(x)(y)  x=Buchstabe y=Zahl a1=unten links
matrix(1)(5)
matrix(3)(4)

enum Chip:
  case Yellow, Red, Empty

Chip.Yellow
Chip.Red
Chip.Empty


val matrix2 = Vector(
  Vector(Chip, "a2", "a3", "a4", "a5", "a6" ),
  Vector("b1", "b2", "b3", "b4", "b5", "b6"),
  Vector("c1", "c2", "c3", "c4", "c5", "c6"),
  Vector("d1", "d2", "d3", "d4", "d5", "d6"),
  Vector("e1", "e2", "e3", "e4", "e5", "e6"),
  Vector("f1", "f2", "f3", "f4", "f5", "f6"),
  Vector("g1", "g2", "g3", "g4", "g5", "g6"))

case class Spielbrett(rows: Vector[Vector[Chip]]):
  def cell(row: Int, col: Int) = rows(row)(col)
  def fill(filling: Chip): Spielbrett = copy(Vector.tabulate(3, 3) { (row, col) => filling })
  def replaceCell(row: Int, col: Int, cell: Chip) = copy(rows.updated(row, rows(row).updated(col, cell)))

val m = Spielbrett(Vector(Vector(Chip.Yellow, Chip.Yellow, Chip.Yellow, Chip.Red, Chip.Red, Chip.Red), Vector(), Vector(), Vector(), Vector(), Vector(), Vector()))
m.cell(0, 1)
val m2 = m.fill(Chip.Empty)
m2.replaceCell(1, 1, Chip.Red)

