package com.ur4n0.castellari

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ur4n0.castellari.ui.theme.CastellariTheme
import com.ur4n0.castellari.ui.view.RenderContents
import com.ur4n0.castellari.util.FileUtil
import com.ur4n0.castellari.util.updatePathToSave


class MainActivity : ComponentActivity() {
    private val activityLauncherSavePath = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.v("onActivity", "code_OK")
            var documentUri: Uri? = result.data?.data
            var path = FileUtil.getFullPathFromTreeUri(documentUri, this)

            Log.v("path selected", "path == $path")
            updatePathToSave(this, path.toString())
        } else {
            Log.e("Castellari error", "on processCode")
        }
    }

    private val activityLauncherShare = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.v("onActivity", "code_OK")
            val path = this.getExternalFilesDir(null)!!.absolutePath.toString()+"/userspdf"

        } else {
            Log.e("Castellari error", "on processCode")
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
                    RenderContents(activityLauncherSavePath, activityLauncherShare)
                }
            }
        }
    }
}
