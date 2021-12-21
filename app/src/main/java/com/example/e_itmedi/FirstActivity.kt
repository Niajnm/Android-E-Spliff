package com.example.e_itmedi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.e_itmedi.Authentication.LogInActivity
import com.example.e_itmedi.Authentication.SignUpActivity
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_first)

      //  setTheme(R.style.splashScreenTheme)



        val sharedpreferences :SharedPreferences  = getSharedPreferences("MyPREFERENCES",0)
            val token = sharedpreferences.getString("UserToken",null)
        if (token != null){

            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }

        JoinButton_id.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@FirstActivity, SignUpActivity::class.java)
            startActivity(intent)
        })
        LoginButton_id.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@FirstActivity, LogInActivity::class.java)
            startActivity(intent)
        })
    }
}