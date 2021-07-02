package com.example.a7minuteworkoutapp.data

import androidx.lifecycle.LiveData


class HistoryRepository(private val historyDao: HistoryDao) {
    val readAllData:LiveData<List<History>> = historyDao.readAllData()

    suspend fun addDate(history: History){
        historyDao.addDate(history)
    }
}