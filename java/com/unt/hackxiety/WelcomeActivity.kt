package com.unt.hackxiety

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.unt.hackxiety.login.LoginActivity
import com.unt.hackxiety.ui.theme.HackxietyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val firstTime = sharedPreferences.getBoolean("firstTime", true)


        // Apply the changes
                editor.apply()
        if(firstTime) {
            setContentView(R.layout.`activity_welcome`) // Set the layout

            val backgroundImageView: ImageView = findViewById(R.id.imageView4)
            backgroundImageView.setImageResource(R.drawable.anxiety1)
            val getStartedButton = findViewById<Button>(R.id.getStartedButton)
            getStartedButton.setOnClickListener {

                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
            }
        }
        else{
            var loggedIn = sharedPreferences.getBoolean("loggedIn", false)
            if(loggedIn) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

