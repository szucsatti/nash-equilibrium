package com.szucsatti.lemkehowson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LemkeHowsonTest {

    private Matrix payoffRowPlayer = new Matrix(new long[][]{
            {1, 3},
            {2, 1}});

    private Matrix payoffColumnPlayer = new Matrix(new long[][]{
            {3, 1},
            {1, 3}});

    private LemkeHowson classUnderTest;

    @BeforeEach
    public void setUp(){
        this.classUnderTest = new LemkeHowson(payoffRowPlayer, payoffColumnPlayer);
    }

    @Test
    public void testLemkeHowson(){
        this.classUnderTest.algorithm();

        this.classUnderTest.print();
    }

}
