package com.ur4n0.castellari.model

class Product {
    var quantity: Int = 0
    var description: String = ""
    var unitPrice: Double = 0.0
    var totalPrice: Double = 0.0

    fun calcTotal() {
        this.totalPrice = this.quantity * this.unitPrice
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
