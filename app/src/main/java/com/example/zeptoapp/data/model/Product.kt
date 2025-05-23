package com.example.zeptoapp.data.model


data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val weight: Int = 100,
    val imageRes: Int = 0,
    val category: String? = null,
    val description: String? = null,
    val rating: Rating? = null,
)

data class Rating(
    val review: Double,
    val count: Int,
)