package com.ur4n0.castellari.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.Page
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ur4n0.castellari.R
import com.ur4n0.castellari.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun createPdf(context: Context, mainViewModel: MainViewModel) {
    val margin = 88.58f
    val logoWidth = 600f
    val logoHeight = 400f

    val document = PdfDocument()
    val pageInfo = PageInfo.Builder(1240, 1754, 1).create()
    val page: Page = document.startPage(pageInfo)

    var canvas: Canvas = page.canvas
    val logoPaint = Paint()

    val clienteDataText = Paint()
    clienteDataText.textAlign = Paint.Align.LEFT
    clienteDataText.textSize = 44f
    clienteDataText.color = 0xFFFFFFFF.toInt()

    val blackRect = Paint()
    blackRect.color = 0xFF000000.toInt()

    var logoBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pdflogo)
    var scaledLogoBitmap: Bitmap = Bitmap.createScaledBitmap(
        logoBitmap,
        logoWidth.toInt(), logoHeight.toInt(), false
    )

    canvas.drawBitmap(scaledLogoBitmap, margin, margin, logoPaint)
    canvas.drawRect(
        logoWidth + margin + 30f,
        margin,
        pageInfo.pageWidth - margin,
        logoHeight + margin,
        blackRect
    )

    val clientFieldsLeftSpacing = margin + logoWidth + 50
    canvas.drawText(
        "Cliente: " + mainViewModel.clientName,
        clientFieldsLeftSpacing,
        margin + 75,
        clienteDataText
    )

    canvas.drawText(
        "Telefone: " + mainViewModel.clientTelephone,
        clientFieldsLeftSpacing,
        margin + 150,
        clienteDataText
    )


    canvas.drawText(
        "Veiculo: " + mainViewModel.clientVehicle,
        clientFieldsLeftSpacing,
        margin + 225,
        clienteDataText
    )

    canvas.drawText(
        "Placa: " + mainViewModel.clientLicensePlate,
        clientFieldsLeftSpacing,
        margin + 300,
        clienteDataText
    )

    canvas.drawText(
        "Valor total: " + mainViewModel.calcTotalPriceForAllProducts().toString(),
        clientFieldsLeftSpacing,
        margin + 375,
        clienteDataText
    )

    val orcamentoText = Paint()
    orcamentoText.textAlign = Paint.Align.CENTER
    orcamentoText.textSize = 64f

    canvas.drawText(
        "Orçamento",
        pageInfo.pageWidth / 2f,
        margin + 480f,
        orcamentoText
    )

    canvas.drawRect(
        margin,
        margin + 528f,
        pageInfo.pageWidth - margin,
        margin + 528f + 64f,
        blackRect
    )

    val tableHeaderText = Paint()
    tableHeaderText.color = 0xFFFFFFFF.toInt()
    tableHeaderText.textAlign = Paint.Align.CENTER
    tableHeaderText.textSize = 32f

    val contentWidth = pageInfo.pageWidth - (margin * 2)
    canvas.drawText(
        "Descricao",
        contentWidth / 4f + margin - (tableHeaderText.textSize / 2),
        margin + 528f + 48f,
        tableHeaderText
    )

    canvas.drawText(
        "Preco",
        contentWidth * 0.6f + margin - (tableHeaderText.textSize / 2),
        margin + 528f + 48f,
        tableHeaderText
    )

    canvas.drawText(
        "Qtd",
        contentWidth * 0.75f + margin - (tableHeaderText.textSize / 2),
        margin + 528f + 48f,
        tableHeaderText
    )

    canvas.drawText(
        "Total",
        contentWidth * 0.90f + margin - (tableHeaderText.textSize / 2),
        margin + 528f + 48f,
        tableHeaderText
    )

    document.finishPage(page)
    val fileName =
        mainViewModel.clientName + "_" + mainViewModel.clientLicensePlate + "_" + getTodayDateWithoutSpace()
    val file = File(getPathToSave(context), "$fileName.pdf")
    try {
        document.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF file generated..", Toast.LENGTH_SHORT).show()
    } catch (error: Exception) {
        error.printStackTrace()
        Toast.makeText(context, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
            .show()
    } finally {
        document.close()
    }
}
