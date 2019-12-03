package com.szucsatti.lemkehowson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.szucsatti.lemkehowson.Rational.valueOf;


class MatrixOperationsTest {

    private Matrix xMatrix = new Matrix(new long[][]{
            {1, 2, 3},
            {7, -8, 9},
            {13, 14, 15}});

    private Matrix yMatrix = new Matrix(new long[][]{
            {4, 5, 6},
            {10, 11, 12},
            {16, -17, 18}});

    private Matrix nonZero = new Matrix(new long[][]{
            {4, 5, 6, 100, 200},
            {10, 0, 12, 101, 0},
            {16, -17, 18, 102, 202}});

    private int NORMALIZATION_CONST = 18;

    private Matrix xyJoined = new Matrix(new long[][]{
            {1, 2, 3, 4, 5, 6,},
            {7, -8, 9, 10, 11, 12},
            {13, 14, 15, 16, -17, 18}});

    private Matrix expectedNormalized = new Matrix(new long[][]{
            {1 + NORMALIZATION_CONST, 2 + NORMALIZATION_CONST, 3 + NORMALIZATION_CONST, 4 + NORMALIZATION_CONST, 5 + NORMALIZATION_CONST, 6 + NORMALIZATION_CONST,},
            {7 + NORMALIZATION_CONST, -8 + NORMALIZATION_CONST, 9 + NORMALIZATION_CONST, 10 + NORMALIZATION_CONST, 11 + NORMALIZATION_CONST, 12 + NORMALIZATION_CONST},
            {13 + NORMALIZATION_CONST, 14 + NORMALIZATION_CONST, 15 + NORMALIZATION_CONST, 16 + NORMALIZATION_CONST, -17 + NORMALIZATION_CONST, 18 + NORMALIZATION_CONST}});

    @Test
    public void givenMatrix_OnMinimumValue_returnMinimumValue() {
        Assertions.assertEquals(valueOf(-8), xMatrix.getMinimumValue());
        Assertions.assertEquals(valueOf(-17), yMatrix.getMinimumValue());
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

    @Test
    public void givenMatrix_onRatio_calculateRatio(){
        Assertions.assertEquals(xMatrix.ratio(1, 2, 0, 2), valueOf(3));
        Assertions.assertEquals(xMatrix.ratio(2, 1, 1, 1), valueOf(-14, 8));
        Assertions.assertEquals(xMatrix.ratio(2, 2, 1, 2), valueOf(15, 9));
    }

    @Test
    public void givenMatrix_onMultiplyRowByPositiveNumber_multipliesRow(){
        Matrix actualMatrix = xMatrix.multiplyRow(0, valueOf(5));

        Assertions.assertEquals(new Matrix(new long[][]{
                {5, 10, 15},
                {7, -8, 9},
                {13, 14, 15}}), actualMatrix);
    }
    @Test
    public void givenMatrix_onMultiplyRowByNegativeNumber_multipliesRow(){
        Matrix actualMatrix = xMatrix.multiplyRow(1, valueOf(-1,2));

        Assertions.assertEquals(new Matrix(new Rational[][]{
                {valueOf(1), valueOf(2), valueOf(3)},
                {valueOf(-7,2), valueOf(4), valueOf(-9,2)},
                {valueOf(13), valueOf(14), valueOf(15)}}), actualMatrix);
    }

    @Test
    public void givenMatrix_onSubtract_doSubtraction(){
        final Matrix actualMatrix = xMatrix.subtract(0, 1);

        Assertions.assertEquals(new Matrix(new long[][]{
                {1, 2, 3},
                {6, -10, 6},
                {13, 14, 15}}), actualMatrix);
    }

    @Test
    public void givenMatrix_OnNoZeroCols_findNonZeroCols(){
        Assertions.assertEquals(Arrays.asList(0,2,3), nonZero.getNoZeroCols());
    }

}
