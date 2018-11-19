package com.socialnetwork.services;


import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class AppRetrofit {

    private static AppRetrofit instance;
    ApiServices apiServices;


    public AppRetrofit() {
        //code for retrofit 2.0

        Interceptor HEADER_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader(ApiServices.APP_VERSION, "")
                        .addHeader(ApiServices.AUTHENTICATION_KEY, "")
                        .build();
                return chain.proceed(request);
            }
        };

        //for logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //setting up client
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(HEADER_INTERCEPTOR);
        client.interceptors().add(interceptor);
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    X509Certificate[] cArrr = new X509Certificate[0];
                    return cArrr;
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] chain,
                                               final String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(final X509Certificate[] chain,
                                               final String authType) throws CertificateException {
                }
            }};

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            client.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //rest adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServices.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        /*.serializeNulls()*/
                        .create()))
                .build();


        apiServices = retrofit.create(ApiServices.class);

    }

    public static AppRetrofit getInstance() {
        if (instance == null)
            instance = new AppRetrofit();
        return instance;
    }

    public ApiServices getApiServices() {
        return apiServices;
    }
}
