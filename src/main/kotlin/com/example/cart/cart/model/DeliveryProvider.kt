package com.example.cart.cart.model

data class DeliveryProvider(
    val id: String,
    val providerName: String,
    val deliveryEstimate: String,
    val deliveryBasePrice: Double,
    val deliveryPriceDiscountPercent: Double,
    val label: String,
)
