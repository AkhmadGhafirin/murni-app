package com.cascer.murni_app.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cascer.murni_app.R
import com.cascer.murni_app.data.model.User
import com.cascer.murni_app.databinding.ActivityMainBinding
import com.cascer.murni_app.utils.gone
import com.cascer.murni_app.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra("username").orEmpty()
        viewModel.getUser(username)
        setupViewModel()
    }

    private fun setupView(user: User) {
        with(binding) {
            etAge.setText(user.age.toString())
            tvTitle.text = getString(R.string.hello, username)
            btnEdit.isEnabled = false
            etAge.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    btnEdit.isEnabled = s.toString() != user.age.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
            btnEdit.setOnClickListener {
                editUser(username, user.password)
            }
        }
    }

    private fun editUser(username: String, password: String) {
        val age = binding.etAge.text.toString()
        if (age.isEmpty()) {
            binding.etAge.error = "Age wajib diisi"
        } else {
            viewModel.editUser(username, password, age.toInt())
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            user.observe(this@MainActivity) {
                setupView(it)
            }

            isSuccess.observe(this@MainActivity) {
                if (it) {
                    Toast.makeText(this@MainActivity, "Edit berhasil", Toast.LENGTH_SHORT).show()
                }
            }

            isLoading.observe(this@MainActivity) {
                if (it) {
                    binding.progressbar.visible()
                    binding.btnEdit.isEnabled = false
                } else {
                    binding.btnEdit.isEnabled = true
                    binding.progressbar.gone()
                }
            }

            isError.observe(this@MainActivity) {
                if (it) {
                    Toast.makeText(this@MainActivity, "Edit tidak berhasil", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}