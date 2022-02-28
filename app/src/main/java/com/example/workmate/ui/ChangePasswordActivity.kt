package com.example.workmate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.example.workmate.databinding.ActivityChangePasswordBinding
import com.example.workmate.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.io.IOException

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnUpdatePassword.setOnClickListener {
            if (validateField()) {
                doChangePassword()
            }
        }
        binding.chkPassword.setOnClickListener {
            showHidePassword()
        }


    }

    private fun doChangePassword() {

        val oldPassword = binding.etOldPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val userRepo = UserRepository()
                val response = userRepo.changePassword(oldPassword, newPassword)
                if (response.success == true) {
                    withContext(Main) {
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            "Password Updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: IOException) {
                withContext(Main) {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "${ex.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validateField(): Boolean {
        var flag: Boolean = true;
        if (TextUtils.isEmpty(binding.etNewPassword.text.toString())) {
            binding.etNewPassword.error = "Required"
            binding.etNewPassword.requestFocus()
            flag = false
        } else if (TextUtils.isEmpty(binding.etOldPassword.text.toString())) {
            binding.etOldPassword.error = "Required"
            binding.etOldPassword.requestFocus()
            flag = false
        }
        return flag
    }

    private fun showHidePassword() {
        if (binding.chkPassword.isChecked) {
            binding.etNewPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()

            binding.chkPassword.text = "Hide Password"
        } else {
            binding.etNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()

            binding.chkPassword.text = "Show Password"
        }
    }
}