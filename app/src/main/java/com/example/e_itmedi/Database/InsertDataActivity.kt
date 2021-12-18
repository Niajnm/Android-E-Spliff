package com.example.e_itmedi.Database

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_itmedi.R

class InsertDataActivity : AppCompatActivity() {
    var textTitle: EditText? = null
    var textID: EditText? = null
    var textPrice: EditText? = null
    var textDetails: EditText? = null
    var buttonadd: Button? = null
    var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)

        databaseHelper = DatabaseHelper(this)
        val sqLiteDatabase = databaseHelper!!.writableDatabase

        textID = findViewById(R.id.edittextID_id)
        textTitle = findViewById(R.id.edittextTitle_id)
        textPrice = findViewById(R.id.edittextprice_id)
        textDetails = findViewById(R.id.edittextDtails_id)
        buttonadd = findViewById(R.id.buttonAdd_id)
        val textImgUrl =findViewById<EditText>(R.id.edittextImgUrl_id)

        buttonadd!!.setOnClickListener(View.OnClickListener {
            val title = textTitle?.getText().toString()
           // val id = textID?.getText().toString()
            val price = textPrice?.getText().toString()
            val details = textDetails?.getText().toString()
            val imageUrl= textImgUrl.getText().toString()

            val rowid = databaseHelper!!.insertData(title,price, details,imageUrl)

            if (rowid > -1) {
                Toast.makeText(this@InsertDataActivity, "Serial :$rowid Added", Toast.LENGTH_SHORT)
                    .show()
                textID?.setText("")
                textTitle?.setText("")
                textPrice?.setText("")
                textDetails?.setText("")
                textImgUrl.setText("")

            } else {
                Toast.makeText(this@InsertDataActivity, "Insert Error ", Toast.LENGTH_SHORT).show()
            }
        })
    }
}