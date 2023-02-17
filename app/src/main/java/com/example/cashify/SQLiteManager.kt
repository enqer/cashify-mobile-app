package com.example.cashify


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteManager(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cashifyDB"
        private const val TABLE_NAME = "table_cashify"
        private const val DATABASE_VERSION = 1
        private const val ID= "id"
        private const val NAME= "name"
        private const val BALANCE = "balance"
        private const val DATE = "date"
        private const val CONTENT = "content"
        private const val AVATAR = "avatar"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTableCashify = ("CREATE TABLE "+ TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + BALANCE + " DOUBLE," + DATE + " TEXT,"
                + CONTENT + " TEXT," + AVATAR + " TEXT" + ")")
        db?.execSQL(createTableCashify)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
        // check it later
    }
    fun insertPerson(person: Person): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, person.id)
        contentValues.put(NAME,person.name)
        contentValues.put(BALANCE, person.balance)
        contentValues.put(DATE, person.date)
        contentValues.put(CONTENT, person.content)
        contentValues.put(AVATAR,person.avatar)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }


    @SuppressLint("Range")
    fun getAllPeople(): ArrayList<Person> {
        val personList: ArrayList<Person> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var date: String
        var balance: Double
        var content: String
        var avatar: String

        if (cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                date = cursor.getString(cursor.getColumnIndex("date"))
                balance = cursor.getDouble(cursor.getColumnIndex("balance"))
                content = cursor.getString(cursor.getColumnIndex("content"))
                avatar = cursor.getString(cursor.getColumnIndex("avatar"))

                val person = Person(id = id, name = name, date = date, balance = balance
                    , content = content, avatar = avatar)
                personList.add(person)

            } while (cursor.moveToNext())
        }
        return personList
    }

    fun deletePerson(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TABLE_NAME, "id=$id", null)
        db.close()
        return success
    }

}