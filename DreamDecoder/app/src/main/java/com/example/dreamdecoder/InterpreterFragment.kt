package com.example.dreamdecoder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class InterpreterFragment : Fragment() {

    private lateinit var dreamKeywordInput: EditText
    private lateinit var interpretButton: ImageButton
    private lateinit var interpretationResult: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var openInBrowserButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_interpreter, container, false)

        // Initialize views
        dreamKeywordInput = view.findViewById(R.id.dreamKeywordInput)
        interpretButton = view.findViewById(R.id.interpretButton)
        interpretationResult = view.findViewById(R.id.interpretationResult)
        progressBar = view.findViewById(R.id.progressBar)
        openInBrowserButton = view.findViewById(R.id.openInBrowserButton)

        // Initial visibility
        progressBar.visibility = View.GONE
        openInBrowserButton.visibility = View.GONE

        // Handle interpretation logic
        interpretButton.setOnClickListener {
            val keyword = dreamKeywordInput.text.toString().trim()
            if (keyword.isNotEmpty()) {
                interpretationResult.text = ""
                openInBrowserButton.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

                view.postDelayed({
                    progressBar.visibility = View.GONE
                    interpretationResult.text = getLocalInterpretation(keyword)
                    openInBrowserButton.visibility = View.VISIBLE
                }, 800)
            } else {
                interpretationResult.text = "Please enter a dream symbol or keyword."
                openInBrowserButton.visibility = View.GONE
            }
        }

        // Open interpretation in browser
        openInBrowserButton.setOnClickListener {
            val keyword = dreamKeywordInput.text.toString().trim().lowercase().replace(" ", "-")
            val url = "https://www.dreamdictionary.org/?s=$keyword/"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        // Optional: Show a toast when long-pressing the browser button
        openInBrowserButton.setOnLongClickListener {
            Toast.makeText(requireContext(), "View full interpretation in browser", Toast.LENGTH_SHORT).show()
            true
        }

        return view
    }

    // Interpret local meanings for common symbols
    private fun getLocalInterpretation(keyword: String): String {
        return when (keyword.lowercase()) {
            "water" -> "Water often symbolizes emotions, purification, or change. It may reflect emotional depth, subconscious insights, or life transitions depending on whether the water is calm, stormy, or still."
            "falling" -> "Falling may indicate insecurity, fear of failure, or loss of control. It can reflect anxiety about letting go, feeling unsupported, or experiencing vulnerability in life situations."
            "snake" -> "Snakes can represent transformation, fear, or hidden threats. They may also symbolize healing, rebirth, or sexual energy depending on the dream’s emotional tone and context."
            "flying" -> "Flying often reflects freedom, ambition, or escape from limitations. It may symbolize rising above obstacles, gaining a higher perspective, or pursuing dreams with confidence."
            "teeth falling out" -> "This dream is commonly linked to anxiety about appearance, aging, or fear of helplessness. It may also relate to communication challenges or fear of losing power or control."
            "being chased" -> "Being chased in a dream may represent avoidance, unresolved stress, or feelings of pressure. It often points to an issue or emotion the dreamer is unwilling to confront."
            "death" -> "Dreaming about death often signifies transformation, endings, or major life changes—not literal death. It can also represent fears, transitions, or emotional closure."
            else -> "Symbolic interpretation for \"$keyword\": This dream may reflect subconscious thoughts or feelings related to that concept. For a deeper meaning, you can also view interpretations online."
        }
    }
}

