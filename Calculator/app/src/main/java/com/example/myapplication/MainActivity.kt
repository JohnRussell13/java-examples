package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.lang.Exception
import kotlin.math.*
import kotlinx.android.synthetic.main.activity_adv.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var valueOrder = 0
    private var canAddDecimal = true
    private var resetX = false
    private var X = ""
    private var Y = ""
    private var Z = ""
    private var W = ""
    private var switchLayout = true
    private var screenTextW = ""
    private var screenTextR = ""
    private var operationVal = ""

    private var memory = 0.0
    private var memoryFl = false

    fun numberAction(view: View){
        if(view is Button){
            if(view.text == "."){
                if(canAddDecimal){
                    if(valueOrder == 0){
                        if(resetX){
                            X = ""
                            resultsTV.text = ""
                            resetX = false
                        }
                        X += view.text.toString()
                    }
                    else if(valueOrder == 1) {
                        Y += view.text.toString()
                    }
                    resultsTV.append(view.text)
                }
                canAddDecimal = false
            }
            else{
                if(valueOrder == 0){
                    if(resetX){
                        X = ""
                        resultsTV.text = ""
                        resetX = false
                    }
                    X += view.text.toString()
                }
                else if(valueOrder == 1){
                    Y += view.text.toString()
                }
                resultsTV.append(view.text)
            }
        }
    }
    fun operationAction(view: View){
        if(view is Button && valueOrder == 0){
            if(X == ""){
                X = "0"
            }
            operationVal = view.text.toString()
            resultsTV.append(view.text)
            canAddDecimal = true
            valueOrder++
        }
    }
    fun equalsAction(view: View){
        if(valueOrder == 0){
            return
        }
        try {
            Z = if (operationVal == "+") {
                (X.toDouble() + Y.toDouble()).rounder().toString()
            } else if (operationVal == "-") {
                (X.toDouble() - Y.toDouble()).rounder().toString()
            } else if (operationVal == "*") {
                (X.toDouble() * Y.toDouble()).rounder().toString()
            } else if (operationVal == "/" && Y.toDouble() != 0.0) {
                (X.toDouble() / Y.toDouble()).rounder().toString()
            } else {
                return
            }
        } catch (e: Exception){
            return
        }

        workingsTV.text = resultsTV.text
        resultsTV.text = Z
        X = Z
        Y = ""
        resetX = true
        canAddDecimal = true
        valueOrder = 0
        operationVal = ""
    }
    fun unaryAction(view: View){
        if(view is Button){
            if(valueOrder == 0){
                if(X == ""){
                    X = "0"
                }
                try{
                    if(view.text == "sqrt" && X.toDouble() >= 0){
                        Z = (sqrt(X.toDouble())).rounder().toString()
                        W = "sqrt".plus(X)
                    } else if(view.text == "^2"){
                        Z = ((X.toDouble()).pow(2)).rounder().toString()
                        W = X.plus("^2")
                    } else if(view.text == "^3"){
                        Z = ((X.toDouble()).pow(3)).rounder().toString()
                        W = X.plus("^3")
                    } else if(view.text == "ln" && X.toDouble() > 0.0){
                        Z = (ln(X.toDouble())).rounder().toString()
                        W = "ln".plus(X)
                    } else if(view.text == "e^"){
                        Z = (exp(X.toDouble())).rounder().toString()
                        W = "e^".plus(X)
                    }else{
                        return
                    }
                } catch (e: Exception){
                    return
                }

                workingsTV.text = W
                resultsTV.text = Z
                X = Z
                resetX = true
                canAddDecimal = true
                valueOrder = 0
                operationVal = ""
            }
        }
    }
    fun allClearAction(view: View){
        resultsTV.text = ""
        X = ""
        Y = ""
        canAddDecimal = true
        valueOrder = 0
        operationVal = ""
    }
    fun memorySetAction(view: View){
        memory = X.toDouble()
        memoryFl = true
    }
    fun memoryReadAction(view: View){
        if(memoryFl){
            if(valueOrder == 0){
                if(resetX){
                    X = ""
                    resultsTV.text = ""
                    resetX = false
                }
                X += memory.toString()
            }
            else if(valueOrder == 1){
                Y += memory.toString()
            }
            resultsTV.append(memory.toString())

            canAddDecimal = false
        }
    }

    fun advancedAction(view: View){
        screenTextW = workingsTV.text.toString()
        screenTextR = resultsTV.text.toString()
        switchLayout = if(switchLayout){
            setContentView(R.layout.activity_adv)
            false
        } else{
            setContentView(R.layout.activity_main)
            true
        }
        workingsTV.append(screenTextW)
        resultsTV.append(screenTextR)
    }

    private fun Double.rounder(): Double {
        var decimals = 5
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}