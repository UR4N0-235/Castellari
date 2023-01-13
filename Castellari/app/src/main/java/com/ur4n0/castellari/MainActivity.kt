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

import java.util.Calendar

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
    Column{
        Row{
            LogoView()
        }
        Row{
            Column{
                Text("Data: " + getTodayDate())
                TextField(value = "cliente", onValueChange = {})
                Text("Veiculo: ")
            }
            Column{
                Text("Vencimento em 10 dias!")
                Text("Tel: ")
                Text("Placa: ")
            }
        }
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
fun LogoView(){
    Image(
        painter = painterResource(id = R.drawable.banner),
        contentDescription = "That is the logo app"
    )
}
@Preview(showBackground = true , showSystemUi = true)
@Composable
fun DefaultPreview() {
    CastellariTheme {
        MainContent()
    }
}