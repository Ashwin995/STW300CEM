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

            binding.btnLogin.setOnClickListener{
                if (binding.etEmail.text.toString()=="hari@gmail.com"
                    && binding.etPassword.text.toString()=="Hari234"){
                Toast.makeText(this, "Welcome Hari", Toast.LENGTH_SHORT).show()
            }
                else{
                    Toast.makeText(this, "Access denied", Toast.LENGTH_SHORT).show()
                }
        }


    }
}