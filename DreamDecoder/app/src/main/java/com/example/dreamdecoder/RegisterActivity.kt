package com.example.dreamdecoder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: ImageButton
    private lateinit var loginRedirect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailEditText = findViewById(R.id.registerEmailEditText)
        passwordEditText = findViewById(R.id.registerPasswordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        registerButton = findViewById(R.id.registerButton)
        loginRedirect = findViewById(R.id.loginRedirect)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save new user to SharedPreferences (mock storage)
            val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("email", email)
                putString("password", password)
                apply()
            }

            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()

            // Go to MainActivity directly after registration
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 🔄 Redirect to login screen
        loginRedirect.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

