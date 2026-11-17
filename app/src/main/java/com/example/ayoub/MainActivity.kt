package com.example.ayoub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIrPrefs = findViewById<Button>(R.id.btnIrPrefs)
        val btnIrFicheros = findViewById<Button>(R.id.btnIrFicheros)
        val btnIrExterno = findViewById<Button>(R.id.btnIrExterno)
        val btnIrSQLite = findViewById<Button>(R.id.btnIrSQLite)

        btnIrPrefs.setOnClickListener {
            val intent = Intent(this, PreferenciasActivity::class.java)
            startActivity(intent)
        }

        btnIrFicheros.setOnClickListener {
            val intent = Intent(this, FicherosActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Laboratorio 2 A", Toast.LENGTH_SHORT).show()
        }

        btnIrExterno.setOnClickListener {
            val intent = Intent(this, AlmacenamientoExterno::class.java)
            startActivity(intent)
            Toast.makeText(this, "Laboratorio 2 B", Toast.LENGTH_SHORT).show()
        }

        btnIrSQLite.setOnClickListener {
            Toast.makeText(this, "Laboratorio 3", Toast.LENGTH_SHORT).show()
        }
    }
}