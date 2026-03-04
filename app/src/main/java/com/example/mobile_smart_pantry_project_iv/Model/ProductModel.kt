package com.example.mobile_smart_pantry_project_iv.Model

import kotlinx.serialization.Serializable

@Serializable
data class Product (
    val id: Int,
    val nazwa: String,
    var ilosc: Int,
    val jednostka: String,
    val kategoria: String,
    val zdjecie: String
)