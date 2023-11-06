package com.example.myapplication.data.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Condition(
    val code: Int?,
    val icon: String?,
    val text: String?
)