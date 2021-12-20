package com.example.e_itmedi.Authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_itmedi.Authentication.APIClient.ATservice
import com.example.e_itmedi.Authentication.LogInActivity
import com.example.e_itmedi.Authentication.SignUp.ResponseData
import com.example.e_itmedi.Authentication.SignUpActivity
import com.example.e_itmedi.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    var sname: EditText? = null
    var smail: EditText? = null
    var spass: EditText? = null
    var sconfirmPass: EditText? = null
    var buttonSign: Button? = null
    var memberLog: TextView? = null
    var email: String? = null
    var password: String? = null
    var cpassword: String? = null
    var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        memberLog = findViewById(R.id.textView_loginMember)
        sname = findViewById(R.id.editTextSign_name)
        smail = findViewById(R.id.editTextSign_EmailAddress)
        spass = findViewById(R.id.editTextText_SignPass_id)
        sconfirmPass = findViewById(R.id.editTextText_SignPassConfirm_id)
        buttonSign = findViewById(R.id.buttonSign_id)
        memberLog!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
            startActivity(intent)
            finish()
        })
        buttonSign!!.setOnClickListener(View.OnClickListener {
            validationCheck()
            //                RegisterRequest registerRequest = new RegisterRequest();
//                registerRequest.setEmail(smail.getText().toString());
//                registerRequest.setName(sname.getText().toString());
//                registerRequest.setPassword(spass.getText().toString());
//                registerRequest.setC_password(sconfirmPass.getText().toString());
            signUp()
        })
    }

    fun validationCheck() {
        name = sname!!.text.toString()
        email = smail!!.text.toString()
        password = spass!!.text.toString()
        cpassword = sconfirmPass!!.text.toString()
        if (name!!.isEmpty()) {
            sname!!.error = "Enter a name"
            sname!!.requestFocus()
            return
        }
        if (email!!.isEmpty()) {
            smail!!.error = "Enter an email address"
            smail!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            smail!!.error = "Enter a valid email address"
            smail!!.requestFocus()
            return
        }

        //checking the validity of the password
        if (password!!.isEmpty()) {
            spass!!.error = "Enter a password"
            spass!!.requestFocus()
            return
        }
        if (password!!.length < 6) {
            spass!!.error = "Password Length Must be 6 Digits"
            spass!!.requestFocus()
            return
        }
        if (cpassword != password) {
            sconfirmPass!!.error = "Password mismatched !"
            sconfirmPass!!.requestFocus()
            return
        }
    }

    fun signUp() {
        val reqcall = ATservice().registeruser(email, password, cpassword, name)
        reqcall!!.enqueue(object : Callback<ResponseData?> {


            override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                if (response.isSuccessful) {


                    val tk=response.body()?.success?.token
                    val name=response.body()?.success?.name
//                    Log.d(
//                        TAG, "onResponse: " + response.body()!!.success.name
//                    )
//                    Log.d(
//                        TAG, "onResponse: " + response.body()!!
//                            .success.token
//                    )
                    Toast.makeText(this@SignUpActivity, "Success....1", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "error " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}