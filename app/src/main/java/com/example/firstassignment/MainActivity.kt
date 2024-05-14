package com.example.firstassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val signUpTextView: TextView = findViewById(R.id.textView5)

        val usernameEditText = findViewById<EditText>(R.id.editText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.button)
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            //retreving data using  database helper class
//            val databaseHelper = DatabaseHelper(this)
//            when (databaseHelper.checkUser(username, password)) {
//                0 -> Toast.makeText(this, "This username does not exist, sign up to create a new user", Toast.LENGTH_SHORT).show()
//                1 -> {
//                    val intent = Intent(this, homeActivity::class.java)
//                    startActivity(intent)
//                }
//                2 -> Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show()
//            }

            // retreving data using shared preferences
            val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val savedUsername = sharedPreferences.getString("username", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (username == savedUsername && password == savedPassword) {
                val intent = Intent(this, homeActivity::class.java)
                startActivity(intent)
            } else {
                // show error message
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
        signUpTextView.setOnClickListener {
            val intent = Intent(this, signUpActivity::class.java)
            startActivity(intent)
        }


    }
}