package com.example.pkam

import java.util.*

class Person(var name: String, var date: String, var content: String, var balance: Double, var id: Int = getAutoId(), var avatar: String) {

    override fun toString(): String {
        return "$name $date $content $balance"
    }

    companion object {
        private fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }

}