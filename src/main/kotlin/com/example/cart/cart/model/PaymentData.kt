package com.example.cart.cart.model

data class PaymentData(
    val paymentMethods: List<PaymentMethod>,
    val selectedPaymentMethodId: String,
)
