package com.szucsatti.lemkehowsonkt

import org.jscience.mathematics.number.Rational

internal typealias MatrixOfRationals = Array<Array<Rational>>
internal typealias MatrixOfAny = Array<Array<Any>>

fun MatrixOfAny.rows() = this.size
fun MatrixOfAny.cols() = this[0].size

class Matrix(private val matrix: MatrixOfRationals) {

    private val rows: Int = matrix.size
    private val cols: Int = matrix[0].size

    /* ==================================================================== */
    /* Operations on this matrix                                            */
    /* ==================================================================== */

    fun copy(): Matrix {
        var result = init(this.rows, this.cols)

        this.matrix.forEachIndexed {index, row -> row.copyInto(result[index])}

        return Matrix(result)
    }

    fun getMinimumValue(): Rational? {
        var minimum = matrix[0][0]
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (matrix[row][col].isLessThan(minimum)) {
                    minimum = matrix[row][col]
                }
            }
        }
        return minimum
    }

    fun print() {
        for (row in 0 until rows) {
            print(START_LINE)
            for (col in 0 until cols) {
                print(matrix[row][col].toText())
                if (col != cols - 1) {
                    print(VALUE_DELIMITER)
                }
            }
            print(END_LINE)
            if (row != rows - 1) {
                println()
            }
        }
        println()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val typedOther = other as Matrix
        if (rows != typedOther.rows) return false

        return if (cols != typedOther.cols) false else this.matrix.contentDeepEquals(typedOther.matrix)
    }

    override fun hashCode(): Int {
        var result = matrix.contentDeepHashCode()
        result = 31 * result + rows
        result = 31 * result + cols
        return result
    }

    /* ==================================================================== */
    /* Operations with other matrices                                       */
    /* ==================================================================== */

    fun join(other: Matrix) : Matrix {
        assert(this.rows == other.rows)
        val result = init(this.rows, this.cols + other.cols)

        this.matrix.forEachIndexed{index, row ->
            run {
                row.copyInto(result[index])
                other.matrix[index].copyInto(result[index], destinationOffset = row.size)
            }
        }

        return Matrix(result)
    }


    companion object {
        private const val START_LINE = "|  "
        private const val END_LINE = "  |"
        private const val VALUE_DELIMITER = "  |  "

        val IDENTITY_2X2 = Matrix(arrayOf(
                arrayOf(Rational.ONE, Rational.ZERO),
                arrayOf(Rational.ONE, Rational.ZERO)))
        val COLUMN_ONE = Matrix(arrayOf(
                arrayOf(Rational.ONE),
                arrayOf(Rational.ONE)
        ))

        private fun init(rows: Int, cols: Int) = Array(rows) { Array<Rational>(cols) { Rational.ONE } }

        fun build(vararg values: Array<Any>): Matrix {
            @Suppress("UNCHECKED_CAST")
            return buildFromMatrixOfAny(values as MatrixOfAny)
        }

        private fun buildFromMatrixOfAny(values: MatrixOfAny): Matrix {
            var result = init(values.rows(), values.cols())
            for (row in values.indices) {
                for (col in values[0].indices) {
                    result[row][col] = valueOf(values[row][col])
                }
            }
            return Matrix(result)
        }
    }
}