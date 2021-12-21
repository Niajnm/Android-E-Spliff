package com.example.e_itmedi.Database

import android.util.Log

class DataResponse {
    var tt: String? = null
    var id: String? = null
    var pp: String? = null
    var dd: String? = null
    var cc: String? = null
    var img: String? = null
    var dataToken: String? = null

    private val TAG = "DataResponse"

   fun log(){
       Log.d(TAG, "Dataresponde token :"+dataToken)


   }
}