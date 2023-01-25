package com.ur4n0.castellari.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.Page
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ur4n0.castellari.R
import com.ur4n0.castellari.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun createPdf(context: Context, mainViewModel: MainViewModel){
    val document = PdfDocument()
    var logoBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pdflogo)
    var scaledLogoBitmap: Bitmap = Bitmap.createScaledBitmap(logoBitmap, 293, 143, false)

    // pdf is 2480x3508px and 1px == 0.75 postScript ( unit that page info uses )
    // so, the pdf in postScript is 1860x2631
    val pageInfo = PageInfo.Builder(1860, 2631, 1).create()

    val page: Page = document.startPage(pageInfo)
    var canvas: Canvas = page.canvas
    val logoPaint = Paint()
    canvas.drawBitmap(scaledLogoBitmap, 30F, 30F, logoPaint)

    document.finishPage(page)
    val fileName = mainViewModel.clientName + "_" + mainViewModel.clientLicensePlate

    val file = File(getPathToSave(context), "$fileName.pdf")

    try {
        document.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF file generated..", Toast.LENGTH_SHORT).show()
    } catch (error: Exception) {
        error.printStackTrace()
        Toast.makeText(context, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
            .show()
    }
    document.close()
}
