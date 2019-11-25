package com.szucsatti.lemkehowson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatrixTest {

    private Matrix matrix;

    @BeforeEach
    void setUp() {
        matrix = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, -8, 9}});
    }

    @Test
    void givenMatrix_OnMinimumValue_returnMinimumValue() {
        Assertions.assertEquals(-8, matrix.minimumValue());
    }

}
