package com.example.dreamdecoder

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        // Set default fragment on launch
        loadFragment(JournalFragment())

        // Set up button click listeners
        findViewById<ImageButton>(R.id.btn_journal).setOnClickListener {
            loadFragment(JournalFragment())
        }

        findViewById<ImageButton>(R.id.btn_interpreter).setOnClickListener {
            loadFragment(InterpreterFragment())
        }

        findViewById<ImageButton>(R.id.btn_calendar).setOnClickListener {
            loadFragment(CalendarFragment())
        }

        findViewById<ImageButton>(R.id.btn_settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
