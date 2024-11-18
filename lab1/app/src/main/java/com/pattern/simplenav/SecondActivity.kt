package com.pattern.simplenav

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener{
            val intent = Intent(this, SecretActivity::class.java)
            startActivity(intent)
        }
        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener{
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}