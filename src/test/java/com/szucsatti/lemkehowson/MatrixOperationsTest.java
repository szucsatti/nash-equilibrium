package com.szucsatti.lemkehowson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixOperationsTest {

    private Matrix xMatrix = new Matrix(new double[][]{
            {1, 2, 3},
            {7, -8, 9},
            {13, 14, 15}});

    private Matrix yMatrix = new Matrix(new double[][]{
            {4, 5, 6},
            {10, 11, 12},
            {16, -17, 18}});

    private int NORMALIZATION_CONST = 18;

    private Matrix xyJoined = new Matrix(new double[][]{
            {1, 2, 3, 4, 5, 6,},
            {7, -8, 9, 10, 11, 12},
            {13, 14, 15, 16, -17, 18}});

    private Matrix expectedNormalized = new Matrix(new double[][]{
            {1 + NORMALIZATION_CONST, 2 + NORMALIZATION_CONST, 3 + NORMALIZATION_CONST, 4 + NORMALIZATION_CONST, 5 + NORMALIZATION_CONST, 6 + NORMALIZATION_CONST,},
            {7 + NORMALIZATION_CONST, -8 + NORMALIZATION_CONST, 9 + NORMALIZATION_CONST, 10 + NORMALIZATION_CONST, 11 + NORMALIZATION_CONST, 12 + NORMALIZATION_CONST},
            {13 + NORMALIZATION_CONST, 14 + NORMALIZATION_CONST, 15 + NORMALIZATION_CONST, 16 + NORMALIZATION_CONST, -17 + NORMALIZATION_CONST, 18 + NORMALIZATION_CONST}});

    @Test
    public void givenMatrix_OnMinimumValue_returnMinimumValue() {
        Assertions.assertEquals(-8, xMatrix.minimumValue());
        Assertions.assertEquals(-17, yMatrix.minimumValue());
    }

    @Test
    public void givenMatrix_OnCopy_returnsCopy(){
        Matrix copy = new Matrix(xMatrix);

        Assertions.assertEquals(xMatrix, copy);
    }

    @Test
    public void givenMatrix_OnNormalize_returnNormalized(){
        Matrix normalized = this.xyJoined.normalize();

        Assertions.assertEquals(this.expectedNormalized, normalized);
    }

    @Test
    public void givenMatrices_OnJoin_returnJoinedBiMatrix(){
        Matrix joined = xMatrix.join(yMatrix);

        Assertions.assertEquals(this.xyJoined, joined);
    }

    @Test
    public void givenBiMatrix_onSplit_returnSplitMatrices(){
        Matrix[] split = this.xyJoined.split();

        Assertions.assertEquals(xMatrix, split[0]);
        Assertions.assertEquals(yMatrix, split[1]);
    }


}
