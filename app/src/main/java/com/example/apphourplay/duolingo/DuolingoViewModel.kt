package com.example.apphourplay.duolingo

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import com.example.apphourplay.database.DuolingoDataBaseDao
import com.example.apphourplay.database.DuolingoUserTime
import kotlinx.coroutines.*
import java.util.*

class DuolingoViewModel(
    val database: DuolingoDataBaseDao,
    application: Application): AndroidViewModel(application) {
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

    private var _countDownTime = MutableLiveData<Int>()
    val countDownTime: LiveData<Int>
        get() = _countDownTime

    private var _startHour = MutableLiveData<String>()
    val startHour : LiveData<String>
        get() = _startHour
    private var _newStartHour = MutableLiveData<String>()
    val newStartHour : LiveData<String>
        get() = _newStartHour
    private var _endHour = MutableLiveData<String>()
    val endHour: LiveData<String>
        get() = _endHour
    private var _stopHour = MutableLiveData<String>()
    val stopHour: LiveData<String>
        get() = _stopHour

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

    private var _duoUser =MutableLiveData<DuolingoUserTime?>()
    private val duoUser: LiveData<DuolingoUserTime?>
        get() = _duoUser

    private var _aux = MutableLiveData<String>()
    val aux: LiveData<String>
        get() = _aux

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //private var duoUser = MutableLiveData<DuolingoTime?>()
    private val duoUsers = database.getAllDuoUsers()

    private lateinit var ussss : DuolingoUserTime

    init {
        initializeDuoUser()

        if (_duoUser.value?.timeState == STATE_START){
            //val timeR = differenceBetweenTimes(_duoUser.value?.startTimeMilli.toString(), getCurrentTime())
            //_duoUser.value?.timeRemaining = _duoUser.value?.timeRemaining - timeR
        }
    }
    // definiendo el estado si poner o no crhonometroasdfhjalks

    private fun initializeDuoUser() {
        uiScope.launch {
            _duoUser.value = getDuoUserFromDataBase()
            _aux.value = _duoUser.value?.startTimeMilli.toString()
            _stopHour.value = _duoUser.value?.stopTimeMilli.toString()
            _endHour.value = _duoUser.value?.endTimeMilli.toString()
            _newStartHour.value = _duoUser.value?.startTimeMilli2.toString()
            _state.value = _duoUser.value?.timeState
            _timeRemaining.value = _duoUser.value?.timeRemaining

            Log.i("Duo","User1_ ${_duoUser.value?.userId}")
            Log.i("Duo","User2_ ${_duoUser.value?.userName}")
            Log.i("Duo","User3_ ${_duoUser.value?.startTimeMilli}")
            Log.i("Duo","User4_ ${_duoUser.value?.startTimeMilli2}")
            Log.i("Duo","User5_ ${_duoUser.value?.stopTimeMilli}")
            Log.i("Duo","User6_ ${_duoUser.value?.timeExtra}")
            Log.i("Duo","User7_ ${_duoUser.value?.timeRemaining}")
            Log.i("Duo","User8_ ${_duoUser.value?.timeState}")
            Log.i("Duo","Userssss_ ${duoUsers.value}")

            //Log.i("Duo","Usergettttt_ ${usess}")
        }
    }

/*
    private suspend fun getDuoUserFromDataBase2(id: Long): DuolingoUserTime? {
        return withContext(Dispatchers.IO){
            var user = database.get(id)
            Log.i("Duo","Userasdfasdf_ ${user.startTimeMilli}")
            user
        }
    }
    fun getuse(id: Long){
       uiScope.launch {
           val newDuoTime2 =  getDuoUserFromDataBase2(id)
           Log.i("Duo","Useasdfasdfasdfasdfr_ ${newDuoTime2?.startTimeMilli}")
       }
    }
*/

    private suspend fun getDuoUserFromDataBase(): DuolingoUserTime? {
        return withContext(Dispatchers.IO){
            var user = database.getDuolingoUser()
            user
        }
    }

    fun onStartTimeDuo(){
        uiScope.launch {
            initAll()

            val newDuoTime = DuolingoUserTime()
            newDuoTime.userName = "Andrew"
            newDuoTime.timeRemaining = 60
            newDuoTime.endTimeMilli = addTime(0, _timeMinutes.value!!)

            insert(newDuoTime)
            _duoUser.value = getDuoUserFromDataBase()

            initializeDuoUser()
        }
    }
    private suspend fun insert(newDuoTime: DuolingoUserTime) {
        withContext(Dispatchers.IO){
            database.insert(newDuoTime)
        }
    }

    fun onStopDuoTime(){
        uiScope.launch {
            onPausedTime()

            val stopDuoTime = duoUser.value ?: return@launch
            stopDuoTime.stopTimeMilli = getCurrentTime()
            stopDuoTime.timeRemaining = currentTime.value!!.toInt()

            update(stopDuoTime)
            initializeDuoUser()
        }
    }
    private suspend fun update(updateDuoTime: DuolingoUserTime) {
        withContext(Dispatchers.IO){
            database.update(updateDuoTime)
        }
    }

    fun onContineDuoTime(){
        uiScope.launch {
            onContinueTime()
            val continueDuoTime = duoUser.value ?: return@launch
            continueDuoTime.startTimeMilli2 = continueDuoTime.startTimeMilli
            continueDuoTime.startTimeMilli = getCurrentTime()
            continueDuoTime.endTimeMilli = addTime(0, (_timeRemaining.value!!/60))

            update(continueDuoTime)
            Log.i("Duo","onCotinueTimeDuo ${continueDuoTime.startTimeMilli2}")
            initializeDuoUser()
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
            _duoUser.value = null
        }
    }
    suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }


    // Ingresar el tiempo en segundos
    fun onTimerStart(pTime: Int){

        val timeLong = (pTime*1000).toLong()
        timer = object : CountDownTimer(timeLong, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/1000
            }

            override fun onFinish() {
                _currentTime.value = DONE
            }
        }

        timer.start()
    }

    fun onStartTime(){
        _timeMinutes.value = 60 // 3600
        //_startHour.value = getCurrentTime()
        //_endHour.value = addTime(0, _timeMinutes.value!!)
        _state.value = STATE_START
        _unity.value = (100/12000F)
        _progressBar.value = 100
    }
    fun onPausedTime(){
        //_stopHour.value = getCurrentTime()
        //_timeRemaining.value = currentTime.value!!.toInt()
        _state.value = STATE_STOP
        timer.cancel()
    }

    fun onContinueTime(){
        //_newStartHour.value = startHour.value
        //_startHour.value = getCurrentTime()
        //_endHour.value = addTime(0, (_timeRemaining.value!!/60))
        _state.value = STATE_CONTINUE
        onTimerStart(_timeRemaining.value!!)
    }

    fun stopTimer(){
        Log.i("TimeDuo", "stop!:_ ${_currentTime.value}")
        timer.cancel()
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
        onTimerStart(3600)
    }

    fun differenceBetweenTimes(startTime: String, endTime: String): Int {
        val startTimeInMiliseconds = convertTimeToMiliseconds(startTime)
        val endTimeInMiliseconds = convertTimeToMiliseconds(endTime)
        return endTimeInMiliseconds - startTimeInMiliseconds
    }

    fun convertTimeToMiliseconds(timeString: String): Int {
        val hours = timeString.split(":")[0].toInt()
        val minutes = timeString.split(":")[1].toInt()
        return (hours * 60 + minutes) * 60 * 1000
    }
}