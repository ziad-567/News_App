package com.AXX.newsapp.API

import com.AXX.newsapp.API.model.SourcesResponse
import com.AXX.newsapp.API.model.newsResponse.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources")
    fun getNewsSources(
        @Query("apiKey") apikey:String = Constants.apikey,
        ):Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(
        @Query("apiKey") apikey:String = Constants.apikey,
        @Query("sources") sources:String ,

        ):Call<NewsResponse>
}