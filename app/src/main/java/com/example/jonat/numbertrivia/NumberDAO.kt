package com.example.jonat.numbertrivia

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE


@Dao
interface NumberDAO {

    @Query("SELECT * from numbers")
    fun getAllNumbers(): LiveData<List<Number>>

    @Update
    fun update(number: Number)

    @Insert (onConflict = REPLACE)
    fun insert(number: Number)

    @Query("DELETE from numbers")
    fun deleteAll()

    @Delete
    fun delete(number: Number)
}