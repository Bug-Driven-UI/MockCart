package com.example.cart.cart.controllers

import com.example.cart.cart.model.Cart
import com.example.cart.cart.model.CartItem
import com.example.cart.cart.model.CartStoreItemsGroup
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class CartController {

    private var cart = Cart(
        itemGroups = listOf(
            CartStoreItemsGroup(
                storeId = "store-1",
                storeName = "O RLY? Books Ltd.",
                storeRating = 4.5,
                storeReviewsCount = 120,
                items = listOf(
                    CartItem(
                        id = "item-1",
                        itemName = "Coding with GPT",
                        imageUrl = "https://raw.githubusercontent.com/denitdao/o-rly-collection/refs/heads/main/public/book_covers/coding-with-gpt.jpeg",
                        baseItemPrice = 25.99,
                        quantity = 2,
                        itemDiscountPercent = 10.0
                    ),
                    CartItem(
                        id = "item-2",
                        itemName = "Z-Index book",
                        imageUrl = "https://www.designer-daily.com/wp-content/uploads/2017/10/fGsXd.jpg",
                        baseItemPrice = 79.99,
                        quantity = 1,
                        itemDiscountPercent = 5.0
                    ),
                    CartItem(
                        id = "item-3",
                        itemName = "Procrastination: The definitive guide",
                        imageUrl = "https://boyter.org/static/books/5.png",
                        baseItemPrice = 29.99,
                        quantity = 3,
                        itemDiscountPercent = 15.0
                    )
                )
            ),
            CartStoreItemsGroup(
                storeId = "store-2",
                storeName = "Tech Gadgets Inc.",
                storeRating = 4.7,
                storeReviewsCount = 250,
                items = listOf(
                    CartItem(
                        id = "item-4",
                        itemName = "Xbox 720",
                        imageUrl = "https://jbreaker.ru/images/stories/pics/adv/xbox-720.jpg",
                        baseItemPrice = 499.99,
                        quantity = 1,
                        itemDiscountPercent = 5.0,
                    ),
                    CartItem(
                        id = "item-5",
                        itemName = "IPhone 20",
                        imageUrl = "https://pbs.twimg.com/media/E-m8s9LWUAY3LW-.jpg:large",
                        baseItemPrice = 999.99,
                        quantity = 1,
                        itemDiscountPercent = 10.0,
                    )
                )
            )
        )
    )

    private val favouriteItemIds = mutableSetOf<String>()

    @GetMapping("/cart")
    fun getCart(): Cart {
        return cart.copy(
            itemGroups = cart.itemGroups.filter {
                it.items.isNotEmpty()
            },
        )
    }

    @GetMapping("/favourites")
    fun getFavourites(): Set<String> {
        return favouriteItemIds
    }

    @PostMapping("/cart/add-favourite")
    fun addFavourite(@RequestParam itemId: String): Set<String> {
        favouriteItemIds.add(itemId)
        return favouriteItemIds
    }

    @DeleteMapping("/cart/remove-favourite")
    fun removeFavourite(@RequestParam itemId: String): Set<String> {
        favouriteItemIds.remove(itemId)
        return favouriteItemIds
    }

    @PostMapping("/cart/update-item-quantity")
    fun updateItemQuantity(
        @RequestParam itemId: String,
        @RequestParam quantity: Int
    ): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                group.copy(
                    items = group.items.mapNotNull { item ->
                        if (item.id == itemId) {
                            if (quantity <= 0) {
                                null
                            } else {
                                item.copy(quantity = quantity)
                            }
                        } else {
                            item
                        }
                    }
                )
            }.filter { it.items.isNotEmpty() }
        )
        return cart
    }

    @PostMapping("/cart/add-item")
    fun addItem(
        @RequestParam storeId: String,
        @RequestParam itemId: String,
        @RequestParam itemName: String,
        @RequestParam imageUrl: String,
        @RequestParam baseItemPrice: Double,
        @RequestParam quantity: Int,
        @RequestParam itemDiscountPercent: Double
    ): Cart {
        val newItem = CartItem(
            id = itemId,
            itemName = itemName,
            imageUrl = imageUrl,
            baseItemPrice = baseItemPrice,
            quantity = quantity,
            itemDiscountPercent = itemDiscountPercent
        )

        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                if (group.storeId == storeId) {
                    group.copy(items = group.items + newItem)
                } else {
                    group
                }
            }
        )

        return cart
    }

    @DeleteMapping("/cart/remove-item")
    fun removeItem(@RequestParam itemId: String): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                group.copy(
                    items = group.items.filter { it.id != itemId }
                )
            }.filter { it.items.isNotEmpty() }
        )
        return cart
    }

    @DeleteMapping("/cart/clear")
    fun clearCart(): Cart {
        cart = cart.copy(itemGroups = emptyList())
        return cart
    }

    @PostMapping("/cart/add-store")
    fun addStore(
        @RequestParam storeId: String,
        @RequestParam storeName: String,
        @RequestParam storeRating: Double,
        @RequestParam storeReviewsCount: Int
    ): Cart {
        val newStore = CartStoreItemsGroup(
            storeId = storeId,
            storeName = storeName,
            storeRating = storeRating,
            storeReviewsCount = storeReviewsCount,
            items = emptyList()
        )

        cart = cart.copy(
            itemGroups = cart.itemGroups + newStore
        )

        return cart
    }

    @DeleteMapping("/cart/remove-store")
    fun removeStore(@RequestParam storeId: String): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.filter { it.storeId != storeId }
        )
        return cart
    }
}