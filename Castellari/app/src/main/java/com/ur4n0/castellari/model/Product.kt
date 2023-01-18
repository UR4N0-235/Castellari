package com.ur4n0.castellari.model

data class Product(
    val id: Int,
    var quantity: Int,
    var description: String,
    var unitPrice: Double
)

//val elements = mutableStateListOf<Product>()
//
//fun addProduct(product: Product) {
//    elements.add(product)
//}
//
//fun removeProduct(product: Product) {
//    elements.removeAll{ it == product }
//}
//
//fun newId():Int {
//    if(elements.isEmpty()){
//        return 1
//    }
//    return elements.last().id + 1
//}