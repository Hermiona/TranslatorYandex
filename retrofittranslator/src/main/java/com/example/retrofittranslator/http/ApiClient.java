package com.example.retrofittranslator.http;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 17.01.2018.
 */

public class ApiClient {
    static final String BASE_URL="https://translate.yandex.net/api/v1.5/tr.json/";
    static final String apiKey="trnsl.1.1.20150521T113341Z.acb496f5f56e4321.b24f6e45a9852010f9f768a4208bc336d97be221";

    private static Retrofit retrofit=null;

    public static Retrofit getClient(){


        if(retrofit == null){
            OkHttpClient okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original=chain.request();
                            HttpUrl httpUrl=original.url();
                            HttpUrl newHttpUrl=httpUrl.newBuilder().addQueryParameter("key", apiKey).build();
                            Request.Builder requestBuilder=original.newBuilder().url(newHttpUrl);
                            Request request=requestBuilder.build();
                            return chain.proceed(request);

                        }
                    }).build();

            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }


}
