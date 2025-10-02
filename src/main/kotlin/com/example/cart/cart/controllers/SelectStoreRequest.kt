package com.example.cart.cart.controllers

data class SelectStoreRequest(
    val storeId: String,
    val isSelected: Boolean
)