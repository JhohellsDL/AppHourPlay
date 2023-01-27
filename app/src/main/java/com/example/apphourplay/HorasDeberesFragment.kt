package com.example.apphourplay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.apphourplay.databinding.FragmentHorasDeberesBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class HorasDeberesFragment : Fragment() {

    private lateinit var binding: FragmentHorasDeberesBinding

    private var isRunning = false
    private var isPaused = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHorasDeberesBinding.inflate(inflater)

        binding.timeChronometer.text = "no"

        val seconds: Long = 1000

        binding.buttonCalculeTime.setOnClickListener {
            binding.timeStart.text = "Inicio : ${getCurrentTime()}"
            binding.timeEnd.text = "Termina : ${addTime(1,0)}"
        }

        return binding.root
    }
    /*
    @OptIn(DelicateCoroutinesApi::class)
    fun startTimer(pSeconds: Long) {
        var str: String = ""
        var time = pSeconds.toInt()
        GlobalScope.launch {
            isRunning = true
            while (time >= 0) {
                if (!isPaused) {

                    val hours = time / 3600
                    val minutes = (time % 3600) / 60
                    val seconds = time % 60

                    binding.timeChronometer.text = "$hours : $minutes : $seconds "
                    println("Time remaining: $time seconds")
                    delay(1000)
                    time--
                }
                if ((time == 0) || !isRunning) {
                    break
                }
            }
            if (isRunning) {
                println("Time's up!")
            } else {
                println("Timer stopped!")
            }
        }
    }
    fun stopTimer() {
        isRunning = false
    }
    fun pauseTimer() {
        isPaused = true
    }
    fun resumeTimer() {
        isPaused = false
    }
*/

    // para obtener la hora y minutos
    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        return "$hour:$minute:$second"
    }
    private fun addTime(hours: Int, minutes: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, hours)
        calendar.add(Calendar.MINUTE, minutes)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        return "$hour:$minute:$second"
    }

}