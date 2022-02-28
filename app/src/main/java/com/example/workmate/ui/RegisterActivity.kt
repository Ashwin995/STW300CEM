package com.example.workmate.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.PopupMenu
import android.widget.Toast
import com.example.workmate.R
import com.example.workmate.databinding.ActivityRegisterBinding
import com.example.workmate.entity.User
import com.example.workmate.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            val name = binding.etFullname.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val phone = binding.etPhoneNo.text.toString().trim()
            if (validateField()) {
                if (checkPassword()) {
                    val user = User(
                        name = name,
                        email = email,
                        password = password,
                        phone = phone
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        try {

                            val userRepo = UserRepository();
                            val response = userRepo.register(user)
                            if (response.success == true) {
                                withContext(Main) {
                                    startActivity(
                                        Intent(
                                            this@RegisterActivity,
                                            LoginActivity::class.java
                                        )
                                    );
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Account Created",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }

                        } catch (ex: IOException) {
                            withContext(Main) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    ex.localizedMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
        binding.chkPassword.setOnClickListener {
            showHidePassword()
        }
        binding.tvBackLogin.setOnClickListener {
            gotoLogin()
        }

    }

    private fun validateField(): Boolean {
        var flag = true
        when {
            TextUtils.isEmpty(binding.etFullname.text) -> {
                binding.etFullname.error = "Required"
                binding.etFullname.requestFocus()
                flag = false
            }
            TextUtils.isEmpty(binding.etEmail.text) -> {
                binding.etEmail.error = "Required"
                binding.etEmail.requestFocus()
                flag = false
            }
            TextUtils.isEmpty(binding.etPhoneNo.text) -> {
                binding.etPhoneNo.error = "Required"
                binding.etPhoneNo.requestFocus()
                flag = false
            }
            TextUtils.isEmpty(binding.etPassword.text) -> {
                binding.etPassword.error = "Required"
                binding.etPassword.requestFocus()
                flag = false
            }
            TextUtils.isEmpty(binding.etReenterPassword.text) -> {
                binding.etReenterPassword.error = "Required"
                binding.etReenterPassword.requestFocus()
                flag = false
            }
        }
        return flag

    }

    private fun checkPassword(): Boolean {
        var flag = true
        if (binding.etPassword.text.toString().trim() != binding.etReenterPassword.text.toString()
                .trim()
        ) {
            binding.etReenterPassword.error = "Password Does not match"
            binding.etReenterPassword.requestFocus()
            flag = false
        }
        return flag
    }

    private fun showHidePassword() {
        if (binding.chkPassword.isChecked) {
            binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.etReenterPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.chkPassword.text = "Hide Password"
        } else {
            binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.etReenterPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.chkPassword.text = "Show Password"
        }
    }

    private fun gotoLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

}