package com.ur4n0.castellari.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ur4n0.castellari.model.Product

class MainViewModel: ViewModel(){
    private val _listOfElements: MutableState<MutableList<Product>> = mutableStateOf(mutableListOf())
    val listOfElements: State<List<Product>> = _listOfElements

    fun addProduct(product: Product){
        _listOfElements.value.add(product)
//        println("new products is :")
//        _listOfElements.value.forEach(){
//            println("id: " + it.id)
//            println("quantity: " + it.quantity)
//            println("description: " + it.description)
//            println("unit price: " + it.unitPrice)
//        }
    }

    fun removeProduct(product: Product) {
        _listOfElements.value.removeAll(){ it == product }
    }

    fun newId():Int {
        if(_listOfElements.value.isEmpty()){
            return 1
        }
        return _listOfElements.value.last().id + 1
    }
}