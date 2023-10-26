package com.cascer.murni_app.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cascer.murni_app.databinding.ActivityLoginBinding
import com.cascer.murni_app.ui.home.MainActivity
import com.cascer.murni_app.ui.register.RegisterActivity
import com.cascer.murni_app.utils.gone
import com.cascer.murni_app.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {

            btnLogin.setOnClickListener {
                login()
            }

            btnRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun login() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        if (username.isEmpty()) {
            binding.etUsername.error = "Username wajib diisi"
        } else if (!isValidEmail(username)) {
            binding.etUsername.error = "Email tidak valid"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Password wajib diisi"
        } else {
            viewModel.login(username, password)
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            isSuccess.observe(this@LoginActivity) {
                if (it) {
                    Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("username", binding.etUsername.text.toString())
                    startActivity(intent)
                }
            }

            isLoading.observe(this@LoginActivity) {
                if (it) {
                    binding.progressbar.visible()
                    binding.btnRegister.isEnabled = false
                    binding.btnLogin.isEnabled = false
                } else {
                    binding.btnRegister.isEnabled = true
                    binding.btnLogin.isEnabled = true
                    binding.progressbar.gone()
                }
            }

            isError.observe(this@LoginActivity) {
                if (it) {
                    Toast.makeText(this@LoginActivity, "Login tidak berhasil", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}