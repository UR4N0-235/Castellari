package com.ur4n0.castellari.ui.view

import android.content.Context
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ur4n0.castellari.R
import com.ur4n0.castellari.ui.theme.CastellariTheme
import com.ur4n0.castellari.util.getPathToSave
import com.ur4n0.castellari.viewmodel.MainViewModel
import java.util.*

@Composable
fun MainContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        TopLayout(context = LocalContext.current)

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(7f)
        ) {
            MainViewHeader()
            TableHeader()
            TableContent()
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(3f)
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
            .padding(2.dp, 8.dp),
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
                "Placa do veiculo",
                Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun Buttons(mainViewModel: MainViewModel = viewModel()) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = {
                mainViewModel.addEmptyProduct()
            },
            colors = ButtonDefaults
                .buttonColors(
                    backgroundColor = Color.Transparent
                ),
            modifier = Modifier.padding(0.dp, 0.dp).size(40.dp),
            contentPadding = PaddingValues(0.dp, 0.dp),
        ) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = "Adicionar",
                modifier = Modifier
                    .size(40.dp)
                    .border(BorderStroke(1.dp, Color.Black))
                    .background(MaterialTheme.colors.primary)
            )
        }
    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
            },
            colors = ButtonDefaults
                .buttonColors(
                    backgroundColor = MaterialTheme
                        .colors
                        .primaryVariant,
                    contentColor = MaterialTheme
                        .colors
                        .primary
                ),

        ) {
            Text("Salvar")
        }

        Button(
            onClick = {

            },
            colors = ButtonDefaults
                .buttonColors(
                    backgroundColor = Color.Transparent,
                ),
            modifier = Modifier.padding(0.dp, 0.dp).size(40.dp),
            contentPadding = PaddingValues(0.dp, 0.dp),
        ) {
            Icon(
                Icons.Rounded.Share,
                contentDescription = "Compartilhar",
                modifier = Modifier
                    .size(40.dp)
                    .border(BorderStroke(1.dp, Color.Black))
                    .background(MaterialTheme.colors.primaryVariant)
            )
        }
    }
}


@Composable
fun TopLayout(context: Context) {
    Box(
        Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "That is the logo app"
        )

        Button(
            onClick = {
                Toast
                    .makeText(
                        context,
                        "Botao config apertado",
                        Toast.LENGTH_SHORT
                    )
                    .show()
        Column {
            Button(onClick = {

            },
            modifier = Modifier.padding(0.dp, 0.dp).size(40.dp),
            contentPadding = PaddingValues(0.dp, 0.dp),
            colors = ButtonDefaults
                .buttonColors(
                    backgroundColor = Color.Transparent
                )
        ) {
            Icon(
                Icons.Rounded.Settings,
                contentDescription = "Configuracaoes",
                modifier = Modifier
                    .size(40.dp)
                    .border(BorderStroke(1.dp, Color.Black))
                    .background(MaterialTheme.colors.primaryVariant)
            )
        }
    }
}

@Composable
fun InputItem(labelText: String, placeholder: String, modifier: Modifier) {
    Row {
        val text = remember { mutableStateOf("") }

        OutlinedTextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            label = {
                Text(text = labelText)
            },
            placeholder = {
                Text(text = placeholder)
            },
            modifier = modifier
        )

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
