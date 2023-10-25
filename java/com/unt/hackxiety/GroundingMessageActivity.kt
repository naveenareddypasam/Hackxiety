package com.unt.hackxiety

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GroundingMessageActivity : AppCompatActivity(){
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grounding)
        val exitButton = findViewById<ImageButton>(R.id.exitButton)
        exitButton.setOnClickListener {
            finish()
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.calmingmessage1)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release() // Release the MediaPlayer
            val intent = Intent(this, ExcersiceActivity::class.java)
            startActivity(intent)
            finish() // Finish this activity to prevent going back to it
        }
        val skipText = findViewById<TextView>(R.id.skipText)
        skipText.text = getUnderlinedText("Skip")
        skipText.setOnClickListener {
            // Handle the click event here
            onSkipClick()
        }
    }
    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
    fun onSkipClick() {
        mediaPlayer?.release() // Release the MediaPlayer
        val intent = Intent(this, ExcersiceActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun getUnderlinedText(text: String): Spannable {
        val spannableString = SpannableString(text)
        spannableString.setSpan(UnderlineSpan(), 0, text.length, 0)
        return spannableString
    }
}