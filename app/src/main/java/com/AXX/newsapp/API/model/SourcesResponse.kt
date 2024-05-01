package com.AXX.newsapp.API.model


import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("sources")
    val sources:List<Source>?=null,

    @SerializedName("status")
    val status:String? =null,

    @SerializedName("code")
    val code:String?=null,

    @SerializedName("message")
    val usermessage :String? =null

    )