package com.example.e_itmedi

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), CellClickListener, DialogListener {
    var tabLayout: TabLayout? = null


    var toggle: ActionBarDrawerToggle? = null
    var navigationView: NavigationView? = null
    var drawerLayout: DrawerLayout? = null
    var databaseHelper = DatabaseHelper(this)
    var customAdapter: CustomAdapter? = null
    var Rdata = ArrayList<DataResponse>()
    var newList = ArrayList<DataResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById(R.id.nav_id)
        drawerLayout = findViewById(R.id.drawer_id)

        button_nyBag.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        })

        setSupportActionBar(toolbar_id)
        title = "Spliff"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toolbar_id!!.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })
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
                R.id.menuLogIn_id -> {
                    LogIn()
                    navigationView!!.getMenu().findItem(R.id.menuLogout_id).isVisible = true
                }
            }
            true
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.webmenu_layout, menu)

        val item = menu?.findItem(R.id.menuSearch_id);
        val searchView = item?.actionView as SearchView

        val filterList = ArrayList<DataResponse>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //newList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())

                if (searchText.isNotEmpty()) {
                    filterList.clear()
                    newList.forEach {
                        if (it.tt!!.toLowerCase().contains(searchText)) {
                            filterList.add(it)

                        }
                    }

                    newList.clear()
                    newList.addAll(filterList)
                    recycler?.adapter!!.notifyDataSetChanged()
                } else {

                    newList.clear()
                    newList.addAll(Rdata)
                    recycler?.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        Display()
    }

    fun Display() {
        val cursor = databaseHelper!!.dsiplayData()
        Rdata = loadData(cursor)
        //newList = Rdata
        newList.clear()
        Rdata.forEach {
            var dataResponse = DataResponse()
            dataResponse = it
            newList.add(dataResponse)
        }
        //newList.addAll(Rdata)
        customAdapter = CustomAdapter(
            this@MainActivity, newList, this, this
        )
        recycler!!.adapter = customAdapter
        recycler!!.layoutManager = LinearLayoutManager(this)

    }

    fun loadData(cursor: Cursor): ArrayList<DataResponse> {

        val dataList = ArrayList<DataResponse>()
        if (cursor.count == 0) {

            Toast.makeText(this@MainActivity, "No data in database", Toast.LENGTH_LONG).show()

        } else {
            while (cursor.moveToNext()) {
                val dataResponse = DataResponse()
                dataResponse.id = cursor.getString(0)
                dataResponse.tt = cursor.getString(1)
                dataResponse.dd = cursor.getString(2)
                dataResponse.pp = cursor.getString(3)
                dataResponse.img = cursor.getString(4)
                dataList.add(dataResponse)
            }
        }

        return dataList
    }

    override fun onCellClickListener(
        Tdata: String?,
        Pdata: String?,
        Ddata: String?,
        IDdata: String?,
        Imgdata: String?
    ) {
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
        val cartRdata: ArrayList<DataResponse> = loadData(cursor)
        Log.d(TAG, "ALLDATA" + cartRdata)
        val cartPrice = Countdata.times(Pdata!!.toInt())
        if (cartRdata.isEmpty()) {

            databaseHelper?.insertCartData(
                Tdata,
                IDdata,
                cartPrice.toString(),
                Ddata,
                Countdata.toString(),
                Imgdata
            )

        } else {
            for (i in cartRdata.indices) {

                Log.d(TAG, "check" + cartRdata[i].id)

                if (IDdata == cartRdata[i].id) {
                    Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
                    // databaseHelper!!.updateCartData(title,cartID,price,type,count.toString())
                    val db: SQLiteDatabase = databaseHelper.getWritableDatabase()
                    var updateprice = Countdata.times(Pdata!!.toInt())
                    db.execSQL(
                        "UPDATE Cart SET quantity =quantity+$Countdata," +
                                " price =price+$updateprice WHERE id_=$IDdata "
                    )
                    break
                } else {

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

    fun LogOut() {

        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage("Write your message here.")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()

                navigationView!!.getMenu().findItem(R.id.menuLogout_id).setVisible(false)

            })

        builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    fun LogIn() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
        finish()
    }


}