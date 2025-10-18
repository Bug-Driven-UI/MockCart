package com.example.cart.cart.controllers

import com.example.cart.cart.model.Cart
import com.example.cart.cart.model.CartItem
import com.example.cart.cart.model.CartStoreItemsGroup
import com.example.cart.cart.model.DeliveryData
import com.example.cart.cart.model.DeliveryItem
import com.example.cart.cart.model.DeliveryProvider
import com.example.cart.cart.model.DeliveryRecipient
import com.example.cart.cart.model.DeliveryStoreItemsGroup
import com.example.cart.cart.model.PaymentData
import com.example.cart.cart.model.PaymentMethod
import com.example.cart.cart.model.RecommendedItem

object GlobalState {
    val recommededItemsPool = listOf(
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

    var cart = Cart(
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

    val favouriteItemIds = mutableSetOf<String>()

    val deliveryProviders = listOf(
        DeliveryProvider(
            id = "avito_delivery",
            providerName = "Авито",
            deliveryEstimate = "1-4 дня",
            deliveryBasePrice = 619.0,
            deliveryPriceDiscountPercent = 70.0,
            label = "Лучшая цена",
        ),
        DeliveryProvider(
            id = "post_office_delivery",
            providerName = "Отделения Почты России",
            deliveryEstimate = "3-4 дня",
            deliveryBasePrice = 789.0,
            deliveryPriceDiscountPercent = 70.0,
            label = "",
        ),
        DeliveryProvider(
            id = "sdek_delivery",
            providerName = "Пункты выдачи СДЭК",
            deliveryEstimate = "4-7 дней",
            deliveryBasePrice = 809.0,
            deliveryPriceDiscountPercent = 70.0,
            label = "",
        ),
        DeliveryProvider(
            id = "ya_delivery",
            providerName = "Пункты выдачи Яндекс доставки",
            deliveryEstimate = "от 4 часов",
            deliveryBasePrice = 539.0,
            deliveryPriceDiscountPercent = 0.0,
            label = "Самый быстрый",
        )
    )

    var deliveryRecipient = DeliveryRecipient(
        fullName = "Князева Екатерина",
        phoneNumber = "+7 800 555-35-35",
        email = "catherineu@gmail.com",
    )

    val paymentMethods = listOf(
        PaymentMethod(
            id = "avito",
            iconUrl = "https://psv4.userapi.com/s/v1/d2/xs79r6d_6lihP2-KLo8DApTT5gaKT-RXevEu9ALjJ0cL3LW_loemFzM8lN4GTCcxB2kzZdJ-LtYHiHluREW0tuAx_kO76RjA5iVXyIc2F0L6F6dZxThbRtbuZvRECCcBWfZaH8leXtbw/avito.png",
            name = "Кошелёк",
            description = "Быстро пополнить через СБП",
            badgeText = "-100 ₽"
        ),
        PaymentMethod(
            id = "tbank",
            iconUrl = "https://psv4.userapi.com/s/v1/d2/oEFim_ba3awjMjtjl9o61imwpNsryZWZ6h5ec7_RKItToUblOI0tXayRcptSe5q1HttorHANWEB0gfjhqQ8w98hF4q0dbs5U0-AZYU17sxHMVesSCOt7nYLJ2mwO9gNGMx3mwCkltGIH/tbank.png",
            name = "Т-Банк",
            description = "Мгновенно через СБП",
            badgeText = ""
        ),
        PaymentMethod(
            id = "sbp",
            iconUrl = "https://psv4.userapi.com/s/v1/d2/p6oUst3ljdoVK8bV68QiYR-y5e95M7yuXI3OS69kIKDSqshukNgSxIvkYu-zgETMCHocrYbyU0hLTOvBOI8x9NbSlk0Z5aEIl8doXAxQRg9U9PaSlTH2lMSPCVKkf5kLxdD5YmHw8lMB/sbp.png",
            name = "СБП",
            description = "Оплата в приложении банка",
            badgeText = "",
        )
    )

    val address = "Москва, ул. Лесная, 7"

    var selectedPaymentMethodId = paymentMethods.first().id

    var selectedDeliveryProviderId = deliveryProviders.first().id

    fun getDeliveryData(): DeliveryData {
        return DeliveryData(
            address = address,
            deliveryProviders = deliveryProviders,
            selectedDeliveryProviderId = selectedDeliveryProviderId,
            storeGroups = cart.itemGroups.map { group ->
                DeliveryStoreItemsGroup(
                    storeId = group.storeId,
                    storeName = group.storeName,
                    deliveryProvider = deliveryProviders.first { it.id == selectedDeliveryProviderId },
                    storeItems = group.items.map { item ->
                        DeliveryItem(
                            id = item.id,
                            itemName = item.itemName,
                            imageUrl = item.imageUrl,
                            quantity = item.quantity,
                            baseItemPrice = item.baseItemPrice,
                            itemDiscountPercent = item.itemDiscountPercent,
                        )
                    }
                )
            }
        )
    }

    fun getPaymentData(): PaymentData {
        return PaymentData(
            paymentMethods = paymentMethods,
            selectedPaymentMethodId = selectedPaymentMethodId,
        )
    }
}