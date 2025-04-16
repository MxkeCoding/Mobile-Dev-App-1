package com.maiko.healthcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.Gravity
import android.view.WindowManager


class MainActivity : AppCompatActivity() {

    private lateinit var bmiButton: Button
    private lateinit var calorieButton: Button
    private lateinit var helpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find buttons
        bmiButton = findViewById(R.id.btnBmi)
        calorieButton = findViewById(R.id.btnCalorie)
        helpButton = findViewById(R.id.btnHelp)

        // Show BMI fragment by default
        replaceFragment(BmiFragment())

        // Button click listeners
        bmiButton.setOnClickListener {
            replaceFragment(BmiFragment())
        }

        calorieButton.setOnClickListener {
            replaceFragment(CalorieFragment())
        }

        helpButton.setOnClickListener {
            showInstructionsDialog()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun showInstructionsDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_instructions, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        // Center the dialog
        val window = alertDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)

        val closeButton = dialogView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

}
