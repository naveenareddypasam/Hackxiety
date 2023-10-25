package com.unt.hackxiety

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


class AffirmationsAcitvity : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affirmations)
        val exitButton = findViewById<ImageButton>(R.id.exitButton)
        exitButton.setOnClickListener {
            finish()
        }

    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

}