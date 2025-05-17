package com.example.dreamdecoder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class JournalFragment : Fragment() {

    private lateinit var dreamInput: EditText
    private lateinit var saveDreamButton: ImageButton
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_journal, container, false)

        dreamInput = view.findViewById(R.id.dreamInput)
        saveDreamButton = view.findViewById(R.id.saveDreamButton) // Correct for ImageButton
        sharedPrefs = requireActivity().getSharedPreferences("dreams", Context.MODE_PRIVATE)

        val instructionButton = view.findViewById<ImageButton>(R.id.btn_instructions)
        instructionButton.setOnClickListener {
            val intent = android.content.Intent(requireContext(), InstructionActivity::class.java)
            startActivity(intent)
        }

        saveDreamButton.setOnClickListener {
            val dreamText = dreamInput.text.toString().trim()

            if (dreamText.isNotEmpty()) {
                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                sharedPrefs.edit().putString(currentDate, dreamText).apply()
                Toast.makeText(requireContext(), "Dream saved for $currentDate", Toast.LENGTH_SHORT).show()
                dreamInput.setText("")
            } else {
                Toast.makeText(requireContext(), "Please enter a dream", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}

