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

        String decode = URLDecoder.decode("selectedLang=zh_hans; akacd_IAM_PROD_AM=2147483647~rv=80~id=37e5cadb2f872d45e62c537a3305c00c; CookieInformationConsentError=false; perrec=SS-VEUKM-VPUJD-SE-CC406-MKYTR-PRSGDI; _gcl_au=1.1.779034460.1717602902; _fbp=fb.2.1717602907598.554064735361591830; opEueMonUID=u_4qv6c2rik42lx20z6ob; _gid=GA1.3.1791999870.1717851547; x-maersk-tls=tls1.3; country_code=cn; CookieInformationConsent=%7B%22website_uuid%22%3A%22d116472a-33cc-480c-ae2b-b80c260850c6%22%2C%22timestamp%22%3A%222024-06-10T04%3A53%3A48.621Z%22%2C%22consent_url%22%3A%22https%3A%2F%2Fwww.maersk.com.cn%2Fbook%2F%22%2C%22consent_website%22%3A%22maersk.com%22%2C%22consent_domain%22%3A%22www.maersk.com.cn%22%2C%22user_uid%22%3A%22e61872e1-9699-483f-956d-b2a28186a067%22%2C%22consents_approved%22%3A%5B%22cookie_cat_necessary%22%2C%22cookie_cat_functional%22%2C%22cookie_cat_statistic%22%2C%22cookie_cat_marketing%22%2C%22cookie_cat_unclassified%22%5D%2C%22consents_denied%22%3A%5B%5D%2C%22user_agent%22%3A%22Mozilla%2F5.0%20%28Macintosh%3B%20Intel%20Mac%20OS%20X%2010_15_7%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F125.0.0.0%20Safari%2F537.36%22%7D; _op_jsTiming=1718007330719%7Chttps%3A%2F%2Fwww.maersk.com.cn%2FinstantPrice%2F; ak_bmsc=F886837A1D509CF51D1E6786DC905135~000000000000000000000000000000~YAAQpeBb2uczRsKPAQAAmPWKARi6QGCrLNRmQ8s5wVppCmlMrm5/bg2C74xsVNYgCt8NihEnLRM1YQsnw09ZLnDViugjxtx9fXQmquP3qmejF0P5aCOW/lxZzUQfQ979Cw55NlXXUFSY921k10FarEKBB/Eeb3BwTCFgf0k1b5FVbjIBYDMHFvN6i9XrknY0Wf3ohC3vEDyURL/yko6fqdMFjbpxeXAbgAac80vRk3qU4PaXqhBCiQYBZgu5UR4rpQrbKn2BO8u2GvsZD+a8kpJp+Ke5YfS4ClXDPKeI28hjwU5yxFqvg0jdWAHHvvBy4zcT3lWBGYOsmqPHHotLJjRu5BFBvj8RC/V16/T5gxDdI5UZ/eixz6sccSM0tqp1Z52XkKBXGzXAMHmL2BEnKiS4zokADB3/+4g/FggNzhVJazbJTmfi3vOUKi9FVNW2n583h1rZsZVeGsyUUdJLDw==; maerskUser=6beGUMvS74; akavpau_prod_book=1718013118~id=b3b9c43eb3b25847b9a9c58d026f7b4e; bm_sz=9C795FB048301427DDAB08C6D8628685~YAAQpeBb2jE8RsKPAQAAkTKLARjwg4h4ZYrWLi3s3cUSfkVx85n5qkWLjCW5J0ypR0K5ZA5ZyVYYBZLek7bpSBoKoPoNeS80ikj1m84wFbBgx0BqCRuAnfss2fwxHIoDGo32y9UU1WKkI1Xp+IjXIIOvRIYrVjIaOb+CjWpV0AljGTyl/wn2HgvF9wcl4RxELyH+u65QztZFvIBA1o3IPwagfKjDw111TYiXzu7bTFe8p8DvxL1AI9rw1GZ0P/3a2qkzzUm0H70Ay0oJScFSaS137LJCzRZTPCGt9INf5Emo9Gnxfez9KTWg6RJIvPDTJ0YEKjsBysF4rHb+LBhWQt643uyNdN+xzQ1K7F+p5iIUdkkkJbabmwRAzNbLFzyWFq/maC8H0bwQDDE27AohYJIPxk2UrbdXXl01qWLWObz3JN141ioi10+buhVa1RsT/G2COiWXeXiBE40otuN8f9FqXKp2ZKLq+vohG7/GOrhN5pSceentg91NypwZtgciEaKBFYOW7Br8x+4uvLS/3k7VJf/SI823pdlp04MPv0MS5u6dWjmwk1eUmyhsoiDFCntQYEqlpA==~3225669~4604721; _ga_QCD0MQYKLF=GS1.1.1718012801.18.1.1718012822.0.0.0; sf_ckuserinfo={\"isoCountryFullName\":\"CHINA\",\"isoCountryCode\":\"CN\"}; _ga=GA1.3.741809069.1717602615; bm_sv=EBC6DC00684D082FB1BBF3D6290ACCB7~YAAQpeBb2tRcRsKPAQAAFZGMARjpBcUc4DyfV17ubtv0Hli1U8jg0fYZlq49InUxtZMVR/d2AuLhZRCIgnzo787GAPGaIcaiE0/7MMUkTXyKoRmCCAGHF0JkJYZ+zDJb5P09pqZD7+l1hcTo8KdOJWq6bjSfARXZYxl/WyFkzhWX2oASA4BG69ci/EjRqTUdFikCKcgG1FEYR0nsh8k3T5l8AiDYb0Zk6gr5++ESAESlcZz19yHMBhvA0XOIZn/RTdR+wA==~1; _abck=1C6CCA164D99135BE82DB9DF1E2D5E20~0~YAAQpeBb2uRhRsKPAQAAGAeNAQz6ugcjjYxYmQd3gvteKqN+D6W40TWTj/TOUDT134cL56lBRfi4RxV9i6l5pzHw3lV0T+kgwHYSBKsfA4Fgwz3QwT9ZNoUu2VtsfRxrZnIzCItCKwnoyDilck599tZBWZJ6TKetCUBW8/WlK3126DOoIUkM6lk96UisggeEm/TIPPDXoojEGxMyjGKOoFXbgIYt0LhGSacNwGBqbGKFL5Z461TdDUrgj4+7SHlV6B1qpPmn3w3c92F10mABOWRJvvIjehDxCUWnHhVZ25ETp1sjjW9Csw7TwkkyQ7wySFuQ4FCqAZCuUAoUAu4OxFGOlGDb1n1537iGc42l2sRTJIMeT5dyid4NlTo42nv5Kg==~-1~-1~1718016409");
        System.out.println(decode);
    }
}
