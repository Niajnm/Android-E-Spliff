package com.example.e_itmedi.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHelper(var context: Context?) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, VERSION_NUM
) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
           db.execSQL(CREATE_TABLE)
            db.execSQL(CART_TABLE)
            Toast.makeText(context, "oncreate", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "EXCEPTION$e", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun insertData(
        title: String?,
     //   id: String?,
        price: String?,
        details: String?,
        imageUrl: String
    ): Long {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, title)
      //  contentValues.put(ID, id)
        contentValues.put(PRICE, price)
        contentValues.put(DETAILS, details)
        contentValues.put(IMG, imageUrl)
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
    }
    fun insertCartData(
        title: String?,
        id: String?,
        price: String?,
        details: String?,
        quantity: String?,
        ImgUrl: String?
    ): Long {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, title)
        contentValues.put(ID, id)
        contentValues.put(PRICE, price)
        contentValues.put(DETAILS, details)
        contentValues.put(QAUNTITY, quantity)
        contentValues.put(IMG, ImgUrl)
        return sqLiteDatabase.insert(CART_TABLE_NAME, null, contentValues)
    }

//    fun updateCartData(
//        title: String?,
//       // id: String?,
//        price: String?,
//        details: String?,
//        quantity: String?
//    ): Boolean {
//        val sqLiteDatabase = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(TITLE, title)
//        contentValues.put(ID, id)
//        contentValues.put(PRICE, price)
//        contentValues.put(DETAILS, details)
//        contentValues.put(QAUNTITY, quantity)
//
//        sqLiteDatabase.update(TABLE_NAME, contentValues, "id_=?", arrayOf<String>(id!!)).toLong()
//
//        return true
//
//    }




    fun dsiplayData(): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun CartDsiplayData(): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery("SELECT * FROM $CART_TABLE_NAME", null)

    }

    companion object {
        private const val DATABASE_NAME = "Product_DB"
        private const val TABLE_NAME = "products"
        private const val CART_TABLE_NAME = "Cart"
        private const val TITLE = "Title"
        private const val PRICE = "Price"
        private const val DETAILS = "Details"
        private const val QAUNTITY = "quantity"
        private const val IMG = "imageUrl"
        private const val ID = "id_"
        private const val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        private const val VERSION_NUM = 2
        private const val CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," + TITLE + " VARCHAR(50)," + DETAILS + " VARCHAR(150)," + PRICE + " INTEGER, $IMG VARCHAR(500))"
     private const val CART_TABLE ="CREATE TABLE " + CART_TABLE_NAME + "(" + ID + " VARCHAR(100)  PRIMARY KEY ," + TITLE + " VARCHAR(50)," + DETAILS + " VARCHAR(150)," + PRICE + " INTEGER,"+ QAUNTITY +" INTEGER, $IMG VARCHAR(500))"


    }
}