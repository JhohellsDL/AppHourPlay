package com.example.apphourplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apphourplay.databinding.ActivityMain2Binding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    var isRunning = false
    var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val seconds: Long = 1000
        binding.textTest1.text ="Starting timer for $seconds seconds"
        println("Starting timer for $seconds seconds")
        startTimer(seconds)
        readLine()
    }

    fun startTimer(pSeconds: Long) {
        var time = pSeconds.toInt()
        GlobalScope.launch {
            isRunning = true
            while (time >= 0) {
                if (!isPaused) {

                    val hours = time / 3600
                    val minutes = (time % 3600) / 60
                    val seconds = time % 60

                    binding.textTest1.text ="Time remaining: $hours hours, $minutes minutes, $seconds seconds"
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

}