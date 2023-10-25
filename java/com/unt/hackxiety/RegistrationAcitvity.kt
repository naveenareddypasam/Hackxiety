package com.unt.hackxiety

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unt.hackxiety.login.LoginActivity

class RegistrationActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)

        // Create a SpannableString with "Already have an account? - Log in"
        val spannableString = SpannableString("Already have an account? - Log in")

        // Create a ClickableSpan for "Log in" part
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle the click event and navigate to the login activity
                val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        // Set the ClickableSpan for "Log in" and make it clickable
        spannableString.setSpan(clickableSpan, 27 ,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textViewLogin.text = spannableString
        textViewLogin.movementMethod = LinkMovementMethod.getInstance()

        // Set a click listener for the registration button
        buttonRegister.setOnClickListener {
            // Get user input
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Perform registration logic (replace with your own logic)
            if (registerUser(name, email, password)) {
                // Registration successful
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                // You can navigate to the next activity or perform other actions here.
                val intent = Intent(this@RegistrationActivity, HomeActivity::class.java)
                startActivity(intent)

            } else {
                // Registration failed
                Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Replace this with your registration logic
    private fun registerUser(name: String, email: String, password: String): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("firstTime", false)
        editor.putBoolean("loggedIn",true)
        editor.putString("reguser",name)
        editor.putString("regpass",password)
        editor.putString("email",email)
        editor.apply()

        return true
    }
    fun openLoginActivity(view: View) {
        // Handle the click event and navigate to the login activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
