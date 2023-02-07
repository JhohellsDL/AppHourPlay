package com.example.apphourplay.duolingo

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.apphourplay.databinding.CardDuolingoAddTimeBinding
import com.example.apphourplay.databinding.FragmentDuolingoBinding
import com.example.apphourplay.databinding.SettingsDuolingoDialogBinding
import java.text.SimpleDateFormat
import java.util.*

class DuolingoFragment : Fragment() {

    private lateinit var viewModel: DuolingoViewModel

    private lateinit var binding : FragmentDuolingoBinding
    private lateinit var bindingDialog: SettingsDuolingoDialogBinding
    private lateinit var bindingDialogAdd: CardDuolingoAddTimeBinding

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

    private var modifyHour: String = "00:00"
    private var modifyHour1: String = "00:00"

    private var minutesAdd: Int = 0
    private var minutesAdd1: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDuolingoBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[DuolingoViewModel::class.java]

        binding.duolingoViewModel = viewModel
        binding.lifecycleOwner = this

        binding.time1.text = "Faltan $timeMinutes Minutes"

        unity = (100/timeMinutes.toFloat())

        binding.buttonAddTime.setOnClickListener {
            bindingDialogAdd = CardDuolingoAddTimeBinding.inflate(layoutInflater)
            val myDialog = Dialog(it.context)
            myDialog.setContentView(bindingDialogAdd.root)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            bindingDialogAdd.btnTest.setOnClickListener {
                if (bindingDialogAdd.etMinutesAdd.text.toString().isEmpty()){
                    myDialog.dismiss()
                } else {
                    minutesAdd = bindingDialogAdd.etMinutesAdd.text.toString().toInt()
                    timeRemaining += minutesAdd
                    binding.textState.text = "Add time"
                    myDialog.dismiss()
                }
            }
        }
        binding.buttonAddTime1.setOnClickListener {
            bindingDialogAdd = CardDuolingoAddTimeBinding.inflate(layoutInflater)
            val myDialog = Dialog(it.context)
            myDialog.setContentView(bindingDialogAdd.root)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            bindingDialogAdd.btnTest.setOnClickListener {
                if (bindingDialogAdd.etMinutesAdd.text.toString().isEmpty()){
                    myDialog.dismiss()
                } else {
                    minutesAdd1 = bindingDialogAdd.etMinutesAdd.text.toString().toInt()
                    timeRemaining1 += minutesAdd1
                    binding.textState1.text = "Add time"
                    myDialog.dismiss()
                }
            }
        }

        binding.buttonModifyTime.setOnClickListener {

            bindingDialog = SettingsDuolingoDialogBinding.inflate(layoutInflater)
            val myDialog = Dialog(it.context)
            myDialog.setContentView(bindingDialog.root)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            bindingDialog.btnTest.setOnClickListener {
                if (bindingDialog.etHourStart.text.toString().isEmpty() || bindingDialog.etMinutesStart.text.toString().isEmpty()){
                    myDialog.dismiss()
                } else {
                    modifyHour = "${bindingDialog.etHourStart.text.toString()}:${bindingDialog.etMinutesStart.text.toString()}"
                    println("start modify : $modifyHour")

                    startHour = modifyHour
                    endHour = getNewTime(startHour,0,timeMinutes)
                    binding.timeStart.text = "$startHour"
                    binding.timeEnd.text = "$endHour"

                    binding.textState.text = "Modify"
                    binding.progressBar.progress = 0 //(unity*timeMinutes).toInt()
                    println("start : $startHour - $endHour")

                    myDialog.dismiss()
                }
            }
        }
        binding.buttonModifyTime1.setOnClickListener {

            bindingDialog = SettingsDuolingoDialogBinding.inflate(layoutInflater)
            val myDialog = Dialog(it.context)
            myDialog.setContentView(bindingDialog.root)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            bindingDialog.btnTest.setOnClickListener {
                if (bindingDialog.etHourStart.text.toString().isEmpty() || bindingDialog.etHourStart.text.toString().isEmpty()){
                    myDialog.dismiss()
                } else {
                    modifyHour1 = "${bindingDialog.etHourStart.text.toString()}:${bindingDialog.etMinutesStart.text.toString()}"
                    println("start modify : $modifyHour1")

                    startHour1 = modifyHour1
                    endHour1 = getNewTime(startHour1,0,timeMinutes)
                    binding.timeStart1.text = "$startHour1"
                    binding.timeEnd1.text = "$endHour1"

                    binding.textState1.text = "Modifyt"
                    binding.progressBar1.progress = 0 //(unity*timeMinutes).toInt()
                    println("start : $startHour1- $endHour1")

                    myDialog.dismiss()
                }
            }
        }

        binding.buttonCalculeTime1.setOnClickListener {
            startHour1 = getCurrentTime()
            endHour1 = addTime(0,timeMinutes)
            binding.timeStart1.text = "$startHour1"
            binding.timeEnd1.text = "$endHour1"

            binding.textState1.text = "Start"
            binding.progressBar1.progress = 0 //(unity*timeMinutes).toInt()
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

            binding.textState.text = "Stop"
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

            binding.textState1.text = "Stop"
            binding.timeOnPause1.text = "$stopHour1"
        }

        binding.buttonContinue.setOnClickListener {
            newStartHour = getCurrentTime()

            endHour = addTime(0,timeRemaining)

            binding.timeNewStart.text = startHour
            binding.timeStart.text = newStartHour
            startHour = newStartHour
            binding.timeEnd.text = "$endHour"

            binding.textState.text = "Continue"
            println("stop : $newStartHour - $timeRemaining")
            println("stop : $endHour - $timeElapsed")
        }
        binding.buttonContinue1.setOnClickListener {
            newStartHour1 = getCurrentTime()

            endHour1 = addTime(0,timeRemaining1)

            binding.timeNewStart1.text = startHour1
            binding.timeStart1.text = newStartHour1
            binding.timeEnd1.text = "$endHour1"
            startHour1 = newStartHour1
            binding.textState1.text = "Continue"
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