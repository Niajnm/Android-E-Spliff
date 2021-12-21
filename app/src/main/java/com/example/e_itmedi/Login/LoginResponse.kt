package com.example.e_itmedi.Login


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("success")
    @Expose
    var success: Success? = null
}