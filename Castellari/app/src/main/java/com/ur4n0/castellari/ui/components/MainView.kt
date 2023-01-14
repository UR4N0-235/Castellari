package com.ur4n0.castellari.ui.components

import com.ur4n0.castellari.ui.theme.CastellariTheme
import com.ur4n0.castellari.R
import com.ur4n0.castellari.model.Product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import java.util.Calendar

@Composable
fun MainContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Logo()
        MainViewHeader()
        TableHeader()

        Column(Modifier.weight(5f)) {
            TableContent()
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(4f)) {
            Buttons()
        }
    }
}

@Composable
fun Input(labelText: String, defaultText: String) {
    Row {
        val text = remember { mutableStateOf(defaultText) }
        TextField(value = text.value, onValueChange = { newText ->
            text.value = newText
        }, label = {
            Text(text = labelText)
        })
    }
}

@Composable
fun MainViewHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(0.dp, 4.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Data: " + getTodayDate())
            Input("Nome do cliente", "")
            Input("Veiculo", "")
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .offset(4.dp)
        ) {
            Text("Vencimento em 10 dias!")
            Input("Telefone", "")
            Input("Placa", "")
        }
    }
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.banner),
        contentDescription = "That is the logo app"
    )
}

@Composable
fun Buttons() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = {
            addProduct(Product())
        }) {
            Text("+")
        }

    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            Button(onClick = {

            }) {
                Text("Salvar")
            }
        }
        Column {
            Button(onClick = {

            }) {
                Text("Compartilhar")
            }
        }
    }
}

fun getTodayDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$day/${month + 1}/$year"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    CastellariTheme {
        MainContent()
    }
}
