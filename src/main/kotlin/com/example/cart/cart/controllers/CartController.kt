package com.example.cart.cart.controllers

import com.example.cart.cart.model.Cart
import com.example.cart.cart.model.CartItem
import com.example.cart.cart.model.CartStoreItemsGroup
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
                        isSelected = true,
                        itemName = "Coding with GPT",
                        imageUrl = "https://raw.githubusercontent.com/denitdao/o-rly-collection/refs/heads/main/public/book_covers/coding-with-gpt.jpeg",
                        baseItemPrice = 25.99,
                        quantity = 2,
                        itemDiscountPercent = 10.0
                    ),
                    CartItem(
                        id = "item-2",
                        isSelected = true,
                        itemName = "Z-Index book",
                        imageUrl = "https://www.designer-daily.com/wp-content/uploads/2017/10/fGsXd.jpg",
                        baseItemPrice = 79.99,
                        quantity = 1,
                        itemDiscountPercent = 5.0
                    ),
                    CartItem(
                        id = "item-3",
                        isSelected = true,
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
                        isSelected = true,
                        itemName = "Xbox 720",
                        imageUrl = "https://jbreaker.ru/images/stories/pics/adv/xbox-720.jpg",
                        baseItemPrice = 499.99,
                        quantity = 1,
                        itemDiscountPercent = 5.0,
                    ),
                    CartItem(
                        id = "item-5",
                        isSelected = true,
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
    fun addFavourite(@RequestBody request: IdRequest): Set<String> {
        favouriteItemIds.add(request.itemId)
        return favouriteItemIds
    }

    @DeleteMapping("/cart/remove-favourite")
    fun removeFavourite(@RequestBody request: IdRequest): Set<String> {
        favouriteItemIds.remove(request.itemId)
        return favouriteItemIds
    }

    @PostMapping("/cart/set-item-selected")
    fun setItemSelected(@RequestBody request: SelectItemRequest): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                group.copy(
                    items = group.items.map { item ->
                        if (item.id == request.itemId) {
                            item.copy(isSelected = request.isSelected)
                        } else {
                            item
                        }
                    }
                )
            }
        )
        return cart
    }

    @PostMapping("/cart/set-all-items-selected")
    fun setAllItemsSelected(@RequestBody request: SelectRequest, ): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                group.copy(
                    items = group.items.map { item ->
                        item.copy(isSelected = request.isSelected)
                    }
                )
            }
        )
        return cart
    }

    @PostMapping("/cart/set-store-items-selected")
    fun setStoreItemsSelected(@RequestBody request: SelectStoreRequest): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                if (group.storeId == request.storeId) {
                    group.copy(
                        items = group.items.map { item ->
                            item.copy(isSelected = request.isSelected)
                        }
                    )
                } else {
                    group
                }
            }
        )
        return cart
    }

    @PostMapping("/cart/update-item-quantity")
    fun updateItemQuantity(@RequestBody request: UpdateItemQuantityRequest): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                group.copy(
                    items = group.items.mapNotNull { item ->
                        if (item.id == request.itemId) {
                            if (request.quantity <= 0) {
                                null
                            } else {
                                item.copy(quantity = request.quantity)
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
    fun addItem(@RequestBody request: AddItemRequest): Cart {
        val newItem = CartItem(
            id = request.itemId,
            isSelected = true,
            itemName = request.itemName,
            imageUrl = request.imageUrl,
            baseItemPrice = request.baseItemPrice,
            quantity = request.quantity,
            itemDiscountPercent = request.itemDiscountPercent
        )

        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                if (group.storeId == request.storeId) {
                    group.copy(items = group.items + newItem)
                } else {
                    group
                }
            }
        )

        return cart
    }

    @DeleteMapping("/cart/remove-item")
    fun removeItem(@RequestBody request: IdRequest): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                group.copy(
                    items = group.items.filter { it.id != request.itemId }
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