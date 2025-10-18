package com.example.cart.cart.model

data class RecommendedItem(
    val id: String,
    val itemName: String,
    val imageUrl: String,
    val baseItemPrice: Double,
    val itemDiscountPercent: Double,
)
