package com.example.mycontacts.util

import java.text.DateFormat
import java.util.*

class DateUtil {

    fun getDate(dateFormat: DateFormat, milliseconds: String) : String {
        val date = Date(milliseconds.toLong())
        return dateFormat.format(date)
    }
}