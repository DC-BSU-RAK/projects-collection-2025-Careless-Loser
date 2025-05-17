package com.example.dreamdecoder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: ImageButton
    private lateinit var registerRedirect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Auto-login check
        val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
        val savedEmail = sharedPref.getString("email", null)
        if (savedEmail != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerRedirect = findViewById(R.id.registerRedirect)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Simple input validation
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check hardcoded credentials
            if (email == "test@dream.com" && password == "1234") {
                // Save login state
                with(sharedPref.edit()) {
                    putString("email", email)
                    apply()
                }
                // Go to main journal page
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        registerRedirect.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
