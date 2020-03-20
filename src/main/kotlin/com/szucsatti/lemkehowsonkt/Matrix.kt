package com.szucsatti.lemkehowsonkt

import org.jscience.mathematics.number.Rational
import org.jscience.mathematics.number.Rational.ONE
import org.jscience.mathematics.number.Rational.ZERO


internal typealias MatrixOfRationals = Array<Array<Rational>>
internal typealias MatrixOfAny = Array<Array<Any>>

fun MatrixOfAny.rows() = this.size
fun MatrixOfAny.cols() = this[0].size

fun MatrixOfRationals.rows() = this.size
fun MatrixOfRationals.cols() = this[0].size

fun Array<Rational>.min() : Rational {
    return this.reduce{ left: Rational, right: Rational -> if(left.isLessThan(right)) left else right}
}

class Matrix(private val matrix: MatrixOfRationals) {

    private val rows: Int = matrix.rows()
    private val cols: Int = matrix.cols()

    /* ==================================================================== */
    /* Operations on this matrix                                            */
    /* ==================================================================== */

    fun copy(): Matrix {
        var result = init(rows, cols)

        this.matrix.forEachIndexed {index, row -> row.copyInto(result[index])}

        return Matrix(result)
    }

    fun min(): Rational {
        return this.matrix.asSequence()
                .flatMap { sequenceOf(it.min()) }
                .reduce { left, right -> if(left.isLessThan(right)) left else right }
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

    fun normalize(): Matrix {
        val normalizationConstant: Rational = normalizationConstant()
        val normalized = init(rows, cols)
        if (normalizationConstant.isGreaterOrEqualThan(ZERO)) {

            this.matrix.forEachIndexed{ i, row ->
                run {
                    row.forEachIndexed { j, _ -> normalized[i][j] = matrix[i][j].plus(normalizationConstant)}
                }
            }
//
//            for (row in 0 until rows) {
//                for (col in 0 until cols) {
//                    normalized[row][col] = matrix[row][col].plus(normalizationConstant)
//                }
//            }
        }
        return Matrix(normalized)
    }

    private fun normalizationConstant(): Rational {
        val min = this.min()
        return if (min.isGreaterOrEqualThan(ZERO)) ZERO else min.abs().plus(ONE)
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

    fun split(): Array<Matrix> {
        val halfCol = this.cols / 2

        val firstMatrix = init(this.rows, halfCol)
        val secondMatrix = init(this.rows, halfCol)

        this.matrix.forEachIndexed{ index, row->
            run {
                row.copyInto(firstMatrix[index], endIndex = halfCol )
                row.copyInto(secondMatrix[index], startIndex = halfCol)
            }
        }

        return arrayOf(Matrix(firstMatrix), Matrix(secondMatrix))
    }


    companion object {
        private const val START_LINE = "|  "
        private const val END_LINE = "  |"
        private const val VALUE_DELIMITER = "  |  "

        val IDENTITY_2X2 = Matrix(arrayOf(
                arrayOf(ONE, ZERO),
                arrayOf(ONE, ZERO)))
        val COLUMN_ONE = Matrix(arrayOf(
                arrayOf(ONE),
                arrayOf(ONE)
        ))

        private fun init(rows: Int, cols: Int) = Array(rows) { Array<Rational>(cols) { ONE } }

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