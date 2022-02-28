package com.example.wearables

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.example.wearables.databinding.ActivityLoginBinding

class LoginActivity : Activity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (binding.etEmail.text.equals("asbin@gmail.com")
            && binding.etPassword.text.equals("asbin")
        ) {
            Toast.makeText(this, "Welcome Asbin", Toast.LENGTH_SHORT).show()
        }

    }
}