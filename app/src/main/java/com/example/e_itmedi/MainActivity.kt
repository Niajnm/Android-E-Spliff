package com.example.e_itmedi

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_itmedi.Authentication.LogInActivity
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
        title = "Spliff"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        Display()


        navigationView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Profile_ID -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT)
                    .show()
                R.id.menuSetting_id -> Toast.makeText(
                    this,
                    "Settings",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.menuAdmin_id ->
                    startActivity(
                        Intent(this, InsertDataActivity::class.java)

                    )
                R.id.menuLogout_id -> {
                    LogOut()

                    navigationView!!.getMenu().findItem(R.id.menuLogIn_id).isVisible = true
                }
                R.id.menuLogIn_id-> {
                    LogIn()

                    navigationView!!.getMenu().findItem(R.id.menuLogout_id).isVisible = true
                }

            }
            true
        })
    }

    override fun onResume() {
        super.onResume()
        Display()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
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
    fun LogOut(){

        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage("Write your message here.")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel()

                navigationView!!.getMenu().findItem(R.id.menuLogout_id).setVisible(false)

//                val intent = Intent(this, LogInActivity::class.java)
//                startActivity(intent)
//                finish()

            })

        builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    fun LogIn(){
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
        finish()
    }

}