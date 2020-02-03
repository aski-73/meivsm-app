package com.aveyon.meivsm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var publicAddress: String = ""
)
