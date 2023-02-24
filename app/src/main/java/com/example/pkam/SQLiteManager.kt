package com.example.pkam


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
        private const val TABLE_NAME_NOT = "table_cashify_not"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        // lent to
        val createTableCashify = ("CREATE TABLE "+ TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + BALANCE + " DOUBLE," + DATE + " TEXT,"
                + CONTENT + " TEXT," + AVATAR + " TEXT" + ")")
        db?.execSQL(createTableCashify)

//        // lent from
        val createTableCashifyNot = ("CREATE TABLE "+ TABLE_NAME_NOT + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + BALANCE + " DOUBLE," + DATE + " TEXT,"
                + CONTENT + " TEXT," + AVATAR + " TEXT" + ")")
        db?.execSQL(createTableCashifyNot)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_NOT")
        onCreate(db)
        // check it later
    }

    // name = name of table
    fun insertPerson(person: Person, name: String): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, person.id)
        contentValues.put(NAME,person.name)
        contentValues.put(BALANCE, person.balance)
        contentValues.put(DATE, person.date)
        contentValues.put(CONTENT, person.content)
        contentValues.put(AVATAR,person.avatar)

        val success = db.insert(name, null, contentValues)
        db.close()
        return success
    }


    fun sumBalance(name: String): Double{
        val selectQuery = "SELECT SUM(balance) FROM $name"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return 0.0
        }
        if (cursor.moveToFirst()){
            return cursor.getDouble(0)
        }
        return 0.0
    }
    fun sumBalanceByPerson(nameTable: String, n: String): Double{
        val selectQuery = "SELECT SUM(balance) FROM $nameTable WHERE name='$n';"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return 0.0
        }
        if (cursor.moveToFirst()){
            return cursor.getDouble(0)
        }
        return 0.0
    }

    fun getPerson(nameTable:String, n: String){
        val selectQuery = "SELECT * FROM $nameTable WHERE name='$n';"
    }




    @SuppressLint("Range")
    fun getAllContent(name: String, n: String): ArrayList<Content>{
        val contentList: ArrayList<Content> = ArrayList()
        val selectQuery = "SELECT * FROM $name WHERE name='$n'"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor =db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var date: String
        var content: String
        var balance: Double

        if (cursor.moveToFirst()){
            do{
                date = cursor.getString(cursor.getColumnIndex("date"))
                balance = cursor.getDouble(cursor.getColumnIndex("balance"))
                content = cursor.getString(cursor.getColumnIndex("content"))

                val con = Content(date = date, balance = balance
                    , content = content)
                contentList.add(con)
            } while (cursor.moveToNext())
        }
        return contentList
    }

    @SuppressLint("Range")
    fun getAllPeople(name: String): ArrayList<Person> {
        val personList: ArrayList<Person> = ArrayList()
        val selectQuery = "SELECT * FROM $name"
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

    fun deletePerson(id: Int, name: String): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(name, "id=$id", null)
        db.close()
        return success
    }

    fun deleteContent(con: String, name: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CONTENT, con)

        val success = db.delete(name, "content='$con'", null)
        db.close()
        return success
    }



}