import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dessertmoodcalculator.R

class MainActivity : AppCompatActivity() {

    private lateinit var sleepSeekBar: SeekBar
    private lateinit var sleepLabel: TextView
    private lateinit var favColorInput: EditText
    private lateinit var resultText: TextView
    private lateinit var calcButton: Button
    private lateinit var infoButton: Button

    private lateinit var moodHappy: ImageView
    private lateinit var moodSleepy: ImageView
    private lateinit var moodAngry: ImageView
    private lateinit var moodExcited: ImageView
    private lateinit var moodAnxious: ImageView

    private var selectedMood: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sleepSeekBar = findViewById(R.id.sleepSeekBar)
        sleepLabel = findViewById(R.id.sleepLabel)
        favColorInput = findViewById(R.id.favColorInput)
        resultText = findViewById(R.id.resultText)
        calcButton = findViewById(R.id.calcButton)
        infoButton = findViewById(R.id.infoButton)

        moodHappy = findViewById(R.id.moodHappy)
        moodSleepy = findViewById(R.id.moodSleepy)
        moodAngry = findViewById(R.id.moodAngry)
        moodExcited = findViewById(R.id.moodExcited)
        moodAnxious = findViewById(R.id.moodAnxious)

        // Load GIFs using Glide from res/raw
        Glide.with(this).asGif().load(R.raw.ic_happy).into(moodHappy)
        Glide.with(this).asGif().load(R.raw.ic_sleepy).into(moodSleepy)
        Glide.with(this).asGif().load(R.raw.ic_angry).into(moodAngry)
        Glide.with(this).asGif().load(R.raw.ic_excited).into(moodExcited)
        Glide.with(this).asGif().load(R.raw.ic_anxious).into(moodAnxious)

        moodHappy.setOnClickListener { setMood("Happy") }
        moodSleepy.setOnClickListener { setMood("Sleepy") }
        moodAngry.setOnClickListener { setMood("Angry") }
        moodExcited.setOnClickListener { setMood("Excited") }
        moodAnxious.setOnClickListener { setMood("Anxious") }

        sleepSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (selectedMood.isNotEmpty()) {
                    sleepLabel.text = "On a scale, how much do you rate your $selectedMood today? ($progress%)"
                } else {
                    sleepLabel.text = "Scale: $progress%"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        infoButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("What is this?")
                .setMessage("This fun, fictional calculator pairs your mood, sleep %, and color with a dessert that matches your vibe.")
                .setPositiveButton("Yum!") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        calcButton.setOnClickListener {
            val sleep = sleepSeekBar.progress
            val color = favColorInput.text.toString().trim().lowercase()
            val dessert = getDessertMood(selectedMood, sleep, color)
            resultText.text = "You are: $dessert"
        }
    }

    private fun setMood(mood: String) {
        selectedMood = mood
        val progress = sleepSeekBar.progress
        Toast.makeText(this, "Mood: $mood selected", Toast.LENGTH_SHORT).show()
        sleepLabel.text = "On a scale, how much do you rate your $selectedMood today? ($progress%)"
    }

    private fun getDessertMood(mood: String, sleep: Int, color: String): String {
        return when {
            mood == "Sleepy" && color.contains("blue") -> "a sad little blueberry cheesecake 🫐"
            mood == "Happy" && sleep >= 60 -> "a joyful mango mousse 🥭"
            mood == "Angry" -> "a spicy jalapeño chocolate cake 🌶️🍫"
            mood == "Excited" && color.contains("pink") -> "a glittery strawberry cupcake with sprinkles 🧁✨"
            mood == "Anxious" && sleep < 30 -> "a melting scoop of vanilla stress swirl 🍦😰"
            else -> "a mysterious mood donut 🍩🔮"
        }
    }
}
