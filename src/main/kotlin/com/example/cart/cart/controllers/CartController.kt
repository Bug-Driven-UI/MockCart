package com.example.cart.cart.controllers

import com.example.cart.cart.controllers.GlobalState.cart
import com.example.cart.cart.controllers.GlobalState.favouriteItemIds
import com.example.cart.cart.controllers.GlobalState.recommededItemsPool
import com.example.cart.cart.model.Cart
import com.example.cart.cart.model.CartItem
import com.example.cart.cart.model.CartStoreItemsGroup
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1")
class CartController {

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
    fun setAllItemsSelected(@RequestBody request: SelectRequest): Cart {
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

    @GetMapping("/cart/get-selected-ids")
    fun getSelectedIds(): SelectedIds {
        return cart.itemGroups.flatMap { group ->
            group.items
                .filter { it.isSelected }
                .map { it.id }
        }
            .let { SelectedIds(it.toSet()) }
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

    @DeleteMapping("/cart/remove-list")
    fun removeItems(@RequestBody request: IdsRequest): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                group.copy(
                    items = group.items.filter { !request.itemIds.contains(it.id) }
                )

            }.filter { it.items.isNotEmpty() }
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
            items = emptyList(),
            recommendedItems = listOf(
                recommededItemsPool.random(),
                recommededItemsPool.random(),
                recommededItemsPool.random(),
            ),
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

    @PostMapping("/cart/add-recommended-item")
    fun addRecommendedItem(@RequestBody recommendedItemAddRequest: RecommendedItemAddRequest): Cart {
        val recommendedItem = recommededItemsPool.first { it.id == recommendedItemAddRequest.itemId }
        val newCartItem = CartItem(
            id = UUID.randomUUID().toString(),
            isSelected = true,
            itemName = recommendedItem.itemName,
            imageUrl = recommendedItem.imageUrl,
            baseItemPrice = recommendedItem.baseItemPrice,
            quantity = 1,
            itemDiscountPercent = recommendedItem.itemDiscountPercent
        )
        cart = cart.copy(
            itemGroups = cart.itemGroups.map { group ->
                if (group.storeId == recommendedItemAddRequest.storeId) {
                    group.copy(
                        items = group.items + newCartItem,
                    )
                } else {
                    group
                }
            }
        )
        return cart
    }
}