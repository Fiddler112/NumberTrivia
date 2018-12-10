package com.example.jonat.numbertrivia

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "numbers")

data class Number(
        @ColumnInfo(name = "number") val number: String,
        @ColumnInfo(name = "numberId") val numberId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}