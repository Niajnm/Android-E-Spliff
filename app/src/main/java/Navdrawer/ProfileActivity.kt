package Navdrawer

import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.e_itmedi.Authentication.LogInActivity
import com.example.e_itmedi.Database.DataResponse
import com.example.e_itmedi.Login.Success

import com.example.e_itmedi.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val backbutton= findViewById<ImageView>(R.id.profile_backbutton_id)
        val profileName= findViewById<TextView>(R.id.profileName_id)

        val con = DataResponse()
        val name = con.dataToken.toString()
        Log.d(TAG, "myData :"+name)

        profileName.text = name

        backbutton.setOnClickListener( {

            onBackPressed()
        })

    }
}