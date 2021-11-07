package com.fifteenpuzzle.ui.main

class Position(var x: Float, var y: Float) {
    private fun subtract(p1: Position, p2: Position): Position {
        return Position(p1.x - p2.x, p1.y - p2.y)
    }

    private fun add(p1: Position, p2: Position): Position {
        return Position(p1.x + p2.x, p1.y + p2.y)
    }

    private fun isValid(matrixSize: Int): Boolean {
        return (this.x in 0f..matrixSize-1f && this.y in 0f..matrixSize-1f)
    }


    fun neighbours(matrixSize: Int): List<Position> {
        val neighbours = mutableListOf<Position>()

        neighbours += subtract(this, Position(0f, 1f))
        neighbours += subtract(this, Position(1f, 0f))
        neighbours += add(this, Position(0f, 1f))
        neighbours += add(this, Position(1f, 0f))
        return neighbours.filter { n -> n.isValid(matrixSize) }
    }


    override fun toString(): String {
        return "P(x: $x, y: $y)"
    }



    operator fun minus(p2: Position): Position {
        val p1 = Position(this.x - p2.x, this.y - p2.y)
        val p3 = Position(p2.x - this.x, p2.y - this.y)
        return if (p1.x > 0 && p1.y > 0)
            p1
        else p3
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }


}