package com.cmc.trafficlights

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var redLight: View
    private lateinit var yellowLight: View
    private lateinit var greenLight: View
    private lateinit var timerText: TextView
    private lateinit var viewModel: TrafficLightViewModel
    private val handler = Handler(Looper.getMainLooper())
    private var isScenarioRunning = false
    private val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        viewModel = ViewModelProvider(this).get(TrafficLightViewModel::class.java)
        scheduleScenario2()
        startNormalSequence()
    }

    private fun setupViews() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        redLight = findViewById(R.id.redLight)
        yellowLight = findViewById(R.id.yellowLight)
        greenLight = findViewById(R.id.greenLight)
        timerText = findViewById(R.id.timerText)
    }

    private fun scheduleScenario2() {
        val initialDelay = calculateInitialDelay()
        scheduler.scheduleWithFixedDelay({
            runOnUiThread {
                isScenarioRunning = false
                startYellowLightSequence()
            }
        }, initialDelay, TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS)
    }

    private fun calculateInitialDelay(): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return calendar.timeInMillis - System.currentTimeMillis()
    }

    private fun startNormalSequence() {
        if (isScenarioRunning) return
        isScenarioRunning = true
        switchToRed()
    }

    private fun startYellowLightSequence() {
        isScenarioRunning = true
        switchToYellow()
        handler.postDelayed({
            startNormalSequence()
        }, 3000)
    }

    private fun switchToRed() {
        activateLight(LightColor.RED, 30)
        handler.postDelayed({
            if (isScenarioRunning) switchToYellow()
        }, 30000)
    }

    private fun switchToYellow() {
        activateLight(LightColor.YELLOW, 5)
        handler.postDelayed({
            if (isScenarioRunning) switchToGreen()
        }, 5000)
    }

    private fun switchToYellowEndGreen() {
        activateLight(LightColor.YELLOW, 5)
        handler.postDelayed({
            if (isScenarioRunning) switchToRed()
        }, 5000)
    }

    private fun switchToGreen() {
        activateLight(LightColor.GREEN, 25)
        handler.postDelayed({
            if (isScenarioRunning) switchToYellowEndGreen()
        }, 25000)
    }

    private fun activateLight(color: LightColor, duration: Int) {
        viewModel.currentLight = color
        viewModel.timeLeft = duration

        redLight.setBackgroundColor(getColor(if (color == LightColor.RED) R.color.red else R.color.gray))
        yellowLight.setBackgroundColor(getColor(if (color == LightColor.YELLOW) R.color.yellow else R.color.gray))
        greenLight.setBackgroundColor(getColor(if (color == LightColor.GREEN) R.color.green else R.color.gray))

        timerText.setTextColor(getColor(when(color) {
            LightColor.RED -> R.color.red
            LightColor.YELLOW -> R.color.yellow
            LightColor.GREEN -> R.color.green
        }))

        startTimer()
    }

    private fun startTimer() {
        handler.removeCallbacksAndMessages(null)

        handler.post(object : Runnable {
            override fun run() {
                if (viewModel.timeLeft > 0) {
                    timerText.text = viewModel.timeLeft.toString()
                    viewModel.timeLeft--
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        isScenarioRunning = false
        handler.removeCallbacksAndMessages(null)
        scheduler.shutdown()
    }
}