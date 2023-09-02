package com.samosasolutions.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var historyTextView: TextView
    private lateinit var displayTextView: TextView

    private var currentInput = StringBuilder()
    private var result = 0.0
    private var currentOperator = ""
    private var isOperatorClicked = false

    private val numberButtonIds = listOf(
        R.id.button0, R.id.button1, R.id.button2, R.id.button3,
        R.id.button4, R.id.button5, R.id.button6, R.id.button7,
        R.id.button8, R.id.button9
    )

    private val operatorButtonIds = listOf(
        R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply,
        R.id.buttonDivide, R.id.buttonEquals
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayTextView = findViewById(R.id.display)
        historyTextView = findViewById(R.id.historyTextView)

        val numberButtons = numberButtonIds.map { findViewById<Button>(it) }
        val operatorButtons = operatorButtonIds.map { findViewById<Button>(it) }

        numberButtons.forEach { button ->
            button.setOnClickListener {
                handleNumberClick(button.text.toString())
            }
        }

        val dotButton = findViewById<Button>(R.id.buttonDecimal)
        dotButton.setOnClickListener {
            handleDotClick()
        }

        val clearButton = findViewById<Button>(R.id.buttonAC)
        clearButton.setOnClickListener {
            handleClearClick()
        }

        operatorButtons.forEach { button ->
            button.setOnClickListener {
                when (button.id) {
                    R.id.buttonAdd -> handleAddition()
                    R.id.buttonSubtract -> handleSubtraction()
                    R.id.buttonMultiply -> handleMultiplication()
                    R.id.buttonDivide -> handleDivision()
                    R.id.buttonEquals -> handleEquals()
                    R.id.buttonAC -> handleClear()
                    R.id.buttonC -> handleAllClear()
                }
            }
        }
    }

    private fun handleNumberClick(buttonId: String) {
        val number = buttonId

        // Check if the current input is too long
        if (currentInput.length >= 9) {
            val scientificNotation = currentInput.toString().toDouble().toString()
            currentInput = StringBuilder(scientificNotation)
        }

        currentInput.append(number)
        displayTextView.text = currentInput.toString()
        isOperatorClicked = false
    }

    private fun handleDotClick() {
        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) {
                currentInput.append("0")
            }
            currentInput.append(".")
            displayTextView.text = currentInput.toString()
        }
    }

    private fun performCalculation(secondOperand: Double) {
        when (currentOperator) {
            "+" -> result += secondOperand
            "-" -> result -= secondOperand
            "*" -> result *= secondOperand
            "/" -> {
                if (secondOperand != 0.0) {
                    result /= secondOperand
                } else {
                    displayTextView.text = "Error"
                    return
                }
            }
        }
        currentInput = StringBuilder(result.toString())
        displayTextView.text = currentInput.toString()
        historyTextView.text = "$result $currentOperator $secondOperand"
    }

    private fun handleEqualsClick() {
        if (!currentOperator.isEmpty()) {
            val secondOperand = currentInput.toString().toDouble()
            if (currentOperator == "/" && secondOperand == 0.0) {
                displayTextView.text = "Error"
                return
            }
            performCalculation(secondOperand)
            currentOperator = ""
            isOperatorClicked = false
        }
    }

    private fun handleClearClick() {
        currentInput = StringBuilder()
        result = 0.0
        currentOperator = ""
        displayTextView.text = "0"
        historyTextView.text = ""
        isOperatorClicked = false
    }

    private fun handleAddition() {
        if (!isOperatorClicked) {
            val currentNumber = currentInput.toString().toDouble()
            historyTextView.text = "$currentNumber + "
            result += currentNumber
            currentInput = StringBuilder()
            displayTextView.text = "0"
            isOperatorClicked = true
            currentOperator = "+"
        }
    }

    private fun handleSubtraction() {
        if (!isOperatorClicked) {
            val currentNumber = currentInput.toString().toDouble()
            historyTextView.text = "$currentNumber - "
            result = currentNumber
            currentInput = StringBuilder()
            displayTextView.text = "0"
            isOperatorClicked = true
            currentOperator = "-"
        }
    }

    private fun handleMultiplication() {
        if (!isOperatorClicked) {
            val currentNumber = currentInput.toString().toDouble()
            historyTextView.text = "$currentNumber * "
            result = currentNumber
            currentInput = StringBuilder()
            displayTextView.text = "0"
            isOperatorClicked = true
            currentOperator = "*"
        }
    }

    private fun handleDivision() {
        if (!isOperatorClicked) {
            val currentNumber = currentInput.toString().toDouble()
            historyTextView.text = "$currentNumber / "
            result = currentNumber
            currentInput = StringBuilder()
            displayTextView.text = "0"
            isOperatorClicked = true
            currentOperator = "/"
        }
    }

    private fun handleEquals() {
        if (!currentOperator.isEmpty()) {
            val secondOperand = currentInput.toString().toDouble()
            when (currentOperator) {
                "+" -> {
                    result += secondOperand
                    historyTextView.text = "$result"
                }
                "-" -> {
                    result -= secondOperand
                    historyTextView.text = "$result"
                }
                "*" -> {
                    result *= secondOperand
                    historyTextView.text = "$result"
                }
                "/" -> {
                    if (secondOperand != 0.0) {
                        result /= secondOperand
                        historyTextView.text = "$result"
                    } else {
                        displayTextView.text = "Error"
                    }
                }
            }
            currentInput = StringBuilder(result.toString())
            displayTextView.text = currentInput.toString()
            currentOperator = ""
            isOperatorClicked = false
        }
    }

    private fun handleClear() {
        currentInput = StringBuilder()
        result = 0.0
        currentOperator = ""
        displayTextView.text = "0"
        historyTextView.text = ""
        isOperatorClicked = false
    }

    private fun handleDecimal() {
        if (!currentInput.toString().contains(".")) {
            currentInput.append(".")
            displayTextView.text = currentInput.toString()
            isOperatorClicked = false
        }
    }

    private fun handleAllClear() {
        currentInput = StringBuilder()
        result = 0.0
        currentOperator = ""
        displayTextView.text = "0"
        historyTextView.text = ""
        isOperatorClicked = false
    }

    private fun handlePlusMinus() {
        if (currentInput.isNotEmpty()) {
            val currentValue = currentInput.toString().toDouble()
            currentInput = StringBuilder((-currentValue).toString())
            displayTextView.text = currentInput.toString()
        }
    }

}




