package com.szucsatti.lemkehowsonkt

import org.jscience.mathematics.number.Rational

typealias TwoDimensionalArray<Rational> = Array<Array<Rational>>

class MatrixOperation(private val matrix: TwoDimensionalArray<Rational>) {

    private val rows: Int = matrix.size
    private val cols: Int = matrix[0].size

    /* ==================================================================== */
    /* Operations on this matrix                                            */
    /* ==================================================================== */
    fun copy() : MatrixOperation {
        var copiedElement : TwoDimensionalArray<Rational> = Array(this.rows){Array<Rational>(this.cols){ Rational.ONE} }
        for (rowIndex in 0 until this.rows) {
            for (colIndex in 0 until this.cols){
                copiedElement[rowIndex][colIndex] = this.matrix[rowIndex][colIndex]
            }
        }

        return MatrixOperation(copiedElement)
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

    /* ==================================================================== */
    /* Operations with other matrices                                       */
    /* ==================================================================== */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val typedOther = other as MatrixOperation
        if (rows != typedOther.rows) return false

        return if (cols != typedOther.cols) false else this.matrix.contentDeepEquals(typedOther.matrix)
    }

    override fun hashCode(): Int {
        var result = matrix.contentDeepHashCode()
        result = 31 * result + rows
        result = 31 * result + cols
        return result
    }

    companion object {
        private const val START_LINE = "|  "
        private const val END_LINE = "  |"
        private const val VALUE_DELIMITER = "  |  "

        val IDENTITY_2X2 = MatrixOperation(arrayOf(
                arrayOf(Rational.ONE, Rational.ZERO),
                arrayOf(Rational.ONE, Rational.ZERO)))
        val COLUMN_ONE = MatrixOperation(arrayOf(
                arrayOf(Rational.ONE),
                arrayOf(Rational.ONE)
        ))


        fun build(vararg values: Array<Any>): MatrixOperation {
            var convertedElement: TwoDimensionalArray<Rational> = Array(values.size) { Array<Rational>(values[0].size) { Rational.ONE } }
            for (row in values.indices) {
                for (col in values[0].indices) {
                    convertedElement[row][col] = valueOf(values[row][col])
                }
            }
            return MatrixOperation(convertedElement)
        }
    }
}