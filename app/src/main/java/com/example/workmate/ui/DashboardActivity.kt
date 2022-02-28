package com.example.workmate.ui

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.workmate.R
import com.example.workmate.databinding.ActivityDashboardBinding
import com.example.workmate.ui.fragments.HomeFragment
import com.example.workmate.ui.fragments.ProfileFragment

class DashboardActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor()) {
            return
        } else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        setCurrentFragment(homeFragment)
        binding.navigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miProfile -> setCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
            flag = false
        }
        return flag
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    override fun onSensorChanged(event: SensorEvent?) {

        val values = event!!.values[0]
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}
