package com.djk.core;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;


public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws UnsupportedEncodingException
    {
        String s = "https://synconhub.coscoshipping.com/spot?showInvExistOnly=true&showPromotionExistOnly=false&startDate=2024-05-24%2000%3A00&boundType=O&porCityName=%E4%B8%8A%E6%B5%B7%2C%E4%B8%AD%E5%9B%BD&porCityId=738872886232873&fndCityName=Constantza%2CRomania&fndCityId=738874496843885&page=1";
        String decode = URLDecoder.decode(s, "utf-8");
        System.out.println(decode);
    }
}
