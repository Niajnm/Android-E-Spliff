package com.example.e_itmedi.Authentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_itmedi.Authentication.APIClient.ATservice
import com.example.e_itmedi.CategoryActivity
import com.example.e_itmedi.Login.LoginResponse
import com.example.e_itmedi.R
import kotlinx.android.synthetic.main.activity_log_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInActivity : AppCompatActivity() {
    var email: String? = null
    var pass: String? = null
    lateinit var name: String
    var sharedpreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        textView_createAcc_id!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        })

        button_Login!!.setOnClickListener {
            validationCheck()
            //                loginRequest.setEmail(textLoginmail.getText().toString());
//                loginRequest.setPassword(textLoginpass.getText().toString());
            logIn()
        }
    }

    fun validationCheck() {
        email = editTextEmailAddress_Login!!.text.toString()
        pass = editTextPassword_Login!!.text.toString()
        if (email!!.isEmpty()) {
            editTextEmailAddress_Login!!.error = "Enter an email address"
            editTextEmailAddress_Login!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmailAddress_Login!!.error = "Enter a valid email address"
            editTextEmailAddress_Login!!.requestFocus()
            return
        }
        //checking the validity of the password
        if (pass!!.isEmpty()) {
            editTextPassword_Login!!.error = "Enter a password"
            editTextPassword_Login!!.requestFocus()
            return
        }
        if (pass!!.length < 4) {
            editTextPassword_Login!!.error = "Password Length Must be 4 Digits"
            editTextPassword_Login!!.requestFocus()
            return
        }
    }

    fun logIn() {
        val reqcall = ATservice().logijUser(email, pass)
        reqcall!!.enqueue(object : Callback<LoginResponse?> {

            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    val code = response.code()
                    val tk = response.body()!!.success?.token.toString()

                    sharedpreferences = getSharedPreferences("MyPREFERENCES", 0)
                    val editor = sharedpreferences!!.edit()
                    editor.putString("UserToken", tk)
                    editor.commit()

                    Toast.makeText(this@LogInActivity, "Success....1", Toast.LENGTH_SHORT).show()
                    if (code == 200) {
                        startActivity(Intent(this@LogInActivity, CategoryActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {

                Toast.makeText(this@LogInActivity, "error" + t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()

                Log.d("tag", t.localizedMessage)
            }
        })
    }

    companion object {
        private const val TAG = "LogInActivity"
    }
}