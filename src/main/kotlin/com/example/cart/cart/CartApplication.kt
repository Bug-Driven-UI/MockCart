package com.example.cart.cart

import com.example.cart.cart.controllers.CartController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [CartController::class])
class CartApplication

fun main(args: Array<String>) {
	runApplication<CartApplication>(*args)
}
