package board

data class Cell(val i: Int, val j: Int)

enum class Direction {
    UP, DOWN, RIGHT, LEFT;

    fun reversed() = when (this) {
        UP -> DOWN
        DOWN -> UP
        RIGHT -> LEFT
        LEFT -> RIGHT
    }
}

interface SquareBoard {
    val width: Int

    fun getCellOrNull(i: Int, j: Int): Cell?
    fun getCell(i: Int, j: Int): Cell

    fun getAllCells(): Collection<Cell>

    fun getRow(i: Int, jRange: IntProgression): List<Cell>
    fun getColumn(iRange: IntProgression, j: Int): List<Cell>

    fun Cell.getNeighbour(direction: Direction): Cell?
}

interface GameBoard<T> : SquareBoard {

    operator fun get(cell: Cell): T?
    operator fun set(cell: Cell, value: T?)

    fun filter(predicate: (T?) -> Boolean): Collection<Cell>
    fun find(predicate: (T?) -> Boolean): Cell?
    fun any(predicate: (T?) -> Boolean): Boolean
    fun all(predicate: (T?) -> Boolean): Boolean
}


class MyBoard(override val width: Int): SquareBoard {

    private val allCell = mutableMapOf<Cell, Cell>()

    init {
        for(i in 1..width){
            for(j in 1..width){
                allCell[Cell(i,j)]= Cell(i,j)
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if(i> width || j> width) null
        else allCell[Cell(i, j)]
    }

    override fun getCell(i: Int, j: Int): Cell {
        if(i> width || j> width) throw IllegalArgumentException("$i and $j must be less than $width")
        return allCell[Cell(i, j)]!!
    }

    override fun getAllCells(): Collection<Cell> {
        return allCell.values
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val data = mutableListOf<Cell>()
        for(j in jRange){
            if(j in 1..width){
                data.add(allCell[Cell(i, j)]!!)
            }
        }

        return data
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val data = mutableListOf<Cell>()
        for(i in iRange){
            if(i in 1..width){
                data.add(allCell[Cell(i, j)]!!)
            }
        }

        return data
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        var cell:Cell? = null

        when{
            direction == Direction.UP && i > 1 -> cell = allCell[Cell(i-1, j)]
            direction == Direction.DOWN && i < width -> cell = allCell[Cell(i+1, j)]
            direction == Direction.LEFT && j > 1 -> cell = allCell[Cell(i, j-1)]
            direction == Direction.RIGHT && j < width -> cell = allCell[Cell(i, j+1)]
        }

        return cell
    }

}

class MyGameBoard<T>(override val width: Int):GameBoard<T> {
    private val allCellValue = mutableMapOf<Cell, T?>()
    private val allCell = mutableMapOf<Cell, Cell>()

    init {
        for(i in 1..width){
            for(j in 1..width){
                allCell[Cell(i,j)]= Cell(i,j)
                allCellValue[Cell(i,j)] = null
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if(i> width || j> width) null
        else allCell[Cell(i, j)]
    }

    override fun getCell(i: Int, j: Int): Cell {
        if(i> width || j> width) throw IllegalArgumentException("$i and $j must be less than $width")
        return allCell[Cell(i, j)]!!
    }

    override fun getAllCells(): Collection<Cell> {
        return allCell.values
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val data = mutableListOf<Cell>()
        for(j in jRange){
            if(j in 1..width){
                data.add(allCell[Cell(i, j)]!!)
            }
        }

        return data
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val data = mutableListOf<Cell>()
        for(i in iRange){
            if(i in 1..width){
                data.add(allCell[Cell(i, j)]!!)
            }
        }

        return data
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        var cell:Cell? = null

        when{
            direction == Direction.UP && i > 1 -> cell = allCell[Cell(i-1, j)]
            direction == Direction.DOWN && i < width -> cell = allCell[Cell(i+1, j)]
            direction == Direction.LEFT && j > 1 -> cell = allCell[Cell(i, j-1)]
            direction == Direction.RIGHT && j < width -> cell = allCell[Cell(i, j+1)]
        }

        return cell
    }

    override fun get(cell: Cell): T? {
        return allCellValue[cell]
    }

    override fun set(cell: Cell, value: T?) {
        allCellValue[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return allCellValue.filter { (_, value)-> predicate.invoke(value) }.keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return allCellValue.filter { (_, value)-> predicate.invoke(value) }.keys.firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return allCellValue.any { (_, value)-> predicate.invoke(value) }
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return allCellValue.all { (_, value)->
            predicate.invoke(value)
        }
    }
}