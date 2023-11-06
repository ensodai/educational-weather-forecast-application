package com.example.myapplication.data.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Weather(
    val current: Current?,
    val location: Location?
)