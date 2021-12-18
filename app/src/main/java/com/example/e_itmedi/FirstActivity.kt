package com.example.e_itmedi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.e_itmedi.Authentication.LogInActivity
import com.example.e_itmedi.Authentication.SignUpActivity

class FirstActivity : AppCompatActivity() {
    private var buttonjn: Button? = null
    private var buttonlg: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_first)
        buttonjn = findViewById(R.id.JoinButton_id)
        buttonlg = findViewById(R.id.LoginButton_id)
        buttonjn?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@FirstActivity, SignUpActivity::class.java)
            startActivity(intent)
        })
        buttonlg?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@FirstActivity, LogInActivity::class.java)
            startActivity(intent)
        })
    }
}