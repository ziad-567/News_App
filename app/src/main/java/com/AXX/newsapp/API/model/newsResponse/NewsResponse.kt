package com.AXX.newsapp.API.model.newsResponse


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("articles")
    val articles: List<Article?>?=null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("totalResults")
    val totalResults: Int? =null,
    @field:SerializedName("code")
     val code: String? = null,
    @field:SerializedName("message")
     val message: String? = null,


)