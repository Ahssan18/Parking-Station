package com.appsuite.earn.money.playing.bikestationapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appsuite.earn.money.playing.bikestationapp.model.BikesStation
import com.appsuite.earn.money.playing.bikestationapp.repository.StationRepository
import kotlinx.coroutines.*

class StationViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<BikesStation>()
    var repositoryStation = StationRepository()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAllStation() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = repositoryStation.getStations()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body())
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
    }
}


