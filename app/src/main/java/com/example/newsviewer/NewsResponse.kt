package com.example.newsviewer

import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @SerializedName("articles")
    val articles: List<Article>
)