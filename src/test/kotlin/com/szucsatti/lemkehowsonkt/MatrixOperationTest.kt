package com.szucsatti.lemkehowsonkt

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

    @Test
    fun givenMatrixOnMinimumValueShouldReturnMinimumValue(){
        assertEquals(valueOf(-8), xMatrix.min())
        assertEquals(valueOf(-17), yMatrix.min())
    }

    @Test
    fun givenMatrixOnCopyShouldReturnNewIdenticalMatrix() {
        val copy = xMatrix.copy()
        assertEquals(xMatrix, copy)
    }

    @Test
    fun givenMatricesOnJoinReturnNewJoinedMatrix(){
        val joinedMatrix = xMatrix.join(yMatrix)
        assertEquals(xyJoined, joinedMatrix)
    }

}