package com.szucsatti.lemkehowsonkt

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class MatrixOperationTest {

    private val xMatrix = MatrixOperation.build(
            arrayOf(1, 2, 3),
            arrayOf(7, -8, 9),
            arrayOf(13, 14, 15))

    private val yMatrix = MatrixOperation.build(
            arrayOf(4, 5, 6),
            arrayOf(10, 11, 12),
            arrayOf(16, -17, 18))

    @Test
    fun givenMatrixOnMinimumValueShouldReturnMinimumValue(){
        assertEquals(valueOf(-8), xMatrix.getMinimumValue())
        assertEquals(valueOf(-17), yMatrix.getMinimumValue())
    }

    @Test
    fun givenMatrixOnCopyShouldReturnNewIdenticalMatrix() {
        val copy = xMatrix.copy()
        assertEquals(xMatrix, copy)
    }

}