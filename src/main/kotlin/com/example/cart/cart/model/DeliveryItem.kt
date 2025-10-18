package com.example.cart.cart.model

data class DeliveryItem(
    val id: String,
    val itemName: String,
    val imageUrl: String,
    val quantity: Int,
    val baseItemPrice: Double,
    val itemDiscountPercent: Double,
)
