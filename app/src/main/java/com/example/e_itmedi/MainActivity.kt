package com.example.e_itmedi

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_itmedi.Database.DataResponse
import com.example.e_itmedi.Database.DatabaseHelper
import com.example.e_itmedi.Database.InsertDataActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity(), CellClickListener,DialogListener {
    var tabLayout: TabLayout? = null
    var recyclerView: RecyclerView? = null
    var buttonMybag: Button? = null
    var toolbar: Toolbar? = null
    var toggle: ActionBarDrawerToggle? = null
    var navigationView: NavigationView? = null
    var drawerLayout: DrawerLayout? = null
    var databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar_id)
        recyclerView = findViewById(R.id.recycler)
        buttonMybag = findViewById(R.id.button_nyBag)
        navigationView = findViewById(R.id.nav_id)
        drawerLayout = findViewById(R.id.drawer_id)
        buttonMybag!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        })

        setSupportActionBar(toolbar)
        title = ""
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle!!.drawerArrowDrawable.color = resources.getColor(R.color.grey)
        drawerLayout!!.addDrawerListener(toggle!!)
        toggle!!.syncState()

        navigationView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Profile_ID -> Toast.makeText(this@MainActivity, "Profile", Toast.LENGTH_SHORT)
                    .show()
                R.id.menuSetting_id -> Toast.makeText(
                    this@MainActivity,
                    "Settings",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.menuAdmin_id -> startActivity(
                    Intent(
                        this@MainActivity,
                        InsertDataActivity::class.java
                    )
                )
            }
            true
        })
        Display()

    }

    override fun onResume() {
        super.onResume()
        Display()
    }

    fun Display() {

        databaseHelper = DatabaseHelper(this)
        val sqLiteDatabase = databaseHelper!!.writableDatabase
        val cursor = databaseHelper!!.dsiplayData()
        val Rdata = loadData(cursor)
        val customAdapter = CustomAdapter(
            this@MainActivity, Rdata,this,this)
        recyclerView!!.adapter = customAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
    }
    fun loadData(cursor: Cursor): ArrayList<DataResponse> {

        val dataList: ArrayList<DataResponse> = ArrayList<DataResponse>()
        if (cursor.count == 0) {

            Toast.makeText(this@MainActivity, "No data in database", Toast.LENGTH_LONG).show()
        } else {
            while (cursor.moveToNext()) {
                val dataResponse = DataResponse()
                dataResponse.id = cursor.getString(0)
                dataResponse.tt = cursor.getString(1)
                dataResponse.dd = cursor.getString(2)
                dataResponse.pp = cursor.getString(3)
                dataResponse.img= cursor.getString(4)
                dataList.add(dataResponse)
            }
        }
        return dataList

    }

    override  fun onCellClickListener(Tdata: String?, Pdata: String?, Ddata: String?, IDdata: String?, Imgdata: String?) {
        val intent = Intent(this@MainActivity, ProductDetailsActivity::class.java)
        intent.putExtra("dataTitle", Tdata)
        intent.putExtra("dataPrice", Pdata)
        intent.putExtra("dataDetails", Ddata)
        intent.putExtra("dataID", IDdata)
        intent.putExtra("dataImg", Imgdata)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onDialogListener(
        Tdata: String?,
        Pdata: String?,
        Ddata: String?,
        IDdata: String?,
        Countdata: Int,
        Imgdata: String?
    ) {

        val cursor = databaseHelper.CartDsiplayData()
        val Rdata: ArrayList<DataResponse> = loadData(cursor)
        Log.d(TAG, "ALLDATA" + Rdata)
        val cartPrice =Countdata.times(Pdata!!.toInt())
        if (Rdata.isEmpty()) {

            databaseHelper?.insertCartData(Tdata, IDdata, cartPrice.toString(), Ddata, Countdata.toString(),Imgdata)

        }
        else {
            for (i in Rdata.indices) {

                Log.d(TAG, "check" + Rdata[i].id)

                if (IDdata == Rdata[i].id) {
                    Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
                    // databaseHelper!!.updateCartData(title,cartID,price,type,count.toString())
                    val db: SQLiteDatabase = databaseHelper.getWritableDatabase()
                    var updateprice = Countdata.times(Pdata!!.toInt())
                    db.execSQL("UPDATE Cart SET quantity =quantity+$Countdata," +
                            " price =price+$updateprice WHERE id_=$IDdata ")
                    break
                }
                else {

                    val rowid = databaseHelper!!.insertCartData(
                        Tdata,
                        IDdata,
                        cartPrice.toString(),
                        Ddata,
                        Countdata.toString(),
                        Imgdata

                    )
                    if (rowid > -1) {
                        Toast.makeText(this, "$rowid Item Added", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }


}