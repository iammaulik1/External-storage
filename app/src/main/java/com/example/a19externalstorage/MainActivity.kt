package com.example.a19externalstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private val filepath = "MyAppStorage"
    internal var myExternalFile:File?=null
    private val isExternalStorageReadOnly:Boolean get(){
        val extStorageState = Environment.getExternalStorageState()
        return if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)){
            true
        }else{
            false
        }
    }

    private val isExternalStorageAvailable:Boolean get(){
        val extStorageState = Environment.getExternalStorageState()
        return if(Environment.MEDIA_MOUNTED.equals(extStorageState)){
            true
        }else{
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fileName = findViewById<EditText>(R.id.editTextFile)
        val fileData = findViewById<EditText>(R.id.editTextData)
        val saveButton = findViewById<Button>(R.id.buttonSave)
        val viewButton = findViewById<Button>(R.id.buttonView)

        saveButton.setOnClickListener ( View.OnClickListener{
            myExternalFile = File(getExternalFilesDir(filepath),fileName.text.toString())
            try {
                val fileOutputStream = FileOutputStream(myExternalFile)
                fileOutputStream.write(fileData.text.toString().toByteArray())
                fileOutputStream.close()
            }catch (e:IOException){
                e.printStackTrace()
            }
            Toast.makeText(applicationContext,"Data Saved",Toast.LENGTH_SHORT).show()
        })
            viewButton.setOnClickListener(View.OnClickListener{
                myExternalFile = File(getExternalFilesDir(filepath),fileName.text.toString())

                val fileName = fileName.text.toString()
                myExternalFile = File(getExternalFilesDir(filepath),fileName)
                if (fileName.toString()!=null&&fileName.toString().trim()!=""){
                    var fileInputStream = FileInputStream(myExternalFile)
                    var inputStreamReader : InputStreamReader = InputStreamReader(fileInputStream)
                    val bufferedReader : BufferedReader = BufferedReader(inputStreamReader)
                    val stringBuilder : StringBuilder = java.lang.StringBuilder()
                    var text:String?=null
                    while ({text = bufferedReader.readLine();text}()!=null){
                        stringBuilder.append(text)
                    }
                    fileInputStream.close()

                    Toast.makeText(applicationContext,stringBuilder.toString(),Toast.LENGTH_SHORT).show()
                }
            })
            if (!isExternalStorageAvailable||isExternalStorageReadOnly){
                saveButton.isEnabled = false
            }
    }
}














