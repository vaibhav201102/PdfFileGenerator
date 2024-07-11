package com.vaibhavJoshi.pdffilegenerator

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import java.io.File
import com.vaibhavJoshi.pdffilegenerator.data.User
import com.vaibhavJoshi.pdffilegenerator.databinding.ActivityMainBinding
import com.vaibhavJoshi.pdffilegenerator.pdfService.FileHandler
import com.vaibhavJoshi.pdffilegenerator.pdfService.PdfService

class MainActivity : AppCompatActivity() {

    private val userData = listOf(

        User("1", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("2", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("3", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("4", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("5", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("6", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("7", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("8", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("9", "06/07/24","25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("10","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("11","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("12","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("13","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("14","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("15","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("16","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("17","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("18","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("19","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("20","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("21","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("22","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("23","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("24","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("25","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("26","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("27","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("28","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("29","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("30","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("31","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("32","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),
        User("33","06/07/24", "25.00 LTR", "₹50.00", "₹1250.00","Vaibhav Joshi","25.00 LTR","₹40.00","₹1000.00","Vaibhav Joshi","₹2250.00"),

        )

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissionRequired()

        binding.apply {
            exportButton.setOnClickListener {
                createPdf()
            }
        }

    }

    // Method to Check the Permission Required
    private fun checkPermissionRequired() : Boolean{
        this.let {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (hasPermission(it as Context, permission_s)) {
                    //startFragment()
                    return true
                } else {
                    permReqLauncher.launch(permission_s)
                    return false
                }
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
                if (hasPermission(it as Context, permission_r)) {
                    //startFragment()
                    return true
                } else {
                    permReqLauncher.launch(permission_r)
                    return false
                }
            }
        }
        return false
    }

    // Companion Object To Declare Permissions According to The Version Based
    companion object {


        var permission_s                    =               arrayOf(

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

        )

        var permission_r                    =               arrayOf(

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

        )

    }

    // Variable to provide Response That Permission is Granted or Not
    val permReqLauncher =  registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val granted = permissions.entries.all {it.value} // Checks the permission is granted or not

        if (granted) {
            // navigate to respective screen
            //startFragment()
        } else {
            // show custom alert
            //Previously Permission Request was cancelled with 'Dont Ask Again',
            // Redirect to Settings after showing Information about why you need the permission
        }
    }

    // Method Checks That Permission is Provided or not
    private fun hasPermission(context: Context, permissions: Array<String>): Boolean = permissions.all { ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }

    private fun createPdf() {

        val onError: (Exception) -> Unit = { toastErrorMessage(it.message.toString()) }
        val onFinish: (File) -> Unit = { openFile(it) }
//        val paragraphList = listOf(
//            getString(R.string.paragraph1), getString(R.string.paragraph2)
//        )
//        val headerList = listOf("No", "First Name", " Middle Name", " Last Name")
        val headerList = listOf("No","Date","Mor. Qty","Rate","Mor. Total","Username","Eve. Qty","Rate","Eve. Total","Username","Sub Total")
        val pdfService = PdfService()
        pdfService.createUserTable(
            data = userData,
            paragraphList = emptyList<String>(),
            paragraphHeaderList = headerList,
            onFinish = onFinish,
            onError = onError
        )

/*        if (checkPermissionRequired()) {
            val onError: (Exception) -> Unit = { toastErrorMessage(it.message.toString()) }
            val onFinish: (File) -> Unit = { openFile(it) }
//        val paragraphList = listOf(
//            getString(R.string.paragraph1), getString(R.string.paragraph2)
//        )
//        val headerList = listOf("No", "First Name", " Middle Name", " Last Name")
            val headerList = listOf("No","Date","Mor. Qty","Rate","Mor. Total","Username","Eve. Qty","Rate","Eve. Total","Username","Sub Total")
            val pdfService = PdfService()
            pdfService.createUserTable(
                data = userData,
                paragraphList = emptyList<String>(),
                paragraphHeaderList = headerList,
                onFinish = onFinish,
                onError = onError
            )
        }
        else{
            checkPermissionRequired()
        }*/
    }

    private fun openFile(file: File) {
        val path = FileHandler().getPathFromUri(this@MainActivity, file.toUri())
        val pdfFile = File(path.toString())
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
        val pdfIntent = Intent(Intent.ACTION_VIEW)
        pdfIntent.setDataAndType(pdfFile.toUri(), "application/pdf")
        pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        try {
            startActivity(pdfIntent)
        } catch (e: ActivityNotFoundException) {
            toastErrorMessage("Can't read pdf file")
        }
    }

    private fun toastErrorMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}