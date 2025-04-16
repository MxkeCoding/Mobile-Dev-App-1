package com.maiko.healthcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class CalorieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calorie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ageInput = view.findViewById<EditText>(R.id.ageInput)
        val weightInput = view.findViewById<EditText>(R.id.weightInputCalorie)
        val heightInput = view.findViewById<EditText>(R.id.heightInputCalorie)
        val genderGroup = view.findViewById<RadioGroup>(R.id.genderGroup)
        val activityLevelGroup = view.findViewById<RadioGroup>(R.id.activityLevelGroup)
        val calculateButton = view.findViewById<Button>(R.id.calculateCalorieButton)
        val resultText = view.findViewById<TextView>(R.id.calorieResult)

        calculateButton.setOnClickListener {
            val age = ageInput.text.toString().toIntOrNull()
            val weight = weightInput.text.toString().toDoubleOrNull()
            val height = heightInput.text.toString().toDoubleOrNull()

            if (age == null || weight == null || height == null) {
                resultText.text = "Please fill in age, height, and weight correctly."
                return@setOnClickListener
            }

            val gender = when (genderGroup.checkedRadioButtonId) {
                R.id.radioMale -> "male"
                R.id.radioFemale -> "female"
                else -> ""
            }

            val activityLevel = when (activityLevelGroup.checkedRadioButtonId) {
                R.id.sedentaryButton -> "sedentary"
                R.id.lightButton -> "light"
                R.id.moderateButton -> "moderate"
                R.id.veryActiveButton -> "very"
                else -> ""
            }

            if (gender.isEmpty() || activityLevel.isEmpty()) {
                resultText.text = "Please select gender and activity level."
                return@setOnClickListener
            }

            val calories = calculateCalories(weight, height, age, gender, activityLevel)
            resultText.text = "Estimated daily calories: $calories kcal"
        }
    }

    private fun calculateCalories(weight: Double, height: Double, age: Int, gender: String, activityLevel: String): Int {
        // BMR Calculation using Mifflin-St Jeor Equation
        val bmr = if (gender == "male") {
            10 * weight + 6.25 * height - 5 * age + 5
        } else {
            10 * weight + 6.25 * height - 5 * age - 161
        }

        val multiplier = when (activityLevel) {
            "sedentary" -> 1.2
            "light" -> 1.375
            "moderate" -> 1.55
            "very" -> 1.725
            else -> 1.0
        }

        return (bmr * multiplier).toInt()
    }
}
