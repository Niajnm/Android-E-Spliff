package com.example.e_itmedi.Authentication.SignUp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseData {
    @SerializedName("success")
    @Expose
    var success: Success? = null
}