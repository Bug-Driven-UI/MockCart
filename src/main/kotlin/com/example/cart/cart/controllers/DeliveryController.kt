package com.example.cart.cart.controllers

import com.example.cart.cart.model.DeliveryData
import com.example.cart.cart.model.DeliveryProvider
import com.example.cart.cart.model.DeliveryRecipient
import com.example.cart.cart.model.PaymentData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class DeliveryController {

    @GetMapping("/delivery-providers")
    fun getDeliveryProviders(): List<DeliveryProvider> {
        return GlobalState.deliveryProviders
    }

    @GetMapping("/delivery-info")
    fun getDeliveryData(): DeliveryData {
        return GlobalState.getDeliveryData()
    }

    @GetMapping("/payment-info")
    fun getPaymentData(): PaymentData {
        return GlobalState.getPaymentData()
    }

    @GetMapping("/recipient-info")
    fun getRecipientInfo(): DeliveryRecipient {
        return GlobalState.deliveryRecipient
    }

    @PostMapping("/set-delivery-provider")
    fun setDeliveryProvider(@RequestBody request: GenericIdRequest): DeliveryData {
        GlobalState.selectedDeliveryProviderId = request.id
        return GlobalState.getDeliveryData()
    }

    @PostMapping("/set-payment-method")
    fun setPaymentMethod(@RequestBody request: GenericIdRequest): PaymentData {
        GlobalState.selectedPaymentMethodId = request.id
        return GlobalState.getPaymentData()
    }

    @PostMapping("/set-recipient-data")
    fun setRecipientData(@RequestBody request: DeliveryRecipient): DeliveryRecipient {
        GlobalState.deliveryRecipient = request
        return GlobalState.deliveryRecipient
    }
}