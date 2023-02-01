package com.ur4n0.castellari.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.Page
import android.graphics.pdf.PdfDocument.PageInfo
import android.util.Log
import android.widget.Toast
import com.ur4n0.castellari.R
import com.ur4n0.castellari.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream

fun createPdf(
    context: Context,
    mainViewModel: MainViewModel
): File {
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
    clientDataPainter.textSize = 32f
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
        "Valor total: " + convertToMonetaryCase(mainViewModel.calcTotalPriceForAllProducts()).toString(),
        clientFieldsLeftSpacing,
        margin + 375,
        clientDataPainter
    )

    clientDataPainter.color = 0xFF000000.toInt()

    canvas.drawText(
        "Gerado em " + getTodayDate(),
        clientFieldsLeftSpacing + 50f,
        margin + 430,
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

    val productDescriptorPainter = Paint()
    productDescriptorPainter.textSize = 32f
    productDescriptorPainter.textAlign = Paint.Align.LEFT

    val productDataPainter = Paint()
    productDataPainter.textSize = 32f
    productDataPainter.textAlign = Paint.Align.CENTER

    mainViewModel.listOfElements.forEachIndexed { index, product ->
        canvas.drawText(
            product.description,
            margin + 10,
            margin + 606f + (index + 1) * 75f,
            productDescriptorPainter
        )

        canvas.drawText(
            "R$ " + convertToMonetaryCase(product.unitPrice * mainViewModel.porcentagePerMonth.toDouble()).toString(),
            contentWidth * 0.6f + margin - (tableHeaderPainter.textSize / 2),
            margin + 606f + (index + 1) * 75f,
            productDataPainter
        )

        canvas.drawText(
            product.quantity.toString(),
            contentWidth * 0.75f + margin - (tableHeaderPainter.textSize / 2),
            margin + 606f + (index + 1) * 75f,
            productDataPainter
        )

        canvas.drawText(
            "R$ " + convertToMonetaryCase(product.unitPrice * product.quantity * mainViewModel.porcentagePerMonth.toDouble()).toString(),
            contentWidth * 0.90f + margin - (tableHeaderPainter.textSize / 2),
            margin + 606f + (index + 1) * 75f,
            productDataPainter
        )
    }

    val totalToPayPainter = Paint()
    totalToPayPainter.textSize = 32f
    totalToPayPainter.textAlign = Paint.Align.LEFT
    totalToPayPainter.color = 0xFFFFFFFF.toInt()

    canvas.drawRect(
        pageInfo.pageWidth - margin - 400f,
        pageInfo.pageHeight - margin - 192f,
        pageInfo.pageWidth - margin,
        pageInfo.pageHeight - margin - 128f,
        blackRectPainter
    )

    canvas.drawRect(
        pageInfo.pageWidth - margin - 368f,
        pageInfo.pageHeight - margin - 96f,
        pageInfo.pageWidth - margin,
        pageInfo.pageHeight - margin - 32f,
        blackRectPainter
    )

    canvas.drawText(
        mainViewModel.monthsToPay + " parcelas de R$ " + convertToMonetaryCase(mainViewModel.totalPriceSplitIntoPaymentsMonths),
        pageInfo.pageWidth - margin - 384f - 16f,
        pageInfo.pageHeight - margin - 128f - 16f,
        totalToPayPainter
    )

    canvas.drawText(
        "Total pago R$ " + convertToMonetaryCase(mainViewModel.totalPriceOfAllProducts),
        pageInfo.pageWidth - margin - 352f - 16f,
        pageInfo.pageHeight - margin - 32f - 16f,
        totalToPayPainter
    )

    document.finishPage(page)
    val fileName =
        mainViewModel.clientName + "_" + mainViewModel.clientLicensePlate + "_" + getTodayDateWithoutSpace()

    removeOldPdfsOnInternalStorage(context)

    val file = File(getPathToSave(context), "$fileName.pdf")

    val fileOnInternalStorage = File(context.filesDir, "$fileName.pdf")

    try {
        document.writeTo(FileOutputStream(file))
        Log.v("external file saved on", file.path)

        file.copyTo(fileOnInternalStorage)
        Log.v("file copyed to internal Storage", fileOnInternalStorage.path)

//        file.copyTo(File(context.filesDir, "teste.pdf"))
//
//        document.writeTo(FileOutputStream(fileOnInternalStorage))

        Toast.makeText(context, "PDF salvado", Toast.LENGTH_SHORT).show()
    } catch (error: Exception) {
        error.printStackTrace()
        Toast.makeText(context, "Falha ao gerar PDF, tente novamente...", Toast.LENGTH_SHORT)
            .show()
    } finally {
        document.close()
    }
    return fileOnInternalStorage
}

// to clear data Usage of app
fun removeOldPdfsOnInternalStorage(context: Context) {
    val files: Array<String> = context.fileList()
    files.forEach {
        val extension: String = it
        Log.v("file on fileList", it)
        if (extension.indexOf(".pdf") != -1) {
            Log.v("file to delete", "${context.filesDir}${File.pathSeparator}$it")
            if(context.deleteFile(it)) Log.d("deleted", it)
        } else {
            Log.v("file without .pdf extension", it)
        }
    }
}