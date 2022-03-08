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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        if (isResult)
            onClear(view)
        isResult = false

        binding.tvInput.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
        isResult = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded((it.toString()))) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
                isResult = false
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            var splitValue: List<String> = listOf()
            var operation = 0

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    splitValue = tvValue.split("-")
                    operation = 1
                } else if (tvValue.contains("+")) {
                    splitValue = tvValue.split("+")
                    operation = 2
                } else if (tvValue.contains("/")) {
                    splitValue = tvValue.split("/")
                    operation = 3
                } else if (tvValue.contains("*")) {
                    splitValue = tvValue.split("*")
                    operation = 4
                }

                var one = splitValue[0]
                var two = splitValue[1]

                if (prefix.isNotEmpty())
                    one = prefix + one

                when (operation) {
                    1 -> tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    2 -> tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    3 -> tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    4 -> tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
            isResult = true
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
}