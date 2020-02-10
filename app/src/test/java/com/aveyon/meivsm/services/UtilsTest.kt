package com.aveyon.meivsm.services

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UtilsTest {

    @Test
    fun parsingDatestringToLongTimestamp() {
        // GIVEN
        var stringDate = "10.12.2021"

        // WHEN
        var dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
        var date = dateFormat.parse(stringDate)

        // THEN
        assert(1639090800000 == date.time)
    }
}