package com.ur4n0.castellari.ui.components

import androidx.compose.foundation.layout.*
import com.ur4n0.castellari.model.Product

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


private val elements = mutableStateListOf(Product())
val products: List<Product> = elements

fun addProduct(product: Product) {
    elements.add(product)
}

fun removeProduct(product: Product) {
    elements.remove(product)
}

//@Composable
//fun TableView() {
//    TableHeader()
//    TableBody()
//}

@Composable
fun TableHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Qtd.")
        }
        Column(
            modifier = Modifier.weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Descricao")
        }
        Column(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Valor unit")
        }
        Column(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total")
        }
    }
}

@Composable
fun TableContent() {
    Row(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        ProductList(products)
    }
}

@Composable
fun ProductList(products: List<Product>) {
    Column {
        products.forEach { product ->
            ProductRow(product)
        }
    }
}

@Composable
fun ProductRow(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier
                .weight(10f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = remember { mutableStateOf(product.quantity.toString()) }
            TextField(
                value = text.value,
                onValueChange = { newText ->
                    run {
                        text.value = newText
                        product.calcTotal()
                    }
                },
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
                value = text.value,
                onValueChange = { newText ->
                    text.value = newText
                },
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
                value = text.value,
                onValueChange = { newText ->
                    run {
                        text.value = newText
                        product.calcTotal()
                    }
                },
            )
        }
        Column(
            modifier = Modifier
                .weight(20f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = remember { mutableStateOf(product.totalPrice.toString()) }
            TextField(
                value = text.value,
                onValueChange = { newText ->
                    text.value = newText
                },
                readOnly = true
            )
        }
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                removeProduct(product)
            }) {
                Text("X")
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

