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
            itemName = "Samsung Galaxy S24 Ultra Silicone Cover Жёлтый",
            imageUrl = "https://guerteltier.eu/cdn/shop/files/etui-silikonowe-samsung-galaxy-s24-ultra-yellow-00.jpg?v=1706275383&width=1000",
            baseItemPrice = 3000.0,
            itemDiscountPercent = 2.0,
        ),
        RecommendedItem(
            id = "recitem-2",
            itemName = "Samsung Galaxy S24 Ultra Anti-reflecting Screen Protector",
            imageUrl = "https://ae01.alicdn.com/kf/S77c6367adffd44278503e91d239989e8q.jpg",
            baseItemPrice = 1950.0,
            itemDiscountPercent = 0.0,
        ),
        RecommendedItem(
            id = "recitem-3",
            itemName = "Samsung Galaxy Z Flip 7 Синий",
            imageUrl = "https://platform.theverge.com/wp-content/uploads/sites/2/2025/07/257866_Samsung_Galaxy_Z_Flip_7_review_AJohnson_0004.jpg?quality=90&strip=all&crop=16.675,0,66.65,100",
            baseItemPrice = 79000.0,
            itemDiscountPercent = 10.0,
        ),
        RecommendedItem(
            id = "recitem-4",
            itemName = "Samsung Galaxy S25 Ultra 12/512 Титановый Розовый",
            imageUrl = "https://image.cnnturk.com/i/cnnturk/75/740x416/6704df90031ae244b136777a.jpg",
            baseItemPrice = 120000.0,
            itemDiscountPercent = 10.0,
        ),
        RecommendedItem(
            id = "recitem-5",
            itemName = "Samsung Galaxy SmartTag",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/3/3d/Samsung_Galaxy_SmartTag.jpg",
            baseItemPrice = 3000.0,
            itemDiscountPercent = 5.0,
        ),
        RecommendedItem(
            id = "recitem-6",
            itemName = "Apple Зарядное устройство Magsafe 25W",
            imageUrl = "https://photos5.appleinsider.com/gallery/61034-125867-25W-MagSafe-Box-xl.jpg",
            baseItemPrice = 5500.0,
            itemDiscountPercent = 10.0,
        ),
        RecommendedItem(
            id = "recitem-7",
            itemName = "iPhone 17 Pro Max Bare Skin Case Saddle Brown",
            imageUrl = "https://www.bare-cases.com/cdn/shop/files/Bare_Skin_Case_for_iPhone_17_Pro_Max_-_Leather_Case_with_Camera_Control_for_iPhone_17_Pro_Max_-_Saddle_Brown_bb389a25-eff9-4dae-b8fb-162162e1cbbb.jpg?v=1758250202&width=2048",
            baseItemPrice = 8000.0,
            itemDiscountPercent = 32.0,
        ),
        RecommendedItem(
            id = "recitem-8",
            itemName = "Apple Mac Mini M4 16/512 GB",
            imageUrl = "https://trend-wood.ru/upload/dev2fun.imagecompress/webp/iblock/c0d/buprwk5lel0wrr6t9k9he6glj32zzzpr.webp",
            baseItemPrice = 75000.0,
            itemDiscountPercent = 38.0,
        ),
        RecommendedItem(
            id = "recitem-9",
            itemName = "Apple Studio Display",
            imageUrl = "https://www.iphones.ru/wp-content/uploads/2022/03/IMG_0142653.jpg",
            baseItemPrice = 210000.0,
            itemDiscountPercent = 2.0,
        )
    )

    var cart = Cart(
        itemGroups = listOf(
            CartStoreItemsGroup(
                storeId = "store-1",
                storeName = "Galaxystore",
                storeRating = 4.5,
                storeReviewsCount = 120,
                items = listOf(
                    CartItem(
                        id = "item-1",
                        isSelected = true,
                        itemName = "Samsung Galaxy S24 Ultra 12/256 Титановый Серый",
                        imageUrl = "https://techtrendy.ru/wp-content/uploads/2024/03/samsung-galaxy-s24-ultra-obzor-1024x683.webp",
                        baseItemPrice = 75000.00,
                        quantity = 1,
                        itemDiscountPercent = 10.0,
                    ),
                    CartItem(
                        id = "item-2",
                        isSelected = true,
                        itemName = "Samsung Galaxy Buds 3 Pro Серебристый",
                        imageUrl = "https://images.samsung.com/is/image/samsung/assets/uz_ru/audio-sound/galaxy-buds3-pro/feature/galaxy-buds3-pro-new-design-video-endframe-mo_0710.jpg?imbypass=true",
                        baseItemPrice = 11999.00,
                        quantity = 1,
                        itemDiscountPercent = 5.0,
                    ),
                    CartItem(
                        id = "item-3",
                        isSelected = true,
                        itemName = "Samsung Galaxy Watch 8 Classic",
                        imageUrl = "https://rukminim2.flixcart.com/image/480/360/cms-rpd-img/7fa540318e7242ecbb7212d819ff2f02_199c2abd62a_1.jpg.jpeg?q=90",
                        baseItemPrice = 32999.00,
                        quantity = 3,
                        itemDiscountPercent = 15.0,
                    )
                ),
                recommendedItems = recommededItemsPool.take(5),
            ),
            CartStoreItemsGroup(
                storeId = "store-2",
                storeName = "re:Store",
                storeRating = 4.7,
                storeReviewsCount = 250,
                items = listOf(
                    CartItem(
                        id = "item-4",
                        isSelected = true,
                        itemName = "Apple MacBook Pro 14\" (M4, 16 Gb, 512Gb SSD)",
                        imageUrl = "https://9to5mac.com/wp-content/uploads/sites/6/2024/06/MacBook-Pro-M4.jpg?quality=82&strip=all&w=1600",
                        baseItemPrice = 120999.0,
                        quantity = 1,
                        itemDiscountPercent = 5.0,
                    ),
                    CartItem(
                        id = "item-5",
                        isSelected = true,
                        itemName = "Apple iPhone 17 Pro Max 512 GB",
                        imageUrl = "https://shop.a1.by/assets/image/iphone/iphone-17-pro/carousel-1/img-1.jpg",
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
            storeGroups = cart.itemGroups.filter { group -> group.items.any { item -> item.isSelected } }.map { group ->
                DeliveryStoreItemsGroup(
                    storeId = group.storeId,
                    storeName = group.storeName,
                    deliveryProvider = deliveryProviders.first { it.id == selectedDeliveryProviderId },
                    storeItems = group.items.filter { item -> item.isSelected }.map { item ->
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