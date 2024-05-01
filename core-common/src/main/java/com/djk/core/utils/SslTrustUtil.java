package com.djk.core.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public class SslTrustUtil
{
    /**
     * https
     */
    public static final String HTTPS_PROTOCOL = "https";

    /**
     * trustAllCerts
     */
    public static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager()
            {
                @Override
                public X509Certificate[] getAcceptedIssuers()
                {
                    return new X509Certificate[]{};
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType)
                {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType)
                {
                }
            }
    };

    /**
     * NullHostnameVerifier
     */
    public static class NullHostnameVerifier implements HostnameVerifier
    {
        @Override
        public boolean verify(String s, SSLSession sslSession)
        {
            return true;
        }
    }
}
