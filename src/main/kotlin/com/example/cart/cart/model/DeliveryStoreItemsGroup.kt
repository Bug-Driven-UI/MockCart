package com.example.cart.cart.model

data class DeliveryStoreItemsGroup(
    val storeId: String,
    val storeName: String,
    val deliveryProvider: DeliveryProvider,
    val storeItems: List<DeliveryItem>,
)
