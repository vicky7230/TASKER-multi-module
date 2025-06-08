package com.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

@Serializable
data class Rating(
    val rate: Double,
    val count: Int
)