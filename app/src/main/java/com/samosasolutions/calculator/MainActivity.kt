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
                handleOperatorClick(button.text.toString())
            }
        }
    }

    private fun handleNumberClick(buttonId: String) {
        val number = buttonId
        currentInput.append(number.toString())
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

    private fun handleOperatorClick(operator: String) {
        if (!isOperatorClicked) {
            if (currentOperator.isNotEmpty()) {
                val secondOperand = currentInput.toString().toDouble()
                performCalculation(secondOperand)
            } else {
                result = currentInput.toString().toDouble()
            }

            currentInput = StringBuilder()
            currentOperator = operator
            isOperatorClicked = true
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
}




