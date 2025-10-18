package com.example.cart.cart.model

data class CartStoreItemsGroup(
    val storeId: String,
    val storeName: String,
    val storeRating: Double,
    val storeReviewsCount: Int,
    val items: List<CartItem>,
    val recommendedItems: List<RecommendedItem>,
)
