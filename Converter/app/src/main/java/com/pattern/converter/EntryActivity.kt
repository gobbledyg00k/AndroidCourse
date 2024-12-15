package com.pattern.converter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import kotlin.concurrent.thread

class EntryActivity : AppCompatActivity() {

    private lateinit var converterButton: Button
    private lateinit var currencyListButton: Button

    private val client = OkHttpClient()
    private val currencyMap = mutableMapOf<String, Double>()
    private val currencies = mutableListOf<String>()

    private var dataFlag: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        converterButton = findViewById(R.id.converterButton)
        currencyListButton = findViewById(R.id.currencyListButton)

        fetchCurrencies()

        converterButton.isEnabled = true
        currencyListButton.isEnabled = true

        converterButton.setOnClickListener {
            if (dataFlag) {
                val intent = Intent(this, MainActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("currencyMap", HashMap(currencyMap))
                bundle.putStringArrayList("currencies", ArrayList(currencies))
                intent.putExtras(bundle)
                startActivity(intent)
            } else{
                Snackbar.make(converterButton, this.getString(R.string.no_data), Snackbar.LENGTH_LONG).show()
            }
        }

        currencyListButton.setOnClickListener {
            if (dataFlag) {
                val intent = Intent(this, ListActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("currencyMap", HashMap(currencyMap))
                bundle.putStringArrayList("currencies", ArrayList(currencies))
                intent.putExtras(bundle)
                startActivity(intent)
            } else{
                Snackbar.make(currencyListButton, this.getString(R.string.no_data), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun fetchCurrencies() {
        thread {
            try {
                val request = Request.Builder()
                    .url(this.getString(R.string.url))
                    .build()
                val response = client.newCall(request).execute()
                val jsonData = response.body?.string()

                if (jsonData != null) {
                    val jsonObject = JSONObject(jsonData)
                    val valuteObject = jsonObject.getJSONObject("Valute")

                    currencyMap[this.getString(R.string.rub)] = 1.0
                    currencies.add(this.getString(R.string.rub))
                    valuteObject.keys().forEach { key ->
                        val currency = valuteObject.getJSONObject(key)
                        val code = currency.getString("CharCode")
                        val name = currency.getString("Name")
                        val value = currency.getDouble("Value") / currency.getDouble("Nominal")
                        currencyMap["$code - $name"] = value
                        currencies.add("$code - $name")
                    }
                }
            } catch (e: IOException) {
                dataFlag = false
                e.printStackTrace()
            }
        }
    }
}
