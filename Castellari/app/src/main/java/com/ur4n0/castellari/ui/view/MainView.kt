package com.ur4n0.castellari.ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ur4n0.castellari.MainActivity
import com.ur4n0.castellari.R
import com.ur4n0.castellari.ui.components.ClientInputItem
import com.ur4n0.castellari.ui.theme.CastellariTheme
import com.ur4n0.castellari.util.*
import com.ur4n0.castellari.viewmodel.MainViewModel

@Composable
fun RenderContents(activityLauncher: ActivityResultLauncher<Intent>) {
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
            FooterButtons(activityLauncher)
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
                onValueChange = { mainViewModel.onTelephoneChange(it) }
            )
            ClientInputItem(
                labelText = "Placa",
                placeholder = "Placa do veiculo",
                modifier = defaultModifier,
                value = mainViewModel.clientLicensePlate,
                onValueChange = { mainViewModel.onLicensePlateChange(it) }
            )
        }
    }
}

@Composable
fun FooterButtons(
    activityLauncher: ActivityResultLauncher<Intent>,
    mainViewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current

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
            modifier = Modifier
                .padding(0.dp, 0.dp)
                .size(40.dp),
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
                if (getPathToSave(context) == "") {
                    activityLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE))
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

                        activityLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE))
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
