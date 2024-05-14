package com.example.firstassignment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

class signUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup) // Set the layout for this activity

        val nameEditText = findViewById<EditText>(R.id.editText1)

        nameEditText.filters =
            arrayOf<InputFilter>(InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.isLetter(source[i])) {
                        Toast.makeText(
                            this,
                            "Please enter only alphabet characters for name.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@InputFilter ""
                    }
                }
                null
            })
        val mobileNumberEditText = findViewById<EditText>(R.id.editText3)
        mobileNumberEditText.filters = arrayOf<InputFilter>(
            InputFilter.LengthFilter(10),
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.isDigit(source[i])) {
                        Toast.makeText(
                            this,
                            "Please enter only numeric characters for mobile number.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@InputFilter ""
                    }
                }
                null
            })

        mobileNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length > 10) {
                    Toast.makeText(
                        this@signUpActivity,
                        "Mobile number cannot exceed 10 characters.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })

        val passwordEditText = findViewById<EditText>(R.id.editText4)
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val password = s.toString()
                val specialCharPattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
                val alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9]*$")
                val specialCharMatcher = specialCharPattern.matcher(password)
                val alphaNumericMatcher = alphaNumericPattern.matcher(password)

                if (!alphaNumericMatcher.find() || specialCharMatcher.groupCount() != 1) {
                    Toast.makeText(
                        this@signUpActivity,
                        "Password must be alphanumeric and contain exactly one special character.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        })
        val reEnterPasswordEditText = findViewById<EditText>(R.id.editText5)
        val submitButton = findViewById<Button>(R.id.button2)
        submitButton.setOnClickListener {
            val password = passwordEditText.text.toString()
            val reEnterPassword = reEnterPasswordEditText.text.toString()

            if (password != reEnterPassword) {
                Toast.makeText(
                    this,
                    "Password and Re-enter password fields must be the same.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val username = nameEditText.text.toString()
                val password = passwordEditText.text.toString()
                val mobile = mobileNumberEditText.text.toString()
                val emailEditText = findViewById<EditText>(R.id.editText2)
                val email = emailEditText.text.toString()

                //using  sqllite database to store the data of the user
//                    val databaseHelper = DatabaseHelper(this)
//                    databaseHelper.addUser(username, password, mobile, email)
                // Store user data in Shared Preferences


                // Store user data in Shared Preferences
                val sharedPreferences =
                    getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putString("mobile", mobile)
                editor.putString("email", email)
                editor.apply()
                Toast.makeText(this, "User registered successfully.", Toast.LENGTH_SHORT).show()
            }
        }


        // signIn textview click event
        val signInTextView: TextView = findViewById(R.id.textView7)
        signInTextView.setOnClickListener {
            finish()
        }

    }


}