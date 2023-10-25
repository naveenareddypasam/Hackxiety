
package com.unt.hackxiety

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AffirmationsActivitySOS : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affirmations_sos)
        val nextButton = findViewById<Button>(R.id.nextbutton)
        nextButton.setOnClickListener {
            val intent = Intent(this, EmergencyContactActivity::class.java)
            startActivity(intent)
        }
        val exitButton = findViewById<ImageButton>(R.id.exitButton)
        exitButton.setOnClickListener {
            val intent = Intent(this@AffirmationsActivitySOS, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}