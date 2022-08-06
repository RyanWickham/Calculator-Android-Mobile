package com.gmail.ryanwickham259.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var previousInputWasNumeric: Boolean = false
    private var decimalPointInUse: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById<TextView>(R.id.tvInput)
    }

    fun onDigitButtonClick(view: View){
        tvInput?.append((view as Button).text)

        previousInputWasNumeric = true
    }

    fun onClear(view: View){
        tvInput?.text = ""

        previousInputWasNumeric = false
        decimalPointInUse = false
    }

    fun onDecimalPoint(view: View){
        if(previousInputWasNumeric && !decimalPointInUse){
            tvInput?.append(".")

            previousInputWasNumeric = false
            decimalPointInUse = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(previousInputWasNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
            }

            previousInputWasNumeric = false
            decimalPointInUse = false
        }
    }

    fun onEqual(view: View){
        if(previousInputWasNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            var result = ""

            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    result = (one.toDouble() - two.toDouble()).toString()
                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    result = (one.toDouble() + two.toDouble()).toString()
                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    result = (one.toDouble() / two.toDouble()).toString()
                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    result = (one.toDouble() * two.toDouble()).toString()
                }

                tvInput?.text = removeZeroAfterDot(result)
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result

        if(result.endsWith(".0")){
            value = result.substring(0, result.length-2)
        }

        return value
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            return value.contains("/")
                    ||value.contains("*")
                    ||value.contains("+")
                    ||value.contains("-")
        }
    }
}