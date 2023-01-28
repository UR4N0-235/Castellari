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

    private fun newId(): Int {
        if (_listOfElements.isEmpty()) {
            return 1
        }
        return _listOfElements.last().id + 1
    }

    fun calcTotalPriceForAllProducts(): Double {
        var result = 0.0
        if (_listOfElements.size > 0) listOfElements.forEach {
            result += it.unitPrice * it.quantity
        }
        result += result * ( porcentagePerMonth.toDouble() / 100)
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
        val realPercentage = (porcentagePerMonth.toDouble() / 100) + 1
        val totalPaymentWithPercentage = totalPriceOfAllProducts * realPercentage

        return if (thisCanBeDouble(monthsToPay)) {
            (totalPaymentWithPercentage / monthsToPay.toDouble())
        } else 0.0
    }

    fun updateTotalPriceValues() {
        totalPriceOfAllProducts = calcTotalPriceForAllProducts()
        totalPriceSplitIntoPaymentsMonths = calcPriceToPayPerMonth()
    }
}