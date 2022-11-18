package be.mathias.sessiondemo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mathias.sessiondemo.network.SessionApi
import be.mathias.sessiondemo.proto.Index
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _sessionTrainer = MutableLiveData<String>();

    val sessionTrainer: LiveData<String> = _sessionTrainer


    init {
        getSessions()
    }

    /**
     * okay pay attention:
     * The execute method from GetAllSessions is a SUSPEND function.
     * You can compare it in some sence to JS async/await or C# Task class
     * some resources:
     * https://medium.com/@joffrey.bion/kotlins-suspend-functions-are-not-javascript-s-async-they-are-javascript-s-await-f95aae4b3fd9
     * https://amitshekhar.me/blog/suspend-function-in-kotlin-coroutines
     * viewModelScope got an extension function 'launch' that enables you to write a new coroutine
     * without blocking the main Thread.
     *
     */
    private fun getSessions() {
        viewModelScope.launch {
            try {
                //index 6 because .proto file defines index 6 as the Trainer
                val sessionResult = SessionApi.grpcClient.GetAllSessions().execute(Index(1));

                _sessionTrainer.value = sessionResult.items[0].Trainer

            }catch(e: Exception) {
                Log.e("MainViewModel", e.message!!)
            }
        }
    }
}