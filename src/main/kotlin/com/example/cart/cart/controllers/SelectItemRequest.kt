package com.example.cart.cart.controllers

data class SelectItemRequest(
    val itemId: String,
    val isSelected: Boolean,
)