package com.ur4n0.castellari.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.ur4n0.castellari.model.Product

class MainViewModel : ViewModel() {
    var clientName by mutableStateOf("")
    var clientTelephone by mutableStateOf("")
    var clientVehicle by mutableStateOf("")
    var clientLicensePlate by mutableStateOf("")

    var configDialogStatus by mutableStateOf(false)

    private val _listOfElements: MutableList<Product> = mutableStateListOf()
    val listOfElements = _listOfElements

    fun onNameChange(newString: String) {
        clientName = newString
    }

    fun onTelephoneChange(newString: String) {
        clientTelephone = newString
    }

    fun onVehicleChange(newString: String) {
        clientVehicle = newString
    }

    fun onLicensePlateChange(newString: String) {
        clientLicensePlate = newString
    }

    fun addEmptyProduct() {
        val latestId: Int = newId()
        _listOfElements.add(Product(latestId, 1, "", 0.0))
        Log.d("product.status: ", "added new product")
        Log.d("product.size: ", "size changed to " + _listOfElements.size)
    }

    fun removeProduct(product: Product) {
        _listOfElements.removeAll { it == product }
        Log.d("product.status: ", "removed product")
        Log.d("product.removed.info: ", product.id.toString())
        Log.d("product.removed.info: ", product.quantity.toString())
        Log.d("product.removed.info: ", product.description)
        Log.d("product.removed.info: ", product.unitPrice.toString())
        Log.d("product.size: ", "size changed to " + _listOfElements.size)
    }

    fun newId(): Int {
        if (_listOfElements.isEmpty()) {
            return 1
        }
        return _listOfElements.last().id + 1
    }

    fun isMissingFields(): Boolean {
        return (clientName == "" ||
                clientTelephone == "" ||
                clientLicensePlate == "" ||
                clientVehicle == "" ||
                listOfElements.size == 0)
    }
}