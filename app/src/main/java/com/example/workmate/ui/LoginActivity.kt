package com.example.workmate.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.workmate.R
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.databinding.ActivityLoginBinding
import com.example.workmate.notification.NotificationChannels
import com.example.workmate.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.io.IOException


class LoginActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityLoginBinding
    private var activityContext = CoroutineScope(Dispatchers.IO)
    lateinit var notificationManager: NotificationManager

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    companion object {
        public var PREF_NAME: String = "loginSharedPref";
    }

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        checkRunTimePermission()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor()) {
            return
        } else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        binding.btnLogin.setOnClickListener {
            if (validateField()) {

                CoroutineScope(Dispatchers.IO).launch {
                    val email = binding.etEmail.text.toString().trim()
                    val password = binding.etPassword.text.toString().trim()
                    try {
                        val userRepo = UserRepository()
                        val getResponse = userRepo.login(email, password)
                        if (getResponse.success == true) {
                            ServiceBuilder.token = "Bearer " + getResponse.token
                            ServiceBuilder.LoggedInUser = getResponse.data?._id

                            withContext(Main) {
//                                Toast.makeText(
//                                    this@LoginActivity,
//                                    "${ServiceBuilder.LoggedInUser}",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                                setSharedPref()

                                startActivity(
                                    Intent(
                                        this@LoginActivity,
                                        DashboardActivity::class.java
                                    )
                                )
                                showNotifications()
                                finish()

                            }
                        }
                    } catch (ex: IOException) {
                        withContext(Main) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Failed ! Invalid Credentials",
                                Toast.LENGTH_LONG
                            ).show()
                            vibratePhone()
                        }
                    }

                }
            }
        }
        binding.tvgotRegister.setOnClickListener {
            gotoRegister()
        }
        getSharedPref()
    }


    private fun gotoRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun getSharedPref() {
        val spf = getSharedPreferences(LoginActivity.PREF_NAME, MODE_PRIVATE);
        val email = spf.getString("email", "");
        val password = spf.getString("password", "");
        binding.etEmail.setText(email.toString());
        binding.etPassword.setText(password.toString());
    }

    private fun setSharedPref() {
        val username = binding.etEmail.text.toString();
        val password = binding.etPassword.text.toString();
        val sharedPref = getSharedPreferences("loginSharedPref", MODE_PRIVATE);
        val editor = sharedPref.edit();
        editor.putBoolean("hasLoggedIn", true)
        editor.putString("email", username);
        editor.putString("password", password);
        editor.apply();
    }

    private fun validateField(): Boolean {
        var flag = true
        when {

            TextUtils.isEmpty(binding.etEmail.text) -> {
                binding.etEmail.error = "Required"
                binding.etEmail.requestFocus()
                flag = false
            }
            TextUtils.isEmpty(binding.etPassword.text) -> {
                binding.etPassword.error = "Required"
                binding.etPassword.requestFocus()
                flag = false
            }
        }
        return flag
    }

    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@LoginActivity, permissions, 1)
    }

    private fun showNotifications() {

        val notificationManager = NotificationManagerCompat.from(this)

        val notificationChannels = NotificationChannels(this)
        notificationChannels.createNotificationChannels()

        val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Workmate")
            .setContentText("Welcome To Workmate")
            .setColor(Color.BLUE)
            .build()

        notificationManager.notify(1, notification)

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
            binding.btnLogin.isEnabled = false
            Toast.makeText(this, "Don't block front camera", Toast.LENGTH_SHORT).show()
        }
        binding.btnLogin.isEnabled=true
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun vibratePhone() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }
}