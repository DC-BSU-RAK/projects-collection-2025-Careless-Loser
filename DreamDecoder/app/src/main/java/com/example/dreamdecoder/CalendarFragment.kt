package com.example.dreamdecoder

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var dreamTextView: TextView
    private lateinit var editButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var buttonLayout: LinearLayout
    private lateinit var sharedPrefs: SharedPreferences

    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        dreamTextView = view.findViewById(R.id.dreamTextView)
        editButton = view.findViewById(R.id.editButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        buttonLayout = view.findViewById(R.id.buttonLayout)

        // Correct SharedPreferences usage
        sharedPrefs = requireActivity().getSharedPreferences("dreams", Context.MODE_PRIVATE)

        // Calendar date selection
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            val dream = sharedPrefs.getString(selectedDate, null)

            if (dream != null) {
                dreamTextView.text = "🌙 Dream on $selectedDate:\n$dream"
                buttonLayout.visibility = View.VISIBLE
            } else {
                dreamTextView.text = "No dream found on $selectedDate."
                buttonLayout.visibility = View.GONE
            }
        }

        // Edit button
        editButton.setOnClickListener {
            selectedDate?.let { date ->
                val oldDream = sharedPrefs.getString(date, "") ?: ""
                val input = EditText(requireContext()).apply {
                    inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    setText(oldDream)
                    setSelection(oldDream.length)
                    minLines = 3
                }

                AlertDialog.Builder(requireContext())
                    .setTitle("Edit Dream on $date")
                    .setView(input)
                    .setPositiveButton("Save") { _, _ ->
                        val updatedDream = input.text.toString().trim()
                        sharedPrefs.edit().putString(date, updatedDream).apply()
                        dreamTextView.text = "🌙 Dream on $date:\n\n$updatedDream"
                        Toast.makeText(requireContext(), "Dream updated", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }

        // Delete button
        deleteButton.setOnClickListener {
            selectedDate?.let { date ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Dream")
                    .setMessage("Are you sure you want to delete the dream on $date?")
                    .setPositiveButton("Delete") { _, _ ->
                        sharedPrefs.edit().remove(date).apply()
                        dreamTextView.text = "No dream found on $date."
                        buttonLayout.visibility = View.GONE
                        Toast.makeText(requireContext(), "Dream deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }

        return view
    }
}
