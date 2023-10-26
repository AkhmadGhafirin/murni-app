package com.cascer.murni_app.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cascer.murni_app.databinding.ActivityRegisterBinding
import com.cascer.murni_app.utils.gone
import com.cascer.murni_app.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {

            btnRegister.setOnClickListener {
                register()
            }
        }
    }

    private fun register() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        val age = binding.etAge.text.toString()
        if (username.isEmpty()) {
            binding.etUsername.error = "Username wajib diisi"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Password wajib diisi"
        } else if (!isValidPassword(password)) {
            binding.etPassword.error = "Password tidak valid"
        } else if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Confirm Password wajib diisi"
        } else if (confirmPassword != password) {
            binding.etConfirmPassword.error = "Password tidak sama"
        } else if (age.isEmpty()) {
            binding.etAge.error = "Age wajib diisi"
        } else {
            viewModel.register(username, password, age.toInt())
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            isSuccess.observe(this@RegisterActivity) {
                if (it) {
                    Toast.makeText(this@RegisterActivity, "Register berhasil", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }

            isLoading.observe(this@RegisterActivity) {
                if (it) {
                    binding.progressbar.visible()
                    binding.btnRegister.isEnabled = false
                } else {
                    binding.btnRegister.isEnabled = true
                    binding.progressbar.gone()
                }
            }

            isError.observe(this@RegisterActivity) {
                if (it) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Register tidak berhasil",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        if (password.length < 6) return false

        val hasLetter = password.any { it.isLetter() }
        val hasNumber = password.any { it.isDigit() }
        if (!hasLetter || !hasNumber) return false

        val hasUpperCase = password.any { it.isUpperCase() }
        if (!hasUpperCase) return false

        return true
    }
}