package com.ur4n0.castellari.ui.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ur4n0.castellari.model.Product
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
    val fontSize: TextUnit = 3.em

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(15f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = product.quantity.toString(),
                onValueChange = {
//                    try {
//                        if (it == "0" || it == "") {
//                            product.quantity = 0
//                            text.value = ""
//                        } else if (it.length > 1) {
//                            product.quantity = it.drop(1).toInt()
//                            text.value = it.drop(1)
//                        } else {
//                            product.quantity = it.toInt()
//                            text.value = it
//                        }
//                    } catch (ex: NumberFormatException) {
//                        println("quantity NaN Exception")
//                        text.value = product.quantity.toString()
//                    }
                    if(it.length <= 2) product.quantity = it.toInt()
                },
                singleLine = true,
                modifier = Modifier
                    .focusRequester(FocusRequester())
                    .fillMaxHeight()
                    .padding(0.dp),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = fontSize
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                modifier = Modifier
                    .focusRequester(FocusRequester())
                    .fillMaxHeight()
                    .padding(0.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = fontSize
                ),
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
            Row {
                val text = mutableStateOf(product.unitPrice.toString())
                TextField(
                    value = text.value,
                    modifier = Modifier
                        .focusRequester(FocusRequester())
                        .fillMaxHeight()
                        .padding(10.dp),

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
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = fontSize
                    ),
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
                    Modifier
                        .fillMaxHeight()
                        .padding(0.dp),
                    fontSize = fontSize
                )
                Button(
                    onClick = {
                        mainViewModel.removeProduct(product)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier
                        .padding(0.dp, 0.dp)
                        .size(0.dp),
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

