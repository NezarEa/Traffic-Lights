package com.cmc.trafficlights

import androidx.lifecycle.ViewModel

class TrafficLightViewModel : ViewModel() {
    var timeLeft: Int = 0
    var currentLight: LightColor = LightColor.RED
}