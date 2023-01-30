package com.ur4n0.castellari.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ur4n0.castellari.model.Product
import com.ur4n0.castellari.util.thisCanBeDouble

class MainViewModel : ViewModel() {
    var clientName by mutableStateOf("")
    var clientTelephone by mutableStateOf("")
    var clientVehicle by mutableStateOf("")
    var clientLicensePlate by mutableStateOf("")

    var configDialogStatus by mutableStateOf(false)

    private val _listOfElements: MutableList<Product> = mutableStateListOf()
    val listOfElements = _listOfElements

    var monthsToPay by mutableStateOf("1")
    var porcentagePerMonth by mutableStateOf("0.0")

    var totalPriceOfAllProducts by mutableStateOf(calcTotalPriceForAllProducts())
    var totalPriceSplitIntoPaymentsMonths by mutableStateOf(calcPriceToPayPerMonth())

    fun onNameChange(newString: String) {
        clientName = newString
        Log.v("MainViewModel.onNameChange: ", "client name changed to $newString")
    }

    fun onTelephoneChange(newString: String) {
        clientTelephone = newString
        Log.v("MainViewModel.onTelephoneChange: ", "client telephone changed to $newString")
    }

    fun onVehicleChange(newString: String) {
        clientVehicle = newString
        Log.v("MainViewModel.onVehicleChange: ", "client vehicle changed to $newString")
    }

    fun onLicensePlateChange(newString: String) {
        clientLicensePlate = newString
        Log.v("MainViewModel.onLicensePlateChange: ", "client licensePlate changed to $newString")
    }

    fun addEmptyProduct() {
        val latestId: Int = newId()
        _listOfElements.add(Product(latestId, 1, "", 0.0))
        Log.v("product.addEmptyProduct: ", "added new product")
        Log.v("product.addEmptyProduct: ", "size changed to " + _listOfElements.size)
    }

    fun removeProduct(product: Product) {
        _listOfElements.removeAll { it == product }
        Log.v("product.removeProduct: ", "removed product")
        Log.v("product.removeProduct: ", product.id.toString())
        Log.v("product.removeProduct: ", product.quantity.toString())
        Log.v("product.removeProduct: ", product.description)
        Log.v("product.removeProduct: ", product.unitPrice.toString())
        Log.v("product.removeProduct: ", "size changed to " + _listOfElements.size)
    }

    private fun newId(): Int {
        if (_listOfElements.isEmpty()) {
            Log.v("MainViewModel.newId", "not id yet, id 1 generated")
            return 1
        }
        Log.v("MainViewModel.newId", "id ${_listOfElements.last().id + 1} generated")
        return _listOfElements.last().id + 1
    }

    fun calcTotalPriceForAllProducts(): Double {
        var result = 0.0
        if (_listOfElements.size > 0) listOfElements.forEach {
            result += it.unitPrice * it.quantity
        }
        result += result * ( porcentagePerMonth.toDouble() / 100)
        Log.v("MainViewModel.calcTotalPriceForAllProducts", "list size ${_listOfElements.size}")
        Log.v("MainViewModel.calcTotalPriceForAllProducts", "totalPriceForAllProducts is $result")
        return result
    }

    fun isMissingFields(): Boolean {
        return (clientName == "" ||
                clientTelephone == "" ||
                clientLicensePlate == "" ||
                clientVehicle == "" ||
                listOfElements.size == 0)
    }

    private fun calcPriceToPayPerMonth(): Double {
        return if (thisCanBeDouble(monthsToPay)) {
            (totalPriceOfAllProducts / monthsToPay.toDouble())
        } else 0.0
    }

    fun updateTotalPriceValues() {
        totalPriceOfAllProducts = calcTotalPriceForAllProducts()
        totalPriceSplitIntoPaymentsMonths = calcPriceToPayPerMonth()
    }
}