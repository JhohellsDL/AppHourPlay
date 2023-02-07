package com.example.apphourplay.duolingo

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class DuolingoViewModel: ViewModel() {
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 100L
        // This is the total time of the game 3600 000L = 1 hour, 1000L = 1 second
        const val COUNTDOWN_TIME = 120000L

        const val STATE_START = "Start"
        const val STATE_CONTINUE = "Continue"
        const val STATE_STOP = "Stop"
        const val STATE_MODIFY = "Modify"
    }

    private lateinit var timer: CountDownTimer

    private var _currentTime = MutableLiveData<Long>()
    val currentTime : LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) {
        DateUtils.formatElapsedTime(it)
    }

    private var _startHour = MutableLiveData<String>()
    val startHour : LiveData<String>
        get() = _startHour
    private var _endHour = MutableLiveData<String>()
    val endHour: LiveData<String>
        get() = _endHour

    private var _timeMinutes = MutableLiveData<Int>()
    val timeMinutes: LiveData<Int>
        get() = _timeMinutes

    private var _timeRemaining = MutableLiveData<Int>()
    val timeRemaining: LiveData<Int>
        get() = _timeRemaining

    private var _state = MutableLiveData<String>()
    val state: LiveData<String>
        get() = _state

    private var _progressBar = MutableLiveData<Int>()
    val progressBar: LiveData<Int>
        get() = _progressBar

    private var _unity = MutableLiveData<Float>()
    val unity: LiveData<Float>
        get() = _unity


    init {

    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    fun onTimerStart(){
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/1000
                Log.i("TimeDuo","Unity : ${_progressBar.value} , ${_unity.value}")
            }

            override fun onFinish() {
                _currentTime.value = DONE
            }
        }

        timer.start()
    }

    fun onStartTime(){
        _timeMinutes.value = 60 // 3600
        _startHour.value = getCurrentTime()
        _endHour.value = addTime(0, _timeMinutes.value!!)
        _state.value = STATE_START
        _unity.value = (100/12000F)
        _progressBar.value = 100
    }



    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return "$hour:$minute"
    }
    private fun addTime(hours: Int, minutes: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, hours)
        calendar.add(Calendar.MINUTE, minutes)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return "$hour:$minute"
    }
    fun initAll(){
        onStartTime()
        onTimerStart()
        Log.i("TimeDuo","ok111")
    }
}