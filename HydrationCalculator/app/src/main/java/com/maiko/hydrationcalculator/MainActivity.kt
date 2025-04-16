package com.maiko.hydrationcalculator

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var ageInput: EditText
    private lateinit var weightInput: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var calculateButton: Button
    private lateinit var resultText: TextView
    private lateinit var helpButton: Button
    private lateinit var preferencesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        ageInput = findViewById(R.id.ageInput)
        weightInput = findViewById(R.id.weightInput)
        genderGroup = findViewById(R.id.genderGroup)
        calculateButton = findViewById(R.id.calculateButton)
        resultText = findViewById(R.id.resultText)
        helpButton = findViewById(R.id.btnHelp)
        preferencesButton = findViewById(R.id.btnPreferences)

        calculateButton.setOnClickListener {
            calculateWaterIntake()
        }

        helpButton.setOnClickListener {
            showInstructionsDialog()
        }

        preferencesButton.setOnClickListener {
            showPreferencesDialog()
        }
    }

    private fun calculateWaterIntake() {
        val age = ageInput.text.toString().toIntOrNull()
        val weight = weightInput.text.toString().toDoubleOrNull()
        val selectedGenderId = genderGroup.checkedRadioButtonId

        if (age == null || weight == null || selectedGenderId == -1) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val gender = when (selectedGenderId) {
            R.id.radioMale -> "Male"
            R.id.radioFemale -> "Female"
            else -> ""
        }

        // Base water amount in ml per kg
        val baseMlPerKg = when (gender.lowercase()) {
            "male" -> 35
            "female" -> 31
            else -> 33
        }

        var waterMl = weight * baseMlPerKg

        // Adjust for age
        if (age < 14) {
            waterMl *= 0.9
        } else if (age > 55) {
            waterMl *= 1.1
        }

        val waterLiters = waterMl / 1000
        resultText.text = "Recommended Daily Water Intake: %.2f liters".format(waterLiters)
    }

    private fun showInstructionsDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_instructions, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

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

    private fun showPreferencesDialog() {
        val dialogView = layoutInflater.inflate(R.layout.preferences_dialog, null)
        val checkbox = dialogView.findViewById<CheckBox>(R.id.reminderCheckbox)
        val customGoalInput = dialogView.findViewById<EditText>(R.id.customGoalInput)

        val prefs = getSharedPreferences("HydrationPrefs", MODE_PRIVATE)

        // Retrieve stored value in milliliters and convert to liters
        val storedMl = prefs.getInt("custom_goal", 0)
        customGoalInput.setText((storedMl / 1000.0).toString())

        checkbox.isChecked = prefs.getBoolean("reminder_enabled", false)

        AlertDialog.Builder(this)
            .setTitle("Preferences")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val reminderEnabled = checkbox.isChecked
                val customGoalLiters = customGoalInput.text.toString().toDoubleOrNull() ?: 0.0
                val customGoalMl = (customGoalLiters * 1000).toInt()

                prefs.edit()
                    .putBoolean("reminder_enabled", reminderEnabled)
                    .putInt("custom_goal", customGoalMl) // still store in mL internally
                    .apply()

                Toast.makeText(this, "Preferences saved!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}
