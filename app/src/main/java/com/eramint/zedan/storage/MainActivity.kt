package com.eramint.zedan.storage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkPermission()) {   //permission has been accepted later
            createFolder()
        } else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission()
            }
    }

    private fun createFolder(){
        val folder_main = "ZedanNewFolder"

        val f = File(Environment.getExternalStorageDirectory(), folder_main)
        if (!f.exists()) {
            f.mkdirs()
        }

        val f1 = File(Environment.getExternalStorageDirectory().toString() + "/" + folder_main, "product1")
        if (!f1.exists()) {
            f1.mkdirs()
        }
    }

    /**
     * For Resquest Permisssion
     */

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String?>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestPermissionResult(
                        requestCode,
                        grantResults
                )
        ) // User accept permission
            createFolder()
            //startActivityGetImage()
        else { //user deny Permission
            Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
        }
    }

    //Check if permission accepted later
    //return true has accepted, false otherwise.
    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED)
    }

    //show dialog for request permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        requestPermissions(
                arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                200
        )
    }

    //return true if user Accept permission, false otherwise.
    private fun requestPermissionResult(
            requestResult: Int,
            grantResults: IntArray
    ): Boolean {
        return if (requestResult == 200 &&
                grantResults.isNotEmpty()
        ) {
            (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED)
        } else false
    }
}
