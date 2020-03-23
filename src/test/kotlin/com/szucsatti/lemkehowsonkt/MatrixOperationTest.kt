package com.szucsatti.lemkehowsonkt

import org.jscience.mathematics.number.Rational.valueOf
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class MatrixOperationTest {

    private val xMatrix = Matrix.build(
            arrayOf(1, 2, 3),
            arrayOf(7, -8, 9),
            arrayOf(13, 14, 15))

    private val yMatrix = Matrix.build(
            arrayOf(4, 5, 6),
            arrayOf(10, 11, 12),
            arrayOf(16, -17, 18))

    private val xyJoined = Matrix.build(
            arrayOf(1, 2, 3, 4, 5, 6),
            arrayOf(7, -8, 9, 10, 11, 12),
            arrayOf(13, 14, 15, 16, -17, 18))

    private val nonZero = Matrix.build(
            arrayOf(4, 5, 6, 100, 200),
            arrayOf(10, 0, 12, 101, 0),
            arrayOf(16, -17, 18, 102, 202))

    private val normalisationConst = 18L

    private val expectedNormalized = Matrix.build(
            arrayOf(1 + normalisationConst, 2 + normalisationConst, 3 + normalisationConst, 4 + normalisationConst, 5 + normalisationConst, 6 + normalisationConst),
            arrayOf(7 + normalisationConst, -8 + normalisationConst, 9 + normalisationConst, 10 + normalisationConst, 11 + normalisationConst, 12 + normalisationConst),
            arrayOf(13 + normalisationConst, 14 + normalisationConst, 15 + normalisationConst, 16 + normalisationConst, -17 + normalisationConst, 18 + normalisationConst))

    @Test
    fun checkMinimum() {
        assertEquals(valueOf(-8), xMatrix.min())
        assertEquals(valueOf(-17), yMatrix.min())
    }

    @Test
    fun checkCopy() {
        val copy = xMatrix.copy()
        assertEquals(xMatrix, copy)
    }

    @Test
    fun checkJoin() {
        val joinedMatrix = xMatrix.join(yMatrix)
        assertEquals(xyJoined, joinedMatrix)
    }

    @Test
    fun checkSplit() {
        val splitMatrix = xyJoined.split()

        assertEquals(xMatrix, splitMatrix[0])
        assertEquals(yMatrix, splitMatrix[1])
    }

    @Test
    fun checkNormalisation() {
        val normalized = xyJoined.normalize()

        assertEquals(expectedNormalized, normalized)
    }

    @Test
    fun checkRowMultiplicationWithPositiveNumber() {
        val multipliedMatrix = xMatrix.multiplyRow(0, valueOf(5))

        assertEquals(Matrix.build(
                arrayOf(5, 10, 15),
                arrayOf(7, -8, 9),
                arrayOf(13, 14, 15)), multipliedMatrix)
    }

    @Test
    fun checkRowMultiplicationWithNegativeNumber() {
        val multipliedRow = xMatrix.multiplyRow(1, valueOf(-1, 2))

        assertEquals(Matrix.build(
                arrayOf(valueOf(1), valueOf(2), valueOf(3)),
                arrayOf(valueOf(-7, 2), valueOf(4), valueOf(-9, 2)),
                arrayOf(valueOf(13), valueOf(14), valueOf(15))), multipliedRow)
    }

    @Test
    fun checkSubtraction() {
        val subtractedMatrix = xMatrix.subtract(0, 1)

        assertEquals(Matrix.build(
                arrayOf(1, 2, 3),
                arrayOf(6, -10, 6),
                arrayOf(13, 14, 15)), subtractedMatrix)
    }

    @Test
    fun checkNonZeroColsOperation(){
        assertEquals(listOf(0,2,3), nonZero.nonZeroCols())
    }

    @Test
    fun checkRowsWithColsSwitch(){
        val switchedMatrix = xMatrix.switchRowsWithCols()

        assertEquals(Matrix.build(
            arrayOf(1, 7, 13),
            arrayOf(2, -8, 14),
            arrayOf(3, 9, 15)), switchedMatrix)

    }




}