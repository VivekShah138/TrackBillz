package com.example.trackbillz.view_bills.data

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper
import com.example.trackbillz.view_bills.presentation.util.ItemDuration
import com.example.trackbillz.view_bills.presentation.util.ItemType
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FileManager(
    private val context: Context
) {

    fun exportBillsToExcel(bills: List<Bill>,filterWrapper: FilterWrapper): SaveFileResult{
        val type = extractType(filterWrapper)
        val duration = extractDuration(filterWrapper)
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Bills")


        val titleFont = getFont(workbook = workbook, contentType = ContentType.Title)
        val headerFont = getFont(workbook = workbook, contentType = ContentType.Header)
        val bodyFont = getFont(workbook = workbook, contentType = ContentType.Body)

        val titleStyle = getStyle(workbook = workbook, font = titleFont, fieldType = FieldType.Descriptive)
        val headerStyle = getStyle(workbook = workbook, font = headerFont, fieldType = FieldType.Descriptive)
        val descriptiveBodyStyle = getStyle(workbook = workbook, font = bodyFont, fieldType = FieldType.Descriptive)
        val monetaryBodyStyle = getStyle(workbook = workbook, font = bodyFont, fieldType = FieldType.Monetary)


        // Title
        val title = "$type $duration"

        val titleRow = sheet.createRow(0)
        titleRow.createCell(0).apply {
            setCellValue(title)
            cellStyle = titleStyle
        }
        sheet.addMergedRegion(CellRangeAddress(0, 1, 0, 5))

        val taxRow = sheet.createRow(1)
        taxRow.createCell(6).apply {
            setCellValue("6.0%")
            cellStyle = headerStyle
        }

        taxRow.createCell(7).apply {
            setCellValue("6.0%")
            cellStyle = headerStyle
        }


        // Header
        val headerRow = sheet.createRow(2)
        headerRow.createCell(0).apply {
            setCellValue("No.")
            cellStyle = headerStyle
        }
        headerRow.createCell(1).apply {
            setCellValue("Date")
            cellStyle = headerStyle
        }
        headerRow.createCell(2).apply {
            setCellValue("GST No.")
            cellStyle = headerStyle
        }
        headerRow.createCell(3).apply {
            setCellValue("Name")
            cellStyle = headerStyle
        }
        headerRow.createCell(4).apply {
            setCellValue("Bill No.")
            cellStyle = headerStyle
        }
        headerRow.createCell(5).apply {
            setCellValue("Total")
            cellStyle = headerStyle
        }
        headerRow.createCell(6).apply {
            setCellValue("CGST")
            cellStyle = headerStyle
        }
        headerRow.createCell(7).apply {
            setCellValue("SGST")
            cellStyle = headerStyle
        }
        headerRow.createCell(8).apply {
            setCellValue("GrandTotal")
            cellStyle = headerStyle
        }


        // Body
        bills.forEachIndexed { index, bill ->
            val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(bill.date))

            val row = sheet.createRow(index + 3)
            row.createCell(0).apply {
                setCellValue((index + 1).toDouble())
                cellStyle = descriptiveBodyStyle
            }
            row.createCell(1).apply {
                setCellValue(formattedDate)
                cellStyle = descriptiveBodyStyle
            }
            row.createCell(2).apply {
                setCellValue(bill.traderGst)
                cellStyle = descriptiveBodyStyle
            }
            row.createCell(3).apply {
                setCellValue(bill.traderName)
                cellStyle = descriptiveBodyStyle
            }
            row.createCell(4).apply {
                setCellValue(bill.billNumber)
                cellStyle = descriptiveBodyStyle
            }
            row.createCell(5).apply {
                setCellValue(bill.billTotalAmount)
                cellStyle = monetaryBodyStyle
            }
            row.createCell(6).apply {
                setCellValue(bill.billCGSTAmount)
                cellStyle = monetaryBodyStyle
            }
            row.createCell(7).apply {
                setCellValue(bill.billSGSTAmount)
                cellStyle = monetaryBodyStyle
            }
            row.createCell(8).apply {
                setCellValue(bill.billGrandTotal)
                cellStyle = monetaryBodyStyle
            }
        }


        val fileName = "$title.xlsx"
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDir.exists()) downloadsDir.mkdirs()

        val file = File(downloadsDir, fileName)
        try {
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
                workbook.close()
            }
            return SaveFileResult.Success(result = "Excel file saved: ${file.absolutePath}")
        } catch (e: Exception) {
            Log.d("FileManager","error: ${e.localizedMessage}")
            return SaveFileResult.Failure(result = "Failed to save file: ${e.message}")
        }
    }

    private fun extractDuration(filterWrapper: FilterWrapper): String{
        when(val duration = filterWrapper.itemDuration){
            is ItemDuration.CustomRange -> {
                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                val to = dateFormat.format(Date(duration.to))
                val from = dateFormat.format(Date(duration.from))
                return "$from-$to"
            }
            is ItemDuration.Last3Months -> {
                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                val to = dateFormat.format(Date(Calendar.getInstance().apply { set(Calendar.MONTH, -1) }.timeInMillis))
                val from = dateFormat.format(Date(Calendar.getInstance().apply { set(Calendar.MONTH, -3) }.timeInMillis))
                return "$from-$to"
            }
            is ItemDuration.LastMonth -> {
                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(Calendar.getInstance().apply { set(Calendar.MONTH, -1) }.timeInMillis))
                return formattedDate
            }
            is ItemDuration.ThisMonth -> {
                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(Calendar.getInstance().timeInMillis))
                return formattedDate
            }
            is ItemDuration.Today -> {
                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(Calendar.getInstance().timeInMillis))
                return formattedDate
            }
        }
    }

    private fun extractType(filterWrapper: FilterWrapper): String{
        return when(filterWrapper.itemType){
            ItemType.Both -> "Mix"
            ItemType.Purchase -> "Purchase"
            ItemType.Sales -> "Sales"
        }
    }

    private fun getFont(workbook: Workbook,contentType: ContentType): Font {
        return when(contentType){
            ContentType.Title -> {
                workbook.createFont().apply {
                    bold = true
                    fontHeightInPoints = 22
                    fontName = "Times New Roman"
                }
            }
            ContentType.Header -> {
                workbook.createFont().apply {
                    bold = true
                    fontHeightInPoints = 11
                    fontName = "Times New Roman"
                }
            }
            ContentType.Body -> {
                workbook.createFont().apply {
                    bold = false
                    fontHeightInPoints = 11
                    fontName = "Times New Roman"
                }
            }
        }
    }

    private fun getStyle(workbook: Workbook,font: Font,fieldType: FieldType): XSSFCellStyle {
        return when(fieldType){
            FieldType.Monetary -> {
                (workbook.createCellStyle() as XSSFCellStyle).apply {
                    setFont(font)
                    alignment = HorizontalAlignment.RIGHT
                    verticalAlignment = VerticalAlignment.CENTER

                    borderTop = BorderStyle.THIN
                    borderBottom = BorderStyle.THIN
                    borderLeft = BorderStyle.THIN
                    borderRight = BorderStyle.THIN
                }
            }

            FieldType.Descriptive -> {
                (workbook.createCellStyle() as XSSFCellStyle).apply {
                    setFont(font)
                    alignment = HorizontalAlignment.CENTER
                    verticalAlignment = VerticalAlignment.CENTER

                    borderTop = BorderStyle.THIN
                    borderBottom = BorderStyle.THIN
                    borderLeft = BorderStyle.THIN
                    borderRight = BorderStyle.THIN
                }
            }
        }
    }
}


enum class ContentType{
    Title,
    Header,
    Body
}

enum class FieldType {
    Monetary,
    Descriptive
}