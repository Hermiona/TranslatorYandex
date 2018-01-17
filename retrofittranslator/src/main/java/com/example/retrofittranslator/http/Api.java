package com.example.retrofittranslator.http;

import com.example.retrofittranslator.models.GetLangsResponse;
import com.example.retrofittranslator.models.TranslateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ASUS on 17.01.2018.
 */

public interface Api {

    @FormUrlEncoded
    @POST("translate")
    Call<TranslateResponse> translate(
            @Query("lang") String lang,
            @Query("options") int options,
            @Field("text") String text

    );

    @POST("getLangs")
    Call<GetLangsResponse> getLangs(
            @Query("ui") String ui
    );

}
