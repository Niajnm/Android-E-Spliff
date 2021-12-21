package com.example.e_itmedi

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.e_itmedi.Database.DataResponse
import com.example.e_itmedi.Database.DatabaseHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import java.util.*

class ProductDetailsActivity : AppCompatActivity() {

    private val TAG = "ProductDetailsActivity"
    var title: String? = null
    var price: String? = null
    var type: String? = null
    var cartID: String? = null
    var ImgUrl: String? = null
    var count: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        var databaseHelper = DatabaseHelper(this)
        getActionBar()?.setDisplayHomeAsUpEnabled(true)



        val buttonOrder = findViewById<Button>(R.id.button_Cart_id)
        val intent = intent
        title = intent.getStringExtra("dataTitle")
        price = intent.getStringExtra("dataPrice")
        type = intent.getStringExtra("dataDetails")
        cartID = intent.getStringExtra("dataID")
        ImgUrl = intent.getStringExtra("dataImg")

        detail_Title_id?.setText(title)
        detail_Price_id?.setText("$$price")
        detail_Type_id?.setText(type)

        textView_Total_id!!.text="$$price"
        Picasso.get().load(ImgUrl).into(imageViewProduct_id)

        buttonOrder.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"UUUUU" + cartID)

            val cursor = databaseHelper.CartDsiplayData()
            val Rdata: ArrayList<DataResponse> = loadData(cursor)
            Log.d(TAG, "ALLDATA" + Rdata)
            val cartPrice =count.times(price!!.toInt())
            if (Rdata.isEmpty()) {

                databaseHelper?.insertCartData(title, cartID, cartPrice.toString(), type, count.toString(),ImgUrl)

            }
            else {
                for (i in Rdata.indices) {

                    Log.d(TAG, "check" + Rdata[i].id)

                    if (cartID == Rdata[i].id) {
                        Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
                        // databaseHelper!!.updateCartData(title,cartID,price,type,count.toString())
                        val db: SQLiteDatabase = databaseHelper.getWritableDatabase()
                        var updateprice = count.times(price!!.toInt())
                        db.execSQL("UPDATE Cart SET quantity =quantity+$count, price =price+$updateprice WHERE id_=$cartID ")
                        break
                    }
                    else {

                        val rowid = databaseHelper!!.insertCartData(
                            title,
                            cartID,
                            cartPrice.toString(),
                            type,
                            count.toString(),
                            ImgUrl
                        )
                        if (rowid > -1) {
                            Toast.makeText(this, "$rowid Item Added", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        })

        image_increment_id!!.setOnClickListener(View.OnClickListener {
            count++

            textView_Quantity_id!!.text = count.toString()
            textView_Total_id!!.text= "$"+count.times(price!!.toInt()).toString()

        })
        Image_Decrement_id?.setOnClickListener(View.OnClickListener {
            if (count >1) {
                count--
                textView_Quantity_id!!.text = (count.toString())
                textView_Total_id!!.text= "$"+count.times(price!!.toInt()).toString()
            }
        })

        imageView_Cart!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        })
        imageView_backButton_id!!.setOnClickListener(View.OnClickListener {
           onBackPressed()
        })


    }
    fun loadData(cursor: Cursor): ArrayList<DataResponse> {
        val dataList: ArrayList<DataResponse> = ArrayList<DataResponse>()

        while (cursor.moveToNext()) {
            val dataResponse = DataResponse()
            dataResponse.id = cursor.getString(0)
            dataList.add(dataResponse)
            Log.d(TAG, "loadData" + dataList)

        }
        return dataList
    }
//
//    override fun onSupportNavigateUp(): Boolean {
//                super.onBackPressed()
//        return true
//    }


}