package com.example.newsviewer
import com.google.gson.annotations.SerializedName

data class Article(

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("url")
    val url: String?
)