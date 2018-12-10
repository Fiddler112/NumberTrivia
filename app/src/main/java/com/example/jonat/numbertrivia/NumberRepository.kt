package com.example.jonat.numbertrivia

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class NumberRepository(private val numberDao: NumberDAO) {

    val allNumbers: LiveData<List<Number>> = numberDao.getAllNumbers()


    @WorkerThread
    suspend fun insert(number: Number) {
        numberDao.insert(number)
    }

    @WorkerThread
    fun delete(number:Number){
        numberDao.delete(number)
    }

    @WorkerThread
    fun updateGame(number:Number){
        numberDao.update(number)
    }
}