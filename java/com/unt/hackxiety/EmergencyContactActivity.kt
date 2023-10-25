
package com.unt.hackxiety

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class EmergencyContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergencycontact)
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val exitButton = findViewById<ImageButton>(R.id.exitButton)
        exitButton.setOnClickListener {
            val intent = Intent(this@EmergencyContactActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        val nextButton = findViewById<Button>(R.id.callButton)
        nextButton.setOnClickListener {
            val phoneNumber = sharedPreferences.getString("em1",sharedPreferences.getString("em2","911"))
            if(phoneNumber.equals("911")){
                Toast.makeText(this, "No emergency contact available, calling 911", Toast.LENGTH_SHORT).show()

            }
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        }
    }
}