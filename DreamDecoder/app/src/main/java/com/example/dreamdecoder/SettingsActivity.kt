package com.example.dreamdecoder

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var logoutButton: ImageButton  // Change to ImageButton
    private lateinit var changeEmail: EditText
    private lateinit var changePassword: EditText
    private lateinit var changeUsername: EditText
    private lateinit var saveButton: ImageButton  // Change to ImageButton
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            finish() // Go back to previous screen
        }

        sharedPref = getSharedPreferences("userData", MODE_PRIVATE)

        logoutButton = findViewById(R.id.logoutButton)
        changeEmail = findViewById(R.id.editEmail)
        changePassword = findViewById(R.id.editPassword)
        changeUsername = findViewById(R.id.usernameInput)
        saveButton = findViewById(R.id.saveButton)

        // Set current values
        changeEmail.setText(sharedPref.getString("email", ""))
        changePassword.setText(sharedPref.getString("password", ""))
        changeUsername.setText(sharedPref.getString("username", "Dreamer"))

        logoutButton.setOnClickListener {
            sharedPref.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        saveButton.setOnClickListener {
            val email = changeEmail.text.toString().trim()
            val password = changePassword.text.toString().trim()
            val username = changeUsername.text.toString().trim()

            // Validate fields
            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save to SharedPreferences without hashing
            with(sharedPref.edit()) {
                putString("email", email)
                putString("password", password)
                putString("username", username)
                apply()
            }
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show()
        }
    }
}
