package com.djk.core;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.Date;


public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws UnsupportedEncodingException
    {
//        String s = "https://synconhub.coscoshipping.com/spot?showInvExistOnly=true&showPromotionExistOnly=false&startDate=2024-05-24%2000%3A00&boundType=O&porCityName=%E4%B8%8A%E6%B5%B7%2C%E4%B8%AD%E5%9B%BD&porCityId=738872886232873&fndCityName=Constantza%2CRomania&fndCityId=738874496843885&page=1";
//        String decode = URLDecoder.decode(s, "utf-8");
//        System.out.println(decode);
        String s1 = "cGxpZWtqV01nQlBjUGFPaFFxamVWVTk2UTNKSVZOUjFYdEpLQkx1S2dpV2JKWWdHN21vZUV2VkRxdkY4R1ZYSkFnTlhweTBJcFI1QVEvSmNjVGY2dzRKOUdOcEhPcE5MUEs2bUQwcmYra2NacXJ6ajY2Qy9QTGZrbU5Obk1UNEJhY05tWGZVakE1emRHZ3V6TGU3REdLc2FlSGtNa0xyb0NTU1pQTElueDBYWkltYjZnWUJwQ0daQ1pTRE15OWR6ZWE=";
        String s2 = new String(Base64.getDecoder().decode(s1.getBytes()));
        System.out.println(s2);

        String s3 = "BBCA5DDCD3ADB03E76CA9EDF537BE7BB~YAAQjeBb2tN6seKPAQAAdtgP7hifZn+Lsy0n8LsGoh41qWZ3F6CdvP1aefdLVgqbOTW2tEJtgZV3MgKY0+0H4DUFzL5+pybbVoilbRqmMlldkiQyX9+5gL8tJeemRSVJ2LZi7tgjhK4ynds2cEV0t2rujBSkSaNKzlcrV+kaL1MjE0L5RYmVh+jeNB9Ez+MbncF9DttlRqunnPzKOP7AYV0CkQ7+EAPABVV4nj+9HtVL7A/Dp7lbb9rzVVrn52hCbvqUuCQQNloLboTkZr37NB9fGnSVpt2AdH68yf7SFbZjjz53Xy+S7rJKYVZTwoCWEFv2apO/qapeOlMgKPWQ45nWZwb4Npx7OgMihcOmNNzrkhAoAIlhDrmlhvH243OEJY9QBobz1lCbTjuLo0AFThaF4W8WtL4f9IEWy75b2lG6vi5v4X+Ugna9qmjNNG0nCcoc/RsKvt46Cg==~3225665~4599873";
        String s = Base64.getEncoder().encodeToString(s3.getBytes());
        System.out.println(s);
    }
}
