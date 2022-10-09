package com.example.daggerhiltexample.model

import com.squareup.moshi.Json

data class ApiDetailResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val types: List<String>,

)


