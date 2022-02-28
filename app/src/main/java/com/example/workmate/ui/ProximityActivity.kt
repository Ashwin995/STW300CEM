package com.example.workmate.ui

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workmate.R

open class ProximityActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor()) {
            return
        } else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
            flag = false
        }
        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {

        val values = event!!.values[0]
        if (values <= 4) {
            Toast.makeText(this, "Don't block front camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}