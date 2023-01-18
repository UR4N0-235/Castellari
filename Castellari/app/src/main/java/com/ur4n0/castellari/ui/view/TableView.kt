package com.ur4n0.castellari.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        val products: List<Product> by mainViewModel.listOfElements
        Column {
            products.forEach {
                println("product id " + it.id)
                ProductRow(it)
            }
        }
    }
}

@Composable
fun ProductRow(product: Product, mainViewModel: MainViewModel = viewModel()) {
    println("product " + product.id)
    println("quantity " + product.quantity)

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
            var text = remember { product.quantity.toString() }
            println("recomposed " + product.quantity)
            println("new text recomposed " + text)
            TextField(
                value = text,
                singleLine = true,
                onValueChange = {
                    try {
                        if (it == "0" || it == "") {
                            println("user typed 0 or \"\"")
                            product.quantity = 0
                            text = ""
                        } else if (it.length > 1) {
                            println(
                                "user typed one number with length > 1: changed number to " + it.drop(
                                    1
                                )
                            )
                            product.quantity = it.drop(1).toInt()
                            text = it.drop(1)
                            println("Quantity now is " + product.quantity)
                            println("Text is $text")
                        } else {
                            println("user typed $it")
                            product.quantity = it.toInt()
                            text = it
                        }
                    } catch (ex: NumberFormatException) {
                        println("quantity NaN Exception")
                        text = text
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
            val text = remember { mutableStateOf(product.description) }
            TextField(
                value = text.value, onValueChange = { newText ->
                    text.value = newText
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
            val text = remember { mutableStateOf(product.unitPrice.toString()) }
            TextField(
                value = text.value, onValueChange = {
                    try {
                        if (it == "0" || it == "") {
                            text.value = "0.0"
                            product.unitPrice = 0.0
                        } else {
                            product.unitPrice = it.toDouble()
                            text.value = it
                        }
                    } catch (ex: NumberFormatException) {
                        text.value = text.value
                    }
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
            Row {
                Text(
                    "R$ " + (product.quantity * product.unitPrice).toString(),
                    Modifier.fillMaxHeight()
                )
                var text = remember { mutableStateOf("") }
                Text(product.id.toString())
                Button(
                    onClick = {
                        //text.value = elements.indexOf(product).toString()
                        //elements.removeAt(elements.indexOf(product))
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

