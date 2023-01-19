package com.ur4n0.castellari.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import com.ur4n0.castellari.model.Product

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ur4n0.castellari.viewmodel.MainViewModel

@Composable
fun TableHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Qtd.")
        }
        Column(
            modifier = Modifier.weight(3f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Descricao")
        }
        Column(
            modifier = Modifier.weight(2f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Valor unit")
        }
        Column(
            modifier = Modifier.weight(2f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total")
        }
    }
}

@Composable
fun TableContent(mainViewModel: MainViewModel = viewModel()) {
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        val products = mainViewModel.listOfElements

        Column {
            products.forEach {
                ProductRow(it)
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProductRow(product: Product, mainViewModel: MainViewModel = viewModel()) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier
                .weight(15f)
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = mutableStateOf(product.quantity.toString())
//            println("recomposed " + product.quantity)
//            println("new text recomposed " + text.value)
            TextField(
                value = text.value,
                singleLine = true,
                onValueChange = {
                    try {
                        if (it == "0" || it == "") {
                            product.quantity = 0
                            text.value = ""
                        } else if (it.length > 1) {
                            product.quantity = it.drop(1).toInt()
                            text.value = it.drop(1)
                        } else {
                            product.quantity = it.toInt()
                            text.value = it
                        }
                    } catch (ex: NumberFormatException) {
                        println("quantity NaN Exception")
                        text.value = product.quantity.toString()
                    }
                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
        }
        Column(
            modifier = Modifier
                .weight(30f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = mutableStateOf(product.description)
            TextField(
                value = text.value,
                onValueChange = {
                    product.description = it
                    text.value = it
                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
        }
        Column(
            modifier = Modifier
                .weight(20f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row{
                Text("R$ ")
                val text = mutableStateOf(product.unitPrice.toString())
                TextField(
                    value = text.value,
                    onValueChange = {
                        try {
                            if (it == "0" || it == "") {
                                product.unitPrice = 0.0
                                text.value = ""
                            } else {
                                product.unitPrice = it.toDouble()
                                text.value = it
                            }
                        } catch (ex: NumberFormatException) {
                            println("unitPrice NaN Exception")
                            text.value = product.unitPrice.toString()
                        }
                    }, colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(20f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    "R$ " + (product.quantity * product.unitPrice).toString(),
                    Modifier.fillMaxHeight()
                )
                Button(
                    onClick = {
                        mainViewModel.removeProduct(product)
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text("X")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreProductView() {
    TableHeader()
    TableContent()
}

