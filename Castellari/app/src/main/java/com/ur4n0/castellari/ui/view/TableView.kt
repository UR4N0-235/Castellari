package com.ur4n0.castellari.ui.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ur4n0.castellari.model.Product
import com.ur4n0.castellari.util.thisCanBeDouble
import com.ur4n0.castellari.util.thisCanBeInteger
import com.ur4n0.castellari.viewmodel.MainViewModel

@Composable
fun TableHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(width = 8.dp))
        Column(
            modifier = Modifier.weight(10f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Qtd")
        }
        Column(
            modifier = Modifier.weight(40f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Descricao")
        }
        Column(
            modifier = Modifier.weight(20f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Valor unit")
        }
        Column(
            modifier = Modifier.weight(20f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total")
        }
        Spacer(modifier = Modifier.weight(9f))
        Spacer(modifier = Modifier.width(width = 8.dp))
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
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.width(width = 8.dp))
        Column(
            modifier = Modifier
                .weight(10f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = mutableStateOf(product.quantity.toString())
            // because in mutableStateOf, text will be 0 if quantity is 0 too
            // but I want when quantity is 0, the text.value has empty
            if(text.value == "0") text.value = ""


            BasicTextField(
                value = text.value,
                onValueChange = {
                    if (it.length <= 2 && thisCanBeInteger(it)) {
                        Log.v("TableView.ProductRow", "now quantity of product ${product.id} is $it")
                        text.value = it
                        product.quantity = it.toInt()
                    } else if (it.isEmpty()) {
                        Log.v("TableView.ProductRow", "now quantity of product ${product.id} is empty")
                        text.value = it
                        product.quantity = 0
                    }
                    mainViewModel.updateTotalPriceValues()
                },
                singleLine = true,
                modifier = Modifier
                    .focusRequester(FocusRequester())
                    .fillMaxHeight()
                    .padding(0.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = fontSize,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Column(
            modifier = Modifier
                .weight(40f)
                .padding( PaddingValues(8.dp, 0.dp) )
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = mutableStateOf(product.description)

            BasicTextField(
                value = text.value,
                onValueChange = {
                    if (it.length <= 32) {
                        product.description = it
                        text.value = it
                        Log.v(
                            "TableView.ProductRow",
                            "now description of product ${product.id} has ${it.length} length and value is $it"
                        )
                    }
                },
                modifier = Modifier
                    .focusRequester(FocusRequester())
                    .fillMaxHeight()
                    .padding(0.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = fontSize,
                    color = Color.White,
                    textAlign = TextAlign.Justify
                ),
            )
        }
        Column(
            modifier = Modifier
                .weight(20f)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = mutableStateOf(product.unitPrice.toString())
            if(text.value == "0.0" || text.value == "0") text.value = ""

            BasicTextField(
                value = text.value,
                onValueChange = {
                    if (it.length <= 7 && thisCanBeDouble(it)) {
                        Log.v("TableView.ProductRow", "now unitPrice of product ${product.id} is $it")
                        text.value = it
                        product.unitPrice = it.toDouble()
                    } else if (it.isEmpty()) {
                        Log.v(
                            "TableView.ProductRow",
                            "now unitPrice of product ${product.id} is empty"
                        )
                        text.value = it
                        product.unitPrice = 0.0
                    }
                    mainViewModel.updateTotalPriceValues()
                },
                modifier = Modifier
                    .focusRequester(FocusRequester())
                    .fillMaxHeight()
                    .padding(3.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = fontSize,
                    color = Color.White,
                    textAlign = TextAlign.Left
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "R$ ", fontSize = fontSize )
//                        Spacer(modifier = Modifier.width(width = 8.dp))
                        innerTextField()
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .weight(20f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "R$ " + (product.quantity * product.unitPrice).toString(),
                Modifier
                    .fillMaxHeight()
                    .padding(0.dp),
                fontSize = fontSize
            )
        }
        Column(
            modifier = Modifier
                .weight(8f)
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    mainViewModel.removeProduct(product)
                    mainViewModel.updateTotalPriceValues()
                },
                modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .size(40.dp),
                contentPadding = PaddingValues(0.dp, 0.dp)
            ) {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = "Fechar",
                    modifier = Modifier
                        .size(40.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .background(Color.White)
                )
            }
        }
        Spacer(modifier = Modifier.width(width = 8.dp))
    }


}

@Preview(showSystemUi = true)
@Composable
fun PreProductView() {
    TableHeader()
    TableContent()
}

