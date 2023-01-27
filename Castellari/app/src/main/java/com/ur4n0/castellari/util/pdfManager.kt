package com.ur4n0.castellari.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.Page
import android.graphics.pdf.PdfDocument.PageInfo
import android.widget.Toast
import com.ur4n0.castellari.R
import com.ur4n0.castellari.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream

fun createPdf(context: Context, mainViewModel: MainViewModel) {
    val margin = 88.58f
    val logoWidth = 600f
    val logoHeight = 400f

    val document = PdfDocument()
    val pageInfo = PageInfo.Builder(1240, 1754, 1).create()
    val page: Page = document.startPage(pageInfo)

    val canvas: Canvas = page.canvas
    val logoPaint = Paint()

    val clientDataPainter = Paint()
    clientDataPainter.textAlign = Paint.Align.LEFT
    clientDataPainter.textSize = 44f
    clientDataPainter.color = 0xFFFFFFFF.toInt()

    val blackRectPainter = Paint()
    blackRectPainter.color = 0xFF000000.toInt()

    val logoBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pdflogo)
    val scaledLogoBitmap: Bitmap = Bitmap.createScaledBitmap(
        logoBitmap,
        logoWidth.toInt(), logoHeight.toInt(), false
    )

    canvas.drawBitmap(scaledLogoBitmap, margin, margin, logoPaint)
    canvas.drawRect(
        logoWidth + margin + 30f,
        margin,
        pageInfo.pageWidth - margin,
        logoHeight + margin,
        blackRectPainter
    )

    val clientFieldsLeftSpacing = margin + logoWidth + 50
    canvas.drawText(
        "Cliente: " + mainViewModel.clientName,
        clientFieldsLeftSpacing,
        margin + 75,
        clientDataPainter
    )

    canvas.drawText(
        "Telefone: " + mainViewModel.clientTelephone,
        clientFieldsLeftSpacing,
        margin + 150,
        clientDataPainter
    )


    canvas.drawText(
        "Veiculo: " + mainViewModel.clientVehicle,
        clientFieldsLeftSpacing,
        margin + 225,
        clientDataPainter
    )

    canvas.drawText(
        "Placa: " + mainViewModel.clientLicensePlate,
        clientFieldsLeftSpacing,
        margin + 300,
        clientDataPainter
    )

    canvas.drawText(
        "Valor total: " + mainViewModel.calcTotalPriceForAllProducts().toString(),
        clientFieldsLeftSpacing,
        margin + 375,
        clientDataPainter
    )

    val tableTitlePainter = Paint()
    tableTitlePainter.textAlign = Paint.Align.CENTER
    tableTitlePainter.textSize = 64f

    canvas.drawText(
        "Orçamento",
        pageInfo.pageWidth / 2f,
        margin + 480f,
        tableTitlePainter
    )

    canvas.drawRect(
        margin,
        margin + 528f,
        pageInfo.pageWidth - margin,
        margin + 528f + 64f,
        blackRectPainter
    )

    val tableHeaderPainter = Paint()
    tableHeaderPainter.color = 0xFFFFFFFF.toInt()
    tableHeaderPainter.textAlign = Paint.Align.CENTER
    tableHeaderPainter.textSize = 32f

    val contentWidth = pageInfo.pageWidth - (margin * 2)
    canvas.drawText(
        "Descricao",
        contentWidth / 4f + margin - (tableHeaderPainter.textSize / 2),
        margin + 528f + 48f,
        tableHeaderPainter
    )

    canvas.drawText(
        "Preco",
        contentWidth * 0.6f + margin - (tableHeaderPainter.textSize / 2),
        margin + 528f + 48f,
        tableHeaderPainter
    )

    canvas.drawText(
        "Qtd",
        contentWidth * 0.75f + margin - (tableHeaderPainter.textSize / 2),
        margin + 528f + 48f,
        tableHeaderPainter
    )

    canvas.drawText(
        "Total",
        contentWidth * 0.90f + margin - (tableHeaderPainter.textSize / 2),
        margin + 528f + 48f,
        tableHeaderPainter
    )

    val productDataPainter = Paint()
    productDataPainter.textSize = 32f
    productDataPainter.textAlign = Paint.Align.LEFT

    mainViewModel.listOfElements.forEachIndexed { index, product ->
        canvas.drawText(
            product.description,
            contentWidth / 4f + margin - (productDataPainter.textSize / 2),
            margin + 606f + (index + 1) * 75f,
            productDataPainter
        )

        canvas.drawText(
            product.unitPrice.toString(),
            contentWidth * 0.6f + margin - (productDataPainter.textSize / 2),
            margin + 606f + (index + 1) * 75f,
            productDataPainter
        )

        canvas.drawText(
            product.quantity.toString(),
            contentWidth * 0.75f + margin - (productDataPainter.textSize / 2),
            margin + 606f + (index + 1) * 75f,
            productDataPainter
        )

        canvas.drawText(
            (product.unitPrice * product.quantity).toString(),
            contentWidth * 0.90f + margin - (productDataPainter.textSize / 2),
            margin + 606f + (index + 1) * 75f,
            productDataPainter
        )
    }

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