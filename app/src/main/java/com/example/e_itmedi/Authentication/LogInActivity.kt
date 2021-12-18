package com.example.e_itmedi.Authentication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_itmedi.Authentication.APIClient.ATservice
import com.example.e_itmedi.CategoryActivity
import com.example.e_itmedi.Login.LoginResponse
import com.example.e_itmedi.MainActivity
import com.example.e_itmedi.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInActivity : AppCompatActivity() {
    var textViewCreatAc: TextView? = null
    var textLoginmail: EditText? = null
    var textLoginpass: EditText? = null
    var loginbutton: Button? = null
    var email: String? = null
    var pass: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        textViewCreatAc = findViewById(R.id.textView_createAcc_id)
        textLoginmail = findViewById(R.id.editTextEmailAddress_Login)
        textLoginpass = findViewById(R.id.editTextPassword_Login)
        loginbutton = findViewById(R.id.button_Login)
        textViewCreatAc!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        })
        loginbutton!!.setOnClickListener(View.OnClickListener {
            validationCheck()
            //                loginRequest.setEmail(textLoginmail.getText().toString());
//                loginRequest.setPassword(textLoginpass.getText().toString());
            logIn()
        })
    }

    fun validationCheck() {
        email = textLoginmail!!.text.toString()
        pass = textLoginpass!!.text.toString()
        if (email!!.isEmpty()) {
            textLoginmail!!.error = "Enter an email address"
            textLoginmail!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textLoginmail!!.error = "Enter a valid email address"
            textLoginmail!!.requestFocus()
            return
        }

        //checking the validity of the password
        if (pass!!.isEmpty()) {
            textLoginpass!!.error = "Enter a password"
            textLoginpass!!.requestFocus()
            return
        }
        if (pass!!.length < 6) {
            textLoginpass!!.error = "Password Length Must be 6 Digits"
            textLoginpass!!.requestFocus()
            return
        }
    }

    fun logIn() {
        val reqcall = ATservice().logijUser(email, pass)
        reqcall!!.enqueue(object : Callback<LoginResponse?> {




//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                Toast.makeText(this@LogInActivity, "error", Toast.LENGTH_SHORT).show()
//            }

            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) { if (response.isSuccessful) {
//                    Log.d(
//                        TAG, "onResponse: " + response.body()!!
//                            .success.token
//                    )
                Toast.makeText(this@LogInActivity, "Success....1", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LogInActivity, CategoryActivity::class.java))
                finish()
            }

            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

    companion object {
        private const val TAG = "LogInActivity"
    }
}