package com.unt.hackxiety

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class EduActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edu)
        val exitButton = findViewById<ImageButton>(R.id.exitButton)
        exitButton.setOnClickListener {
            val intent = Intent(this@EduActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        val affirmation1 = findViewById<CardView>(R.id.affirmation1)
        affirmation1.setOnClickListener {
            openLinkInBrowser("https://www.greaterlowellpsychassoc.com/blog/when-does-anxiety-require-medical-intervention")
        }
        val affirmation2 = findViewById<CardView>(R.id.affirmation2)
        affirmation2.setOnClickListener {
            openLinkInBrowser("https://www.beyondblue.org.au/mental-health/anxiety/treatments-for-anxiety/anxiety-management-strategies")
        }
        val affirmation3 = findViewById<CardView>(R.id.affirmation3)
        affirmation3.setOnClickListener {
            openLinkInBrowser("https://newsinhealth.nih.gov/2016/03/understanding-anxiety-disorders")
        }
    }
    private fun openLinkInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}