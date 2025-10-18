package com.example.cart.cart.model

data class DeliveryData(
    val address: String,
    val deliveryProviders: List<DeliveryProvider>,
    val selectedDeliveryProviderId: String,
    val storeGroups: List<DeliveryStoreItemsGroup>,
)
