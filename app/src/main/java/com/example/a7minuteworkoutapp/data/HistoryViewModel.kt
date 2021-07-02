package com.example.a7minuteworkoutapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HistoryViewModel(application: Application):AndroidViewModel(application) {
    private val  readAllData:LiveData<List<History>>
    private val repository: HistoryRepository

    init{
        val historyDao = HistoryDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(historyDao)
        readAllData = repository.readAllData
    }

    fun addDate(history: History){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDate(history)
        }
    }
}