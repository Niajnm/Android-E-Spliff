package com.example.e_itmedi.Authentication.SignUp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Success {
    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}