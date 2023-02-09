package com.example.apphourplay.duolingo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apphourplay.database.DuolingoDataBaseDao
import javax.sql.DataSource

class DuolingoViewModelFactory(
    private val dataSource: DuolingoDataBaseDao,
    private val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DuolingoViewModel::class.java)){
            return DuolingoViewModel(dataSource, application) as T
        }
        throw java.lang.IllegalArgumentException("ViewModel Desconocida!!!")
    }
}