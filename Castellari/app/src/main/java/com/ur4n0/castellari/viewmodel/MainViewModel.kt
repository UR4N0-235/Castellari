package com.ur4n0.castellari.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ur4n0.castellari.model.Product

class MainViewModel: ViewModel(){
    private val _listOfElements: MutableList<Product> = mutableStateListOf()
    val listOfElements = _listOfElements

    fun addEmptyProduct(){
        val latestId: Int = newId()
        _listOfElements.add(Product(latestId, 1, "", 0.0))
        println("product list size: " + _listOfElements.size)
    }

    fun removeProduct(product: Product) {
        _listOfElements.removeAll{ it == product }
    }

    fun newId():Int {
        if(_listOfElements.isEmpty()){
            return 1
        }
        return _listOfElements.last().id + 1
    }
}