package com.example.apphourplay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.apphourplay.databinding.FragmentSorteoBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SorteoFragment : Fragment() {

    private lateinit var binding: FragmentSorteoBinding
    var isRunning = false
    var isPaused = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSorteoBinding.inflate(inflater)

        // para agregar Safe args
        val args = SorteoFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "numero recibido: ${args.nroTest} !!!", Toast.LENGTH_SHORT).show()

        //binding.textTest.text = "La hora actual es: ${getCurrentTime()} \n ${addTime(2,35)} "

        // para obtener los datos del otro fragment
        setHasOptionsMenu(true)
/*
        binding.buttonCompartir.setOnClickListener {
            shareSuccess()
        }*/
        val seconds: Long = 1000
        binding.textTest.text ="Starting timer for $seconds seconds"
        println("Starting timer for $seconds seconds")
        startTimer(seconds)
        readLine()

        binding.buttonCompartir.setOnClickListener {
            pauseTimer()
        }
        binding.buttonRestar.setOnClickListener {
            resumeTimer()
        }


        return binding.root


    }
    @OptIn(DelicateCoroutinesApi::class)
    fun startTimer(pSeconds: Long) {
        var time = pSeconds.toInt()
        GlobalScope.launch {
            isRunning = true
            while (time >= 0) {
                if (!isPaused) {

                    val hours = time / 3600
                    val minutes = (time % 3600) / 60
                    val seconds = time % 60

                    binding.textTest.text ="Time remaining: $hours hours, $minutes minutes, $seconds seconds"
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

    fun startTimer1(seconds: Long) {
        var time = seconds
        GlobalScope.launch {
            while (time >= 0) {
                binding.textTest.text ="Starting timer for $time seconds"
                println("Time remaining: $time seconds")
                delay(1000)
                time--
            }
            println("Time's up!")
        }
    }


    private fun getShareIntent(): Intent{
        val args = SorteoFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.shared))
        return shareIntent
    }
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // para obtener la hora y minutos
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
}