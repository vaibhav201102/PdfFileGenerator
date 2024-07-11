@file:Suppress("DEPRECATION")

package com.vaibhavJoshi.pdffilegenerator.pdfService

import android.os.Environment
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

/**
 * Created by Annas Surdyanto on 05/02/22.
 *
 */
class PdfService {

    private val TITLE_FONT = Font(Font.FontFamily.TIMES_ROMAN, 16f, Font.BOLD)
    private val BODY_FONT = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.NORMAL)
    private lateinit var pdf: PdfWriter

    private fun createFile(pdfName : String): File {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        var file = File(path, "$pdfName.pdf")

        // Check if the file already exists
        if (file.exists()) {
            var version = 1
            // Append (1), (2), etc., to the file name until a unique name is found
            while (file.exists()) {
                file = File(path, "$pdfName (${version++}).pdf")
            }
        }

        // Create the file
        if (!file.exists()) {
            file.createNewFile()
        }

        return file
    }

    private fun createDocument(): Document {
        //Create Document
        val document = Document()
        document.setMargins(24f, 24f, 32f, 32f)
        document.pageSize = PageSize.A4
        return document
    }

    private fun setupPdfWriter(document: Document, file: File) {
        pdf = PdfWriter.getInstance(document, FileOutputStream(file))
        pdf.setFullCompression()
        //Open the document
        document.open()
    }

    private fun createTable(column: Int, columnWidth: FloatArray): PdfPTable {
        val table = PdfPTable(column)
        table.widthPercentage = 100f
        table.setWidths(columnWidth)
        table.headerRows = 1
        table.defaultCell.verticalAlignment = Element.ALIGN_CENTER
        table.defaultCell.horizontalAlignment = Element.ALIGN_CENTER
        return table
    }

    private fun createCell(content: String): PdfPCell {
        val cell = PdfPCell(Phrase(content))
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        //setup padding
        cell.setPadding(4f)
        cell.isUseAscender = true
        cell.paddingLeft = 2f
        cell.paddingRight = 2f
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        return cell
    }

    private fun addLineSpace(document: Document, number: Int) {
        for (i in 0 until number) {
            document.add(Paragraph(" "))
        }
    }

    private fun createParagraph(content: String): Paragraph{
        val paragraph = Paragraph(content, BODY_FONT)
        paragraph.firstLineIndent = 25f
        paragraph.alignment = Element.ALIGN_JUSTIFIED
        return paragraph
    }

    fun <T : Any> createUserTable(
        data: List<T>,
        paragraphList: List<String>,
        paragraphHeaderList: List<String>,
        onFinish: (file: File) -> Unit,
        onError: (Exception) -> Unit
    ) {
        // Check if data list is empty
        if (data.isEmpty()) {
            onError(IllegalArgumentException("Data list is empty"))
            return
        }

        // Get the class type of T
//        val clazz = data.first()::class.java
        val clazz: KClass<out Any> = data.first()::class

        //Define the document
        val file = createFile("User Data")
        val document = createDocument()

        //Setup PDF Writer
        setupPdfWriter(document, file)

        //Add Title
//        document.add(Paragraph("Paragraph Title", TITLE_FONT))
        //Add Empty Line as necessary
//        addLineSpace(document, 1)

        //Add paragraph
/*        paragraphList.forEach {content->
            val paragraph = createParagraph(content)
            document.add(paragraph)
        }*/
//        addLineSpace(document, 1)
        //Add Empty Line as necessary

        //Add table title
//        document.add(Paragraph("User Data", TITLE_FONT))
//        addLineSpace(document, 1)

        //Define Table
        val columnWidth: FloatArray = FloatArray(paragraphHeaderList.size) { 1f }
        paragraphHeaderList.forEachIndexed { index, _ ->
            columnWidth[index] = if (index == 0) 0.5f else 1f
        }

        val table = createTable(paragraphHeaderList.size, columnWidth)
        //Table header (first row)
        val tableHeaderContent = paragraphHeaderList
        //write table header into table
        tableHeaderContent.forEach {
            //define a cell
            val cell = createCell(it)
            //add our cell into our table
            table.addCell(cell)
        }

        // Write user data into table
//        data.forEach { user ->
//            // Iterate through each property in User and add it to the table dynamically
//            user::class.members
//                .filterIsInstance<KProperty1<User, *>>()
//                .forEach { prop ->
//                    println(prop.get(user))
//                    val value = prop.get(user).toString()  // Get the property value as String
//                    val cell = createCell(value)
//                    table.addCell(cell)
//                }
//        }

        // Write user data into table dynamically
        data.forEach { item ->
            clazz.declaredMemberProperties.forEach { prop ->
                val value = prop.call(item)?.toString() ?: ""
                val cell = createCell(value)
                table.addCell(cell)
            }
        }

        document.add(table)
        document.close()

        try {
            pdf.close()
        } catch (ex: Exception) {
            onError(ex)
        } finally {
            onFinish(file)
        }
    }

}