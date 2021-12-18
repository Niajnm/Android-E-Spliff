package com.example.e_itmedi

import android.content.ContentValues.TAG
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_itmedi.Database.DataResponse
import com.example.e_itmedi.Database.DatabaseHelper
import java.util.ArrayList
class CartActivity : AppCompatActivity() {

    var recycle: RecyclerView?= null
    var totalView : TextView ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recycle=findViewById(R.id.Cart_recycler)
        totalView= findViewById(R.id.total_tv)
        CArtDisplay()

    }

    fun CArtDisplay() {
        var databaseHelper = DatabaseHelper(this)
        val cursor = databaseHelper!!.CartDsiplayData()
        val Rdata = loadDataCart(cursor)
        val cartAdapter = CartAdapter(this, Rdata)
        recycle!!.adapter = cartAdapter
        recycle!!.layoutManager = LinearLayoutManager(this)

        var sum=0
        for (i in Rdata.indices){
            sum = sum!! + Rdata[i].pp!!.toInt()
        }
        totalView!!.text= "Total $ $sum"
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
        CArtDisplay()
    }

    fun updateDecrementTotalPrice(up: Int, productId: String?) {
        var databaseHelper = DatabaseHelper(this)
        val db: SQLiteDatabase = databaseHelper.getWritableDatabase()

        db.execSQL("UPDATE Cart SET quantity =quantity-1," + " price =price-$up WHERE id_=$productId ")
        CArtDisplay()

    }
}