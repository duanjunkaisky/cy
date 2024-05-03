package com.djk.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        long t = 1715216400000L;
        Date date = new Date(t);
        System.out.println(date);
    }
}
