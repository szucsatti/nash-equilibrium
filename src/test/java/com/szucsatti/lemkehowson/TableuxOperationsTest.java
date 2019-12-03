package com.szucsatti.lemkehowson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.szucsatti.lemkehowson.Rational.valueOf;

public class TableuxOperationsTest {

    private Matrix payoffA = new Matrix (new long[][]{
            {6,7},
            {4,10}});

    private Matrix payoffB = new Matrix(new long[][]{
            {3, 7},
            {4, -8}
    });

    private Tableux tableuxA;
    private Tableux tableuxB;

    @BeforeEach
    public void setUp(){
        this.tableuxA = Tableux.builder()
                .identity()
                .matrix(payoffA)
                .build();

        this.tableuxB = Tableux.builder()
                .identity()
                .matrix(payoffB)
                .build();
    }

    @Test
    public void givenTableux_onPivotOnNonExistentLabel_throwsAssertionError(){
        Assertions.assertThrows(AssertionError.class, () -> {
           this.tableuxA.pivot(5);
        });
    }

    @Test
    public void givenTableux_onPivotFirstRow_doPivoting(){
        Tableux actualTableux = this.tableuxA.pivot(2);

        Assertions.assertEquals(new Matrix(new Rational[][]{
                {valueOf(1), valueOf(0), valueOf(6), valueOf(7), valueOf(1)},
                {valueOf(-1), valueOf(3,2), valueOf(0), valueOf(8), valueOf(1,2)},
        }), actualTableux.getMatrix());

        Assertions.assertEquals(Arrays.asList(0,3), actualTableux.getLabels());
    }

    @Test
    public void givenTableux_onPivotSecondRow_doPivoting(){
        Tableux actualTableux = this.tableuxB.pivot(2);

        Assertions.assertEquals(new Matrix(new Rational[][]{
                {valueOf(4,3), valueOf(-1), valueOf(0), valueOf(52,3), valueOf(1, 3)},
                {valueOf(0), valueOf(1), valueOf(4), valueOf(-8), valueOf(1)},
        }), actualTableux.getMatrix());

        Assertions.assertEquals(Arrays.asList(1,3), actualTableux.getLabels());
    }


}
