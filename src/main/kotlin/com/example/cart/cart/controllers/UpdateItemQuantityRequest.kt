package com.example.cart.cart.controllers

data class UpdateItemQuantityRequest(
    val itemId: String,
    val quantity: Int
)