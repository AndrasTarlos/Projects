package it.andrew.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import it.andrew.calculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    var isResult: Boolean = false
    private var noText: Boolean = true
    var firstValueNeg: Boolean = false
    var zeroDivisionError = false
    var dotInLastNum = false

    var operatorList = listOf<Char>().toMutableList()
    var numberList = listOf<String>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        if (tvInput?.text.toString() == "Divided by zero")
            onClear(view)
        if (isResult)
            onClear(view)
        isResult = false

        binding.tvInput.append((view as Button).text)
        lastNumeric = true
        lastDot = false
        noText = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
        isResult = false
        noText = true
        firstValueNeg = false
        dotInLastNum = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot && !dotInLastNum) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
            dotInLastNum = true
        }
    }

    fun onOperator(view: View) {
        if (tvInput?.text.toString() == "Divided by zero")
            onClear(view)
        var operator = (view as Button).text

        if (noText && operator.equals("-")) {

            tvInput?.text = operator
            noText = false
            lastNumeric = false
            firstValueNeg = true
        }

        tvInput?.text?.let {
            if (lastNumeric) {
                tvInput?.append(operator)
                lastNumeric = false
                lastDot = false
                dotInLastNum = false
                isResult = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun onBackspace(view: View) {
        if (tvInput?.text.toString() == "Divided by zero")
            onClear(view)

        var text = tvInput?.text.toString()
        tvInput?.text = if (text.isNotEmpty()) text.dropLast(1) else ""

        if (text.length == 1) {
            lastNumeric = false
            lastDot = false
            dotInLastNum = false
            isResult = false
            noText = true
            firstValueNeg = false
        }

    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()

            // Remove first minus
            if (tvValue[0] == '-') {
                firstValueNeg = true
                tvValue = tvValue.drop(1)
            }

            // If the value has no operations, it has to stay the same
            if (!isOperatorIn(tvValue)) {
                if (firstValueNeg) {
                    tvInput?.text = "-$tvValue"
                } else {
                    tvInput?.text = tvValue
                }
            } else {
                // Create a list of operators and numbers
                var number: StringBuilder = java.lang.StringBuilder()
                for (c in tvValue) {
                    number.append(c)
                    if (c == '+' || c == '-' || c == '/' || c == '*') {
                        operatorList.add(c)
                        numberList.add(number.toString().dropLast(1))
                        number.clear()
                    }
                }
                numberList.add(number.toString())

                try {
                    while (numberList.size != 1 && numberList.size != 0) {
                        calculate()
                    }
                    if (zeroDivisionError) {
                        tvInput?.text = "Divided by zero"
                        operatorList.clear()
                    } else {
                        if (numberList[0].contains(".") && numberList[0].length - numberList[0].indexOf(".") > 6) {
                            tvInput?.text = numberList[0].subSequence(0, numberList[0].indexOf(".") + 6)
                        } else
                            tvInput?.text = numberList[0]
                    }

                    numberList.clear()
                    operatorList.clear()

                } catch (e: ArithmeticException) {
                    e.printStackTrace()
                }
            }
            isResult = true
            firstValueNeg = false
            dotInLastNum = true
        }
    }

    @SuppressLint("SetTextI18n")
    fun calculate() {
        zeroDivisionError = false
        loop@ for (i in 0 until operatorList.size) {
            var zero = "0.0"
            for (k in 1..80) {
                if (operatorList[i] == '/' && numberList[i+1] == "0" || numberList[i+1] == zero) {
                    zeroDivisionError = true
                    numberList.clear()
                    break@loop
                }
                zero += "0"
                println(zero)
            }

            // Stops program if a zero division error occurs
            if (operatorList[i] == '/' || operatorList[i] == '*') {
                if (firstValueNeg && i == 0) {
                    when (operatorList[i]) {
                        '/' -> numberList[i] =
                            (-numberList[i].toDouble() / numberList[i + 1].toDouble()).toString()
                        '*' -> numberList[i] =
                            (-numberList[i].toDouble() * numberList[i + 1].toDouble()).toString()
                    }
                    numberList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break

                } else {
                    when (operatorList[i]) {
                        '/' -> numberList[i] =
                            (numberList[i].toDouble() / numberList[i + 1].toDouble()).toString()
                        '*' -> numberList[i] =
                            (numberList[i].toDouble() * numberList[i + 1].toDouble()).toString()
                    }
                    numberList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break
                }
            }
        }

        for (i in 0 until operatorList.size) {
            if (operatorList[i] == '+' || operatorList[i] == '-') {
                if (firstValueNeg && i == 0) {
                    when (operatorList[i]) {
                        '+' -> numberList[i] = (-numberList[i].toDouble() + numberList[i + 1].toDouble()).toString()
                        '-' -> numberList[i] = (-numberList[i].toDouble() - numberList[i + 1].toDouble()).toString()
                    }
                    numberList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break

                } else {
                    when (operatorList[i]) {
                        '+' -> numberList[i] = (numberList[i].toDouble() + numberList[i + 1].toDouble()).toString()
                        '-' -> numberList[i] = (numberList[i].toDouble() - numberList[i + 1].toDouble()).toString()
                    }
                    numberList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break
                }
            }
        }
    }

    fun isOperatorIn(input: String): Boolean {
        var inputText: String = input
        if (input[0] == '-')
            inputText = input.drop(1)

        return inputText.contains("+") || inputText.contains("-") || inputText.contains("/") || inputText.contains("*")
    }

}
