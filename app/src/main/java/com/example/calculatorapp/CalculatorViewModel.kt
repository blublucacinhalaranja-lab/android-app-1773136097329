package com.example.calculatorapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class CalculatorViewModel : ViewModel() {

    private val _result = MutableLiveData<String>("0")
    val result: LiveData<String> = _result

    private var operand1: Double? = null
    private var operator: String? = null
    private var currentInput: String = ""

    fun onDigitClicked(digit: String) {
        if (currentInput == "0") {
            currentInput = digit
        } else {
            currentInput += digit
        }
        _result.value = currentInput
    }

    fun onOperatorClicked(op: String) {
        operand1 = currentInput.toDoubleOrNull()
        if (operand1 != null) {
            operator = op
            currentInput = ""
        }
    }

    fun onDecimalClicked() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            _result.value = currentInput
        }
    }

    fun onClearClicked() {
        currentInput = ""
        operand1 = null
        operator = null
        _result.value = "0"
    }

    fun onEqualsClicked() {
        val operand2 = currentInput.toDoubleOrNull()
        if (operand1 != null && operand2 != null && operator != null) {
            val resultValue = when (operator) {
                "+" -> operand1!! + operand2
                "-" -> operand1!! - operand2
                "*" -> operand1!! * operand2
                "/" -> if (operand2 != 0.0) operand1!! / operand2 else 0.0
                else -> 0.0
            }

            currentInput = formatResult(resultValue)
            _result.value = currentInput
            operand1 = null
            operator = null
        }
    }

    fun onPercentClicked() {
        val currentValue = currentInput.toDoubleOrNull()
        if (currentValue != null) {
            currentInput = formatResult(currentValue / 100.0)
            _result.value = currentInput
        }
    }

    fun onPlusMinusClicked() {
        val currentValue = currentInput.toDoubleOrNull()
        if (currentValue != null) {
            currentInput = formatResult(-currentValue)
            _result.value = currentInput
        }
    }

    private fun formatResult(value: Double): String {
        val df = DecimalFormat("#.######")
        return df.format(value)
    }
}