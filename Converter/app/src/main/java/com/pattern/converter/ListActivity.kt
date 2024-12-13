package com.pattern.converter

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    private var currencyMap = mutableMapOf<String, Double>()
    private var currencies = mutableListOf<String>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val currencyListView = findViewById<ListView>(R.id.currencyListView)
        val bundle = intent.extras
        if (bundle != null) {
            currencyMap = bundle.getSerializable("currencyMap", HashMap::class.java) as MutableMap<String, Double>
            currencies = bundle.getStringArrayList("currencies") ?: mutableListOf()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, currencies)
        currencyListView.adapter = adapter
    }
}
