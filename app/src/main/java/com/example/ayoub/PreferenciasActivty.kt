package com.example.ayoub

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class PreferenciasActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var spRango: Spinner
    private lateinit var swEstado: SwitchCompat
    private lateinit var btnGuardar: Button
    private lateinit var tvUltimoRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        etNombre = findViewById(R.id.etNombreGuardia)
        spRango = findViewById(R.id.spRango)
        swEstado = findViewById(R.id.swEstadoTurno)
        btnGuardar = findViewById(R.id.btnGuardarPref)
        tvUltimoRegistro = findViewById(R.id.tvUltimoRegistro)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            if (nombre.isNotEmpty()) {
                val rango = spRango.selectedItemPosition
                val enGuardia = swEstado.isChecked
                guardarDatosGuardia(nombre, rango, enGuardia)
                etNombre.text.clear()
                restaurarInterfaz()
                Toast.makeText(this, "Guardia registrado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                etNombre.error = "Introduce el nombre del efectivo"
                etNombre.requestFocus()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        restaurarInterfaz()
    }

    private fun guardarDatosGuardia(nombre: String, rango: Int, estado: Boolean) {
        val prefs = getSharedPreferences("RegistroGuardia", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("KEY_SOLDADO", nombre)
        editor.putInt("KEY_RANGO", rango)
        editor.putBoolean("KEY_ESTADO", estado)
        editor.apply()
    }

    private fun restaurarInterfaz() {
        val prefs = getSharedPreferences("RegistroGuardia", Context.MODE_PRIVATE)
        val soldado = prefs.getString("KEY_SOLDADO", "") ?: ""
        val rango = prefs.getInt("KEY_RANGO", 0)
        val estado = prefs.getBoolean("KEY_ESTADO", false)

        if (rango in 0 until spRango.count) {
            spRango.setSelection(rango)
        }
        swEstado.isChecked = estado

        if (soldado.isNotEmpty()) {
            val textoEstado = if (estado) "En Guardia" else "En Reposo"
            val nombreRango = spRango.selectedItem.toString()
            tvUltimoRegistro.text = "Turno activo: $soldado ($nombreRango) - $textoEstado"
        } else {
            tvUltimoRegistro.text = "Sin guardia asignada"
        }
    }
}