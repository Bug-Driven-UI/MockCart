package com.example.cart.cart.model

data class CartItem(
    val id: String,
    val itemName: String,
    val imageUrl: String,
    val baseItemPrice: Double,
    val quantity: Int,
    val itemDiscountPercent: Double,
)
