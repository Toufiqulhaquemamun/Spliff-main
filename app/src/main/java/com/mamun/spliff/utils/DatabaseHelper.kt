package com.mamun.spliff.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mamun.spliff.model.Product
import java.util.*

class DatabaseHelper(private val mContext: Context?) : SQLiteOpenHelper(
    mContext, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        private const val TAG = "DatabaseHandler"
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "PRODUCT_ITEM"

        //Table Name
        private const val TABLE_PRODUCT = "productTable"

        //productTable Table Column Name
        //==========================
        private const val COLUMN_PRODUCT_ID = "product_id"
        private const val COLUMN_PRODUCT_NO = "product_no"
        private const val COLUMN_PRODUCT_NAME = "product_name"
        private const val COLUMN_PRODUCT_DESC = "product_desc"
        private const val COLUMN_PRODUCT_GRP = "product_grp"
        private const val COLUMN_PRODUCT_PRICE = "product_price"
        private const val COLUMN_PRODUCT_FAV = "product_fav"
        private const val COLUMN_PRODUCT_IMG = "product_img"
        private const val CREATE_TABLE = "CREATE TABLE "
        private const val DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS "
        private const val PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT,"
        private const val TEXT_WITH_COMMA = " TEXT,"
        private const val TEXT_WITHOUT_COMMA = " TEXT"
        private const val CREATE_TABLE_PRODUCT = (CREATE_TABLE + TABLE_PRODUCT + "("
                + COLUMN_PRODUCT_NO + " INTEGER,"
                + COLUMN_PRODUCT_ID + " INTEGER,"
                + COLUMN_PRODUCT_NAME + TEXT_WITH_COMMA
                + COLUMN_PRODUCT_DESC + TEXT_WITH_COMMA
                + COLUMN_PRODUCT_GRP + TEXT_WITH_COMMA
                + COLUMN_PRODUCT_PRICE + " INTEGER,"
                + COLUMN_PRODUCT_FAV + " INTEGER,"
                + COLUMN_PRODUCT_IMG + TEXT_WITHOUT_COMMA + ")")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.execSQL(CREATE_TABLE_PRODUCT)
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: ", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(DROP_TABLE_IF_EXISTS + TABLE_PRODUCT)
        onCreate(db)
    }


    fun addProduct(productList: List<Product>): Boolean {
        val db = this.writableDatabase
        db.beginTransaction()
        var index = 1
        var result: Boolean
        try {
            val values = ContentValues()
            for (q in productList) {
                values.put(COLUMN_PRODUCT_NO, index++)
                values.put(COLUMN_PRODUCT_ID, q.prodNo)
                values.put(COLUMN_PRODUCT_NAME, q.name)
                values.put(COLUMN_PRODUCT_DESC, q.desc)
                values.put(COLUMN_PRODUCT_GRP, q.group)
                values.put(COLUMN_PRODUCT_PRICE, q.price)
                values.put(COLUMN_PRODUCT_FAV, 0)
                values.put(COLUMN_PRODUCT_IMG, q.img)
                db.insert(TABLE_PRODUCT, null, values)
                result = true
            }
            db.setTransactionSuccessful()
            result = true
        } catch (e: SQLiteAbortException) {
            e.printStackTrace()
            result = false
        } finally {
            db.endTransaction()
        }
        return result
    }

    fun queryFavProd(): List<Product> {
        val productsList: MutableList<Product> = ArrayList()
        val db = this.readableDatabase
        val value = 1
        val query =
            "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_PRODUCT_FAV + " = '" + value + "'"
        try {
            @SuppressLint("Recycle") val cursor = db.rawQuery(query, null)
            cursor.count
            if (cursor.moveToFirst()) {
                do {
                    var productitem = Product()
                    productitem.prodNo = cursor.getString(1)
                    productitem.name = cursor.getString(2)
                    productitem.desc = cursor.getString(3)
                    productitem.group = cursor.getString(4)
                    productitem.price = cursor.getString(5)
                    productitem.fav = cursor.getString(6)
                    productitem.img = cursor.getString(7)
                    productsList.add(productitem)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return productsList
    }


    fun getAllProductGroup(): List<String>? {
        val prodGroup: MutableList<String> = ArrayList()
        val selectQuery =
            "SELECT DISTINCT " + COLUMN_PRODUCT_GRP + " FROM " + TABLE_PRODUCT + " ORDER BY " + COLUMN_PRODUCT_GRP
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        cursor.moveToFirst()
        for (i in 0 until cursor.count) {
            prodGroup.add(cursor.getString(0))
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return prodGroup
    }

    fun updateMarked(productNO: String) {
        val `val` = 1
        val db = this.writableDatabase
        val query = "UPDATE " + TABLE_PRODUCT + " SET " + COLUMN_PRODUCT_FAV +
                " = '" + `val` + "' WHERE " + COLUMN_PRODUCT_NO + "= '" + productNO + "'"
        db.execSQL(query)
        db.close()
    }

    fun updateUnMarked(productNO: Int) {
        val `val` = 0
        val db = this.writableDatabase
        val query = "UPDATE " + TABLE_PRODUCT + " SET " + COLUMN_PRODUCT_FAV +
                " = '" + `val` + "' WHERE " + COLUMN_PRODUCT_NO + "= '" + productNO + "'"
        db.execSQL(query)
        db.close()
    }

    fun getProductInfoByGroup(prodGroup: String): List<Product>? {
        val productInfoList: MutableList<Product> = ArrayList<Product>()
        val selectQuery =
            "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_PRODUCT_GRP + " = '" + prodGroup + "' ORDER BY " + COLUMN_PRODUCT_ID
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        cursor.moveToFirst()
        for (i in 0 until cursor.count) {
            val productitem = Product()
            productitem.prodNo = cursor.getString(1)
            productitem.name = cursor.getString(2)
            productitem.desc = cursor.getString(3)
            productitem.group = cursor.getString(4)
            productitem.price = cursor.getString(5)
            productitem.fav = cursor.getString(6)
            productitem.img = cursor.getString(7)
            productInfoList.add(productitem)
            cursor.moveToNext()
        }
        // make sure to close the cursor
        cursor.close()
        db.close()
        return productInfoList
    }

}