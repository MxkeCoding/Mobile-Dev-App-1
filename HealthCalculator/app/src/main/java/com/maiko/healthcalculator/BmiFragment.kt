package com.maiko.healthcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class BmiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weightInput = view.findViewById<EditText>(R.id.weightInput)
        val heightInput = view.findViewById<EditText>(R.id.heightInput)
        val calculateButton = view.findViewById<Button>(R.id.calculateBMIButton)
        val resultText = view.findViewById<TextView>(R.id.bmiResult)

        calculateButton.setOnClickListener {
            val weight = weightInput.text.toString().toFloatOrNull()
            val height = heightInput.text.toString().toFloatOrNull()

            if (weight != null && height != null && height > 0) {
                val heightInMeters = height / 100
                val bmi = weight / (heightInMeters * heightInMeters)
                val bmiCategory = when {
                    bmi < 18.5 -> "Underweight"
                    bmi < 24.9 -> "Normal weight"
                    bmi < 29.9 -> "Overweight"
                    else -> "Obese"
                }
                resultText.text = "BMI: %.2f\nCategory: %s".format(bmi, bmiCategory)
            } else {
                resultText.text = "Please enter valid weight and height."
            }
        }
    }
}
