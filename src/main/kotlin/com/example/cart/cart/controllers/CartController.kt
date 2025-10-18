package com.example.cart.cart.controllers

import com.example.cart.cart.model.Cart
import com.example.cart.cart.model.CartItem
import com.example.cart.cart.model.CartStoreItemsGroup
import com.example.cart.cart.model.RecommendedItem
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class CartController {

    private val recommededItemsPool = listOf(
        RecommendedItem(
            id = "recitem-1",
            itemName = "Pizzer",
            imageUrl = "https://static.wikia.nocookie.net/gigglercats/images/5/5d/Graystripe.casual.png/revision/latest?cb=20240301002923",
            baseItemPrice = 300000.0,
            itemDiscountPercent = 75.0,
        ),
        RecommendedItem(
            id = "recitem-2",
            itemName = "Evil larry",
            imageUrl = "https://static.wikia.nocookie.net/idkcatmemes/images/c/cc/Larry.png/revision/latest/thumbnail/width/360/height/450?cb=20241123222002",
            baseItemPrice = 450000.0,
            itemDiscountPercent = 10.0,
        ),
        RecommendedItem(
            id = "recitem-3",
            itemName = "Maxwell",
            imageUrl = "https://static.wikia.nocookie.net/silly-cat/images/d/d5/Maxwell.png/revision/latest/thumbnail/width/360/height/360?cb=20231001194454",
            baseItemPrice = 1000000.0,
            itemDiscountPercent = 5.0,
        ),
        RecommendedItem(
            id = "recitem-4",
            itemName = "Doudou",
            imageUrl = "https://static.wikia.nocookie.net/silly-cat/images/7/76/Oh_God_what_happened..png/revision/latest?cb=20241209023151&format=original",
            baseItemPrice = 500000.0,
            itemDiscountPercent = 2.0,
        ),
        RecommendedItem(
            id = "recitem-5",
            itemName = "Watermelon cat",
            imageUrl = "https://static.wikia.nocookie.net/silly-cat/images/e/e7/Melon_Cat_Species_1.png/revision/latest/scale-to-width-down/1000?cb=20240223181514&format=original",
            baseItemPrice = 123456.0,
            itemDiscountPercent = 99.0,
        ),
        RecommendedItem(
            id = "recitem-6",
            itemName = "Jinx",
            imageUrl = "https://i.pinimg.com/736x/59/11/bf/5911bf3118417fc66fb7d584ed7bd805.jpg",
            baseItemPrice = 489305.0,
            itemDiscountPercent = 10.0,
        ),
        RecommendedItem(
            id = "recitem-7",
            itemName = "Wireless cat",
            imageUrl = "https://static.wikia.nocookie.net/silly-cat/images/4/4f/Wire_Cat.png/revision/latest?cb=20231001190626&format=original",
            baseItemPrice = 3407869.0,
            itemDiscountPercent = 32.0,
        ),
        RecommendedItem(
            id = "recitem-8",
            itemName = "Floppa",
            imageUrl = "https://i.pinimg.com/736x/30/01/50/30015070ac6281713e893d5b9cc45b56.jpg",
            baseItemPrice = 438067.0,
            itemDiscountPercent = 38.0,
        ),
        RecommendedItem(
            id = "recitem-9",
            itemName = "Surprised cat",
            imageUrl = "https://i.pinimg.com/564x/0c/2a/7e/0c2a7e51e9d250be5a12e5d0332d6b38.jpg",
            baseItemPrice = 49705.0,
            itemDiscountPercent = 2.0,
        )
    )

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
                ),
                recommendedItems = recommededItemsPool.take(5),
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
                ),
                recommendedItems = recommededItemsPool.takeLast(4),
            )
        )
    )

    private val favouriteItemIds = mutableSetOf<String>()

    private fun Cart.formatForOutput(): Cart = copy(
        itemGroups = itemGroups.map { group ->
            group.copy(
                recommendedItems = group.recommendedItems.takeIf { group.items.size == 2 } ?: emptyList(),
            )
        }
    )

    @GetMapping("/cart")
    fun getCart(): Cart {
        return cart.copy(
            itemGroups = cart.itemGroups.filter {
                it.items.isNotEmpty()
            },
        ).formatForOutput()
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
        return cart.formatForOutput()
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
        return cart.formatForOutput()
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
        return cart.formatForOutput()
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
        return cart.formatForOutput()
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

        return cart.formatForOutput()
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

        return cart.formatForOutput()
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
        return cart.formatForOutput()
    }

    @DeleteMapping("/cart/clear")
    fun clearCart(): Cart {
        cart = cart.copy(itemGroups = emptyList())
        return cart.formatForOutput()
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

        return cart.formatForOutput()
    }

    @DeleteMapping("/cart/remove-store")
    fun removeStore(@RequestParam storeId: String): Cart {
        cart = cart.copy(
            itemGroups = cart.itemGroups.filter { it.storeId != storeId }
        )
        return cart.formatForOutput()
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
        return cart.formatForOutput()
    }
}