package com.djk.core;

import org.junit.Test;

import java.util.Date;


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
