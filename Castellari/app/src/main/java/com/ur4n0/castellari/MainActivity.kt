package com.ur4n0.castellari

import com.ur4n0.castellari.ui.theme.CastellariTheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CastellariTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainContent()
                }
            }

        }
    }
}
@Composable
fun MainContent(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()){
        Row(modifier = Modifier.fillMaxWidth()){
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = "That is the logo app"
            )
        }
        Row(modifier = Modifier.fillMaxWidth().offset(0.dp, 4.dp)){
            Column(modifier = Modifier.weight(1f)){
                Text("Data: " + getTodayDate())
                input("Nome do cliente", "Matheus Verginio")
                input("Veiculo", "Honda")
            }
            Column(modifier = Modifier.weight(1f).offset(4.dp)){
                Text("Vencimento em 10 dias!")
                input("Telefone", "(17) 99673-6229")
                input("Placa", "ABC1D12")
            }
        }
        Table()
    }
}
fun getTodayDate(): String{
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$day/${month+1}/$year"
}
@Composable
fun input(labelText: String, defaultText: String){
    Row{
        var text = remember { mutableStateOf(defaultText)}
        TextField(
            value = text.value,
            onValueChange = { newText ->
            text.value = newText},
            label = {
               Text(text = labelText)
            }
        )
    }
}
@Composable
fun Table(){
    Row(modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally){
            Text("Qtd.")
        }
        Column(modifier = Modifier.weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text("Descricao")
        }
        Column(modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text("Valor unit")
        }
        Column(modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text("Total")
        }
    }
}
@Preview(showBackground = true , showSystemUi = true)
@Composable
fun DefaultPreview() {
    CastellariTheme {
        MainContent()
    }
}
