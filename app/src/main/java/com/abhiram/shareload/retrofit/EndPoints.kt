package com.abhiram.shareload.retrofit

import com.abhiram.shareload.responses.InstagramResponse
import com.abhiram.shareload.responses.YoutubeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPoints {

    @GET("/smvd/get/youtube")
    suspend fun youtube(@Query("url") url : String) : YoutubeResponse

    @GET("/smvd/get/instagram")
    suspend fun instagram(@Query("url") url : String) : InstagramResponse

}