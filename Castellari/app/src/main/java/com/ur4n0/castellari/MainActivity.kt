package com.ur4n0.castellari

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.ur4n0.castellari.ui.theme.CastellariTheme

import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ur4n0.castellari.ui.view.RenderContents
import com.ur4n0.castellari.util.FileUtil
import com.ur4n0.castellari.util.updatePathToSave


class MainActivity : ComponentActivity() {
    private val activityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            var documentUri: Uri? = result.data?.data
            var path = FileUtil.getFullPathFromTreeUri(documentUri, this)

            Log.d("path selected", "path == $path")
            updatePathToSave(this, path.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CastellariTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RenderContents(activityLauncher)
                }
            }
        }
    }
}
