package com.example.apphourplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apphourplay.databinding.FragmentDuolingoBinding
import java.text.SimpleDateFormat
import java.util.*

class DuolingoFragment : Fragment() {

    private lateinit var binding : FragmentDuolingoBinding

    private val timeMinutes: Int = 60

    private var timeRemaining: Int = timeMinutes
    private var timeRemaining1: Int = timeMinutes

    private var elapsedHour: String = "00:00"
    private var timeElapsed: Int = 0
    private var elapsedHour1: String = "00:00"
    private var timeElapsed1: Int = 0

    private var startHour: String = "00:00"
    private var endHour: String = "00:00"
    private var stopHour: String = "00:00"
    private var newStartHour: String = "00:00"
    private var startHour1: String = "00:00"
    private var endHour1: String = "00:00"
    private var stopHour1: String = "00:00"
    private var newStartHour1: String = "00:00"

    private var progress : Int = 0
    private var unity : Float = 0f
    private var progress1 : Int = 0
    private var unity1 : Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDuolingoBinding.inflate(inflater)

        binding.time.text = "Faltan $timeMinutes Minutes"
        binding.time1.text = "Faltan $timeMinutes Minutes"

        unity = (100/timeMinutes.toFloat())

        println("unityy : $unity")

        binding.buttonCalculeTime.setOnClickListener {
            startHour = getCurrentTime()
            endHour = addTime(0,timeMinutes)
            binding.timeStart.text = "$startHour"
            binding.timeEnd.text = "$endHour"

            binding.progressBar.progress = (unity*timeMinutes).toInt()
            println("start : $startHour - $endHour")
        }
        binding.buttonCalculeTime1.setOnClickListener {
            startHour1 = getCurrentTime()
            endHour1 = addTime(0,timeMinutes)
            binding.timeStart1.text = "$startHour1"
            binding.timeEnd1.text = "$endHour1"

            binding.progressBar1.progress = (unity*timeMinutes).toInt()
            println("start1 : $startHour1- $endHour1")
        }

        binding.buttonStop.setOnClickListener {
            stopHour = getCurrentTime()
            elapsedHour = getTimeDifference(startHour, stopHour)
            timeElapsed = (elapsedHour.split(":")[0].toInt()*60) + (elapsedHour.split(":")[1].toInt())
            timeRemaining -= timeElapsed
            binding.time.text = "Faltan $timeRemaining Minutes"


            progress  = 100-(unity*timeRemaining).toInt()
            binding.progressBar.progress = progress
            println("stop : $startHour - $stopHour - $progress")
            println("stop : $elapsedHour - $timeElapsed")

            binding.timeOnPause.text = "$stopHour"
        }
        binding.buttonStop1.setOnClickListener {
            stopHour1 = getCurrentTime()
            elapsedHour1 = getTimeDifference(startHour1, stopHour1)
            timeElapsed1 = (elapsedHour1.split(":")[0].toInt()*60) + (elapsedHour1.split(":")[1].toInt())
            timeRemaining1 -= timeElapsed1
            binding.time1.text = "Faltan $timeRemaining1 Minutes"


            progress1  = 100-(unity*timeRemaining1).toInt()
            binding.progressBar1.progress = progress1
            println("stop1 : $startHour1 - $stopHour1 - $progress1")
            println("stop1 : $elapsedHour1 - $timeElapsed1")

            binding.timeOnPause1.text = "$stopHour1"
        }

        binding.buttonContinue.setOnClickListener {
            startHour = getCurrentTime()
            newStartHour = getCurrentTime()

            endHour = addTime(0,timeRemaining)

            binding.timeNewStart.text = newStartHour
            binding.timeEnd.text = "$endHour"

            println("stop : $newStartHour - $timeRemaining")
            println("stop : $endHour - $timeElapsed")
        }
        binding.buttonContinue1.setOnClickListener {
            startHour1 = getCurrentTime()
            newStartHour1 = getCurrentTime()

            endHour1 = addTime(0,timeRemaining1)

            binding.timeNewStart1.text = newStartHour1
            binding.timeEnd1.text = "$endHour1"

            println("continue1 : $newStartHour1 - $timeRemaining1")
            println("continue1 : $endHour1 - $timeElapsed1")
        }

        return binding.root
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

    private fun getTimeDifference(time1: String, time2: String): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val date1 = dateFormat.parse(time1)
        val date2 = dateFormat.parse(time2)
        val diff = date2.time - date1.time
        val minutes = diff / (60 * 1000) % 60
        val hours = diff / (60 * 60 * 1000) % 24
        return "$hours:$minutes"
    }
    private fun getNewTime(time: String, hours: Int, minutes: Int): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val date = dateFormat.parse(time)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, hours)
        calendar.add(Calendar.MINUTE, minutes)
        return dateFormat.format(calendar.time)
    }

    fun compareTimes(time1: String, time2: String): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        val date1 = dateFormat.parse(time1)
        val date2 = dateFormat.parse(time2)
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2

        val hour1 = cal1.get(Calendar.HOUR_OF_DAY)
        val minute1 = cal1.get(Calendar.MINUTE)
        val second1 = cal1.get(Calendar.SECOND)
        val hour2 = cal2.get(Calendar.HOUR_OF_DAY)
        val minute2 = cal2.get(Calendar.MINUTE)
        val second2 = cal2.get(Calendar.SECOND)

        if (hour1 > hour2) {
            return "Time 1 is greater"
        } else if (hour1 < hour2) {
            return "Time 2 is greater"
        } else {
            if (minute1 > minute2) {
                return "Time 1 is greater"
            } else if (minute1 < minute2) {
                return "Time 2 is greater"
            } else {
                if (second1 > second2) {
                    return "Time 1 is greater"
                } else if (second1 < second2) {
                    return "Time 2 is greater"
                } else {
                    return "Both times are equal"
                }
            }
        }
    }
}