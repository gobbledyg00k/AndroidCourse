package com.pattern.converter

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import kotlin.concurrent.thread
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {

    private lateinit var amountEditText: EditText
    private lateinit var convertButton: Button
    private lateinit var currencyInSpinner: Spinner
    private lateinit var currencyOutSpinner: Spinner
    private lateinit var resultTextView: TextView
    private var currencyMap = mutableMapOf<String, Double>()
    private var currencies = mutableListOf<String>()

    @SuppressLint("DefaultLocale")
    private fun convertCurrencies() {
        val amount = amountEditText.text.toString().toDoubleOrNull()
        val selectedInCurrency = currencyInSpinner.selectedItem as? String
        val selectedOutCurrency = currencyOutSpinner.selectedItem as? String

        if (amount != null && selectedInCurrency != null && selectedOutCurrency != null) {
            val rateIn = currencyMap[selectedInCurrency] ?: 1.0
            val rateOut = currencyMap[selectedOutCurrency] ?: 1.0
            val convertedAmount = amount * rateIn / rateOut
            resultTextView.text = String.format("%.2f %s", convertedAmount, selectedOutCurrency)
        } else {
            Snackbar.make(convertButton, this.getString(R.string.incorrect_data), Snackbar.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amountEditText = findViewById(R.id.amountEditText)
        convertButton = findViewById(R.id.convertButton)
        currencyInSpinner = findViewById(R.id.currencyInSpinner)
        currencyOutSpinner = findViewById(R.id.currencyOutSpinner)
        resultTextView = findViewById(R.id.resultTextView)

        val bundle = intent.extras
        if (bundle != null) {
            currencyMap = bundle.getSerializable("currencyMap", HashMap::class.java) as MutableMap<String, Double>
            currencies = bundle.getStringArrayList("currencies") ?: mutableListOf()
        }

        setupSpinners()

        convertButton.setOnClickListener { convertCurrencies() }
    }

    private fun setupSpinners() {
        val spinnerAdapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_item,
            currencies
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencyInSpinner.adapter = spinnerAdapter
        currencyOutSpinner.adapter = spinnerAdapter
    }

}

