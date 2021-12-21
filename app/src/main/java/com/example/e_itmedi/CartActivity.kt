package com.example.e_itmedi

import android.content.ContentValues.TAG
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_itmedi.Database.DataResponse
import com.example.e_itmedi.Database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_cart.*
import java.util.*

class CartActivity : AppCompatActivity() {


    var totalView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        totalView = findViewById(R.id.total_tv)

        cartToolbar_id.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })
        
        cartdisplay()

    }

    fun cartdisplay() {
        var databaseHelper = DatabaseHelper(this)
        val cursor = databaseHelper!!.CartDsiplayData()
        val Rdata = loadDataCart(cursor)
        val cartAdapter = CartAdapter(this, Rdata)
        Cart_recycler.adapter = cartAdapter
        Cart_recycler.layoutManager = LinearLayoutManager(this)

        var sum = 0
        for (i in Rdata.indices) {
            sum = sum!! + Rdata[i].pp!!.toInt()
        }
        totalView!!.text = "Total $ $sum"
        Log.d(TAG, "CArtDisplay: $sum")

    }

    fun loadDataCart(cursor: Cursor): ArrayList<DataResponse> {

        val dataList: ArrayList<DataResponse> = ArrayList<DataResponse>()
        if (cursor.count == 0) {
            Toast.makeText(this, "No data in database", Toast.LENGTH_LONG).show()
        } else {
            while (cursor.moveToNext()) {
                val dataResponse = DataResponse()
                dataResponse.id = cursor.getString(0)
                dataResponse.tt = cursor.getString(1)
                dataResponse.dd = cursor.getString(2)
                dataResponse.pp = cursor.getString(3)
                dataResponse.cc = cursor.getString(4)
                dataResponse.img = cursor.getString(5)
                dataList.add(dataResponse)
            }
        }
        return dataList
    }

    fun updateTotalPrice(up: Int, productId: String?) {
        var databaseHelper = DatabaseHelper(this)
        val db: SQLiteDatabase = databaseHelper.getWritableDatabase()

        db.execSQL("UPDATE Cart SET quantity =quantity+1," + " price =price+$up WHERE id_=$productId ")
        cartdisplay()
    }

    fun updateDecrementTotalPrice(up: Int, productId: String?) {
        var databaseHelper = DatabaseHelper(this)
        val db: SQLiteDatabase = databaseHelper.getWritableDatabase()

        db.execSQL("UPDATE Cart SET quantity =quantity-1," + " price =price-$up WHERE id_=$productId ")
        cartdisplay()

    }
}