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
        val articles[3] = {"https://www.greaterlowellpsychassoc.com/blog/when-does-anxiety-require-medical-intervention","https://www.beyondblue.org.au/mental-health/anxiety/treatments-for-anxiety/anxiety-management-strategies","https://newsinhealth.nih.gov/2016/03/understanding-anxiety-disorders"}
        val articleTile1 = findViewById<CardView>(R.id.affirmation1)
        articleTile1.setOnClickListener {
            openLinkInBrowser(articles[0)
        }
        val articleTile2 = findViewById<CardView>(R.id.affirmation2)
        articleTile2.setOnClickListener {
            openLinkInBrowser(articles[1)
        }
        val articleTile3 = findViewById<CardView>(R.id.affirmation3)
        articleTile3.setOnClickListener {
            openLinkInBrowser(articles[2])
        }
    }
    private fun openLinkInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
