package com.ur4n0.castellari.ui.components

import com.ur4n0.castellari.ui.theme.CastellariTheme
import com.ur4n0.castellari.R
import com.ur4n0.castellari.model.Product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ur4n0.castellari.viewmodel.InputItem

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
                .weight(4f)
        ) {
            Buttons()
        }
    }
}

@Composable
fun MainViewHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(0.dp, 4.dp)
            .padding(2.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Data: " + getTodayDate()
            )
            InputItem(
                "Cliente",
                "Nome do cliente",
                Modifier
                    .fillMaxWidth()
            )
            InputItem(
                "Veiculo",
                "Tipo do veiculo",
                Modifier
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp, 0.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Vence em 10 dias!"
            )
            InputItem(
                "Telefone",
                "Telefone do cliente",
                Modifier
                    .fillMaxWidth()
            )
            InputItem(
                "Placa",
                "Placa do carro",
                Modifier
                    .fillMaxWidth()
            )
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
        },
            colors = ButtonDefaults
                .buttonColors(
                    backgroundColor = MaterialTheme
                        .colors
                        .primary,
                    contentColor = MaterialTheme
                        .colors
                        .primaryVariant
                )
        ) {
            Text("+")
        }

    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            Button(onClick = {

            },
                colors = ButtonDefaults
                    .buttonColors(
                        backgroundColor = MaterialTheme
                            .colors
                            .primaryVariant,
                        contentColor = MaterialTheme
                            .colors
                            .primary
                    )
            ) {
                Text("Salvar")
            }
        }
        Column {
            Button(onClick = {

            },
                colors = ButtonDefaults
                    .buttonColors(
                        backgroundColor = MaterialTheme
                            .colors
                            .primaryVariant,
                        contentColor = MaterialTheme
                            .colors
                            .primary
                    )
            ) {
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
