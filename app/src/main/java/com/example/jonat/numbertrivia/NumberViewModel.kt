package com.example.jonat.numbertrivia

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NumberViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: NumberRepository

    val allNumbers: LiveData<List<Number>>

    init {
        val numberDao = NumberDatabase.getDatabase(application, scope).numberDAO()
        repository = NumberRepository(numberDao)
        allNumbers = repository.allNumbers
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(number: Number) = scope.launch(Dispatchers.IO) {
        repository.insert(number)
    }
    fun delete(number: Number) {
        repository.delete(number)
    }
    fun updateGame(number: Number) = scope.launch(Dispatchers.IO) {
        repository.updateGame(number)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}