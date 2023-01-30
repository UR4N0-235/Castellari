package com.ur4n0.castellari.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ur4n0.castellari.R
import com.ur4n0.castellari.ui.components.ClientInputItem
import com.ur4n0.castellari.util.*
import com.ur4n0.castellari.viewmodel.MainViewModel

@Composable
fun RenderContents(
    activityLauncher: ActivityResultLauncher<Intent>,
    activityLauncherShare: ActivityResultLauncher<Intent>
) {
    ConfigDialog(activityLauncher)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        LogoLayout(context = LocalContext.current)

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(7f)
        ) {
            ClientInputs()
            RenderTable()
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {
            FooterButtons(activityLauncher, activityLauncherShare)
        }
    }
}

@Composable
fun RenderTable() {
    TableHeader()
    TableContent()
}

@Composable
fun ClientInputs(mainViewModel: MainViewModel = viewModel()) {
    val defaultModifier: Modifier = Modifier.fillMaxWidth()
    val context = LocalContext.current

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
            ClientInputItem(
                labelText = "Cliente",
                placeholder = "Nome do cliente",
                modifier = defaultModifier,
                value = mainViewModel.clientName,
                onValueChange = { mainViewModel.onNameChange(it) }
            )
            ClientInputItem(
                labelText = "Veiculo",
                placeholder = "Tipo do veiculo",
                modifier = defaultModifier,
                value = mainViewModel.clientVehicle,
                onValueChange = { mainViewModel.onVehicleChange(it) }
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
            ClientInputItem(
                labelText = "Telefone",
                placeholder = "Telefone do cliente",
                modifier = defaultModifier,
                value = mainViewModel.clientTelephone,
                onValueChange = { mainViewModel.onTelephoneChange(it) },
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            ClientInputItem(
                labelText = "Placa",
                placeholder = "Placa do veiculo",
                modifier = defaultModifier,
                value = mainViewModel.clientLicensePlate,
                onValueChange = { it ->
                    if (it.length <= 7) mainViewModel.onLicensePlateChange(it)
                    if (it.length > 7) {
                        Toast
                            .makeText(
                                context,
                                "O maximo de caracteres de uma placa e 7...",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun FooterButtons(
    activityLauncher: ActivityResultLauncher<Intent>,
    activityLauncherShare: ActivityResultLauncher<Intent>,
    mainViewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current

    Column( // all footer column
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row( // first row
            Modifier
                .fillMaxWidth()
                .weight(60f)
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(Modifier.weight(80f)) { // column of datas about total price
                Row(
                    Modifier
                        .fillMaxWidth()
//                        .height(30.dp)
                        .background(Color.Transparent)
                ) {
                    Row(Modifier.weight(50f)) {
                        Text("Parcelado em : ")
                        // declaring this, because if client type nothing, the ui needs to render empty field
                        var textMonthsToPay by mutableStateOf(mainViewModel.monthsToPay)
                        if (textMonthsToPay == "0") textMonthsToPay = ""

                        BasicTextField(
                            value = textMonthsToPay,
                            onValueChange = {
                                if (thisCanBeInteger(it)) { // if it can be int, then verify if is less than 12 months
                                    if (it.toInt() <= 12) mainViewModel.monthsToPay = it
                                    if (it.toInt() > 12) {
                                        Toast
                                            .makeText(
                                                context,
                                                "limite maximo de parcelas definido para 12...",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                }
                                if (it.isEmpty()) {
                                    textMonthsToPay = ""
                                    mainViewModel.monthsToPay = "0"
                                }
                                mainViewModel.updateTotalPriceValues()
                            },
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                textAlign = TextAlign.Left
                            ),
                            modifier = Modifier.width(IntrinsicSize.Min),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(text = " x")
                    }
                    Row(Modifier.weight(50f)) {
                        Text("com juros de ")
                        // declaring this, because if client type nothing, the ui needs to render empty field
                        var textPercentagePerMonth by mutableStateOf(mainViewModel.porcentagePerMonth)
                        if (textPercentagePerMonth == "0") textPercentagePerMonth = ""

                        BasicTextField(
                            value = textPercentagePerMonth,
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .focusRequester(FocusRequester())
                                .onFocusChanged {
                                    textPercentagePerMonth =
                                        if (textPercentagePerMonth == "") "0.0" else textPercentagePerMonth
                                },
                            onValueChange = {
                                if (thisCanBeDouble(it)) { // if it can be int, then verify if is less than 12 months
                                    if (it.toDouble() > 0 && it.toDouble() < 100) mainViewModel.porcentagePerMonth =
                                        it
                                }
                                if (it.isEmpty() || it == "0.0" || it == ".0" || it == "0") {
                                    textPercentagePerMonth = ""
                                    mainViewModel.porcentagePerMonth = "0"
                                }
                                mainViewModel.updateTotalPriceValues()
                            },
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                textAlign = TextAlign.Left
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(text = "%")
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row {
                            Text(
                                "Total: R$ ${convertToMonetaryCase(mainViewModel.totalPriceOfAllProducts)}",
                                fontSize = 3.5.em
                            )
                        }
                        Row {
                            Text(
                                "Valor por parcela: R$ ${convertToMonetaryCase(mainViewModel.totalPriceSplitIntoPaymentsMonths)}",
                                fontSize = 3.5.em
                            )
                        }
                    }
                }
            }
            Column(Modifier.weight(20f)) {
                Button(
                    onClick = {
                        mainViewModel.addEmptyProduct()
                    },
                    colors = ButtonDefaults
                        .buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                    modifier = Modifier
                        .padding(0.dp, 0.dp)
                        .size(60.dp),
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
        }
        Row(
            Modifier
                .weight(40f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (getPathToSave(context) == "") {
                        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                        intent.putExtra("process", "savePath")
                        Log.d("stringTextExtra", intent.getStringExtra("process")!!)
                        activityLauncher.launch(intent)
                    } else if (mainViewModel.isMissingFields()) {
                        Toast
                            .makeText(
                                context,
                                "Por favor, preencha os campos primeiro...",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    } else {
                        createPdf(context, mainViewModel)
                    }
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
                    if (mainViewModel.isMissingFields()) {
                        Toast
                            .makeText(
                                context,
                                "Por favor, preencha os campos primeiro...",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    } else {
                        val file = createPdf(context, mainViewModel)
                        val uri = FileProvider.getUriForFile(
                            context,
                            "com.ur4n0.castellari.fileprovider",
                            file
                        )

                        val share = Intent()
                        share.action = Intent.ACTION_SEND
                        share.putExtra("process", "share")
                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                        share.type = "application/pdf"

                        activityLauncherShare.launch(share)
                    }
                },
                colors = ButtonDefaults
                    .buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .size(40.dp),
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
}

@Composable
fun LogoLayout(context: Context, mainViewModel: MainViewModel = viewModel()) {
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
                mainViewModel.configDialogStatus = true
            },
            modifier = Modifier
                .padding(0.dp, 0.dp)
                .size(40.dp),
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun ConfigDialog(
    activityLauncher: ActivityResultLauncher<Intent>,
    mainViewModel: MainViewModel = viewModel()
) {
    val context: Context = LocalContext.current

    if (mainViewModel.configDialogStatus) {
        AlertDialog(
            onDismissRequest = { mainViewModel.configDialogStatus = false },
            title = { Text("Deseja alterar o local onde salvar os PDFs?") },
            text = { Text("local atual: " + getPathToSave(context)) },
            confirmButton = {
                TextButton(
                    onClick = {

                        Toast
                            .makeText(context, "confirmado", Toast.LENGTH_SHORT)
                            .show()

                        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                        intent.putExtra("process", "savePath")
                        Log.d("stringTextExtra", intent.getStringExtra("process")!!)
                        activityLauncher.launch(intent)
                        mainViewModel.configDialogStatus = false
                    }
                ) {
                    Text("Alterar")
                }
            }
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DefaultPreview() {
//    CastellariTheme {
//        RenderContents()
//    }
//}
