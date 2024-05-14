package com.example.firstassignment
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserManager.db"
        private const val TABLE_USER = "user"
        private const val COLUMN_USER_NAME = "user_name"
        private const val COLUMN_USER_PASSWORD = "user_password"
        private const val COLUMN_USER_MOBILE = "user_mobile"
        private const val COLUMN_USER_EMAIL = "user_email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USER_MOBILE + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT" + ")")
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun addUser(username: String, password: String, mobile: String, email: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, username)
        values.put(COLUMN_USER_PASSWORD, password)
        values.put(COLUMN_USER_MOBILE, mobile)
        values.put(COLUMN_USER_EMAIL, email)
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun checkUser(username: String, password: String): Int {
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_NAME = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(TABLE_USER, null, selection, selectionArgs, null, null, null)
        if (cursor.moveToFirst()) {
            val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD))
            cursor.close()
            db.close()
            return if (storedPassword == password) 1 else 2
        } else {
            cursor.close()
            db.close()
            return 0
        }
    }
}
