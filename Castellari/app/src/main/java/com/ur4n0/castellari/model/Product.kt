package com.ur4n0.castellari.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class Product {
    var quantity: Int = 1
    var description: String = ""
    var unitPrice: Double = 0.0
    var totalPrice = 0.0

    fun calcTotal(): String {
        return (this.quantity * this.unitPrice).toString()
    }

    constructor()
    constructor(quantity: Int, description: String, unitPrice: Double) {}
}

fun getSomeProducts(): List<Product> {
    var products: List<Product> = listOf()
    products.plus(Product(2, "parachoque", 400.00))
    products.plus(Product(4, "sinto de seguranca", 100.00))
    products.plus(Product(2, "Mascara de gaz", 50.00))
    products.plus(Product(2, "Assentos", 500.00))
    products.plus(Product(1, "Bateria", 120.00))
    products.plus(Product(4, "Portas", 800.00))
    return products
}
