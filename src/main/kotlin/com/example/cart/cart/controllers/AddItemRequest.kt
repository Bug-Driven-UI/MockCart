package com.example.cart.cart.controllers

data class AddItemRequest(
    val storeId: String,
    val itemId: String,
    val itemName: String,
    val imageUrl: String,
    val baseItemPrice: Double,
    val quantity: Int,
    val itemDiscountPercent: Double
)