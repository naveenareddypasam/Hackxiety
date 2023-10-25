package com.unt.hackxiety

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ExcersiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val nextButton = findViewById<Button>(R.id.nextbutton)
        nextButton.setOnClickListener {
            val intent = Intent(this, AffirmationsActivitySOS::class.java)
            startActivity(intent)
        }
    }
}