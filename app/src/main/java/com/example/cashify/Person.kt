package com.example.cashify

class Person(var name: String, var date: String, var content: String, var balance: Double) {

    override fun toString(): String {
        return "$name $date $content $balance"
    }

}