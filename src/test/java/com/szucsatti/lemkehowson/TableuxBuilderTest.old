package com.szucsatti.lemkehowson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;

public class TableuxBuilderTest {

    private Matrix payoff = new Matrix (new long[][]{
            {4,7},
            {4,2}});

    private Matrix expectedMatrixWithIdentity = new Matrix(new long[][]{
             {4,7,1,0,1},
             {4,2,0,1,1}});

     private Matrix expectedIdentityWithMatrix = new Matrix(new long[][]{
             {1,0,4,7,1},
             {0,1,4,2,1}});

    @Test
    public void givenPayoffAndIdentity_OnBuild_returnsTableuxInOrder(){
        Tableux tableux = Tableux.builder()
                .matrix(payoff)
                .identity()
                .build();
        Assertions.assertEquals(expectedMatrixWithIdentity, tableux.getMatrix());
        Assertions.assertEquals(asList(0,1), tableux.getLabels());
    }

    @Test
    public void givenIdentityAndPayoff_OnBuild_returnsTableuxInOrder(){
        Tableux tableux = Tableux.builder()
                .identity()
                .matrix(payoff)
                .build();
        Assertions.assertEquals(expectedIdentityWithMatrix, tableux.getMatrix());
        Assertions.assertEquals(asList(2,3), tableux.getLabels());
    }
}
