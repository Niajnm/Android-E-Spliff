package com.example.e_itmedi

import Navdrawer.ProfileActivity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.e_itmedi.Authentication.LogInActivity
import com.example.e_itmedi.Database.InsertDataActivity
import com.google.android.material.navigation.NavigationView
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE

import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN

import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY





class CategoryActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    var toggle: ActionBarDrawerToggle? = null
    var navigationView: NavigationView? = null
    var drawerLayout: DrawerLayout? = null
    private var builder: androidx.appcompat.app.AlertDialog.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbar)

        navigationView = findViewById(R.id.nav_id)
        drawerLayout = findViewById(R.id.drawer_id)
        toolbar = findViewById(R.id.toolbar_id)
       var flowerButton = findViewById<ImageButton>(R.id.imageView_Flower_id)

        title = ""
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle!!.drawerArrowDrawable.color = resources.getColor(R.color.grey)
        drawerLayout!!.addDrawerListener(toggle!!)
        toggle!!.syncState()

        navigationView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Profile_ID ->{

                    var intent = Intent(this,ProfileActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Profile",
                        Toast.LENGTH_SHORT)
                        .show()}



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

        flowerButton.setOnClickListener(View.OnClickListener {

            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        })

    }

    fun LogOut(){

        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage("Do you want to Logout ?")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel()

                navigationView!!.getMenu().findItem(R.id.menuLogout_id).setVisible(false)
                navigationView!!.getMenu().findItem(R.id.Profile_ID).setVisible(false)

                val sharedpreferences :SharedPreferences = getSharedPreferences("MyPREFERENCES",0)
                val editor = sharedpreferences!!.edit()

                  editor.clear().commit()

            })

        builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    fun LogIn(){
                val intent = Intent(this, FirstActivity::class.java)
                startActivity(intent)
                finish()
    }

    override fun onBackPressed() {
        builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder!!.setTitle("Exit !")
        builder!!.setMessage("Do you want to exit ?")
        builder!!.setIcon(R.drawable.ic_baseline_warning_24)
        builder!!.setPositiveButton("Yes", { dialog, which -> finish() })
        builder!!.setNegativeButton("No",{ dialog, which ->
                Toast.makeText(
                    this,
                    "Back to main menu !",
                    Toast.LENGTH_SHORT
                ).show()
            })
        builder!!.setNeutralButton("Minimize",
            DialogInterface.OnClickListener { dialog, which -> onBackPressed() })
        val alertDialog: androidx.appcompat.app.AlertDialog = builder!!.create()
        alertDialog.show()
    }




}