package com.unt.hackxiety

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SOSAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos)
        val exitButton = findViewById<ImageButton>(R.id.exitButton)
        exitButton.setOnClickListener {
            finish()
        }
        val beginButton = findViewById<Button>(R.id.begin)
        beginButton.setOnClickListener {
            val intent = Intent(this, GroundingMessageActivity::class.java)
            startActivity(intent)
        }



    }


}