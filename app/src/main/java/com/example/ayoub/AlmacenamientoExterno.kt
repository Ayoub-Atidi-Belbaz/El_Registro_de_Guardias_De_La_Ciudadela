package com.example.ayoub

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class AlmacenamientoExternoActivity : AppCompatActivity() {

    private lateinit var etNombreMapa: EditText
    private lateinit var etCoordenadas: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnLeer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_externo)

        etNombreMapa = findViewById(R.id.etNombreMapa)
        etCoordenadas = findViewById(R.id.etCoordenadasMapa)
        btnGuardar = findViewById(R.id.btnEscribirExterno)
        btnLeer = findViewById(R.id.btnLeerExterno)

        btnGuardar.setOnClickListener {
            val nombre = etNombreMapa.text.toString().trim()
            val contenido = etCoordenadas.text.toString()

            if (!esAlmacenamientoExternoEscribible()) {
                Toast.makeText(this, "Error: El almacenamiento externo no está montado", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (nombre.isNotEmpty()) {
                try {
                    val rutaDirectorio = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                    val archivoTarget = File(rutaDirectorio, nombre)
                    archivoTarget.writeText(contenido)
                    Toast.makeText(this, "Mapa guardado en Android/data/.../Documents/", Toast.LENGTH_SHORT).show()
                    etCoordenadas.text.clear()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error de hardware al escribir: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombreMapa.error = "Introduce el nombre del plano cartográfico"
            }
        }

        btnLeer.setOnClickListener {
            val nombre = etNombreMapa.text.toString().trim()

            if (!esAlmacenamientoExternoLeible()) {
                Toast.makeText(this, "Error: Soporte no disponible para lectura", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (nombre.isNotEmpty()) {
                try {
                    val rutaDirectorio = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                    val archivoTarget = File(rutaDirectorio, nombre)

                    if (archivoTarget.exists()) {
                        val textoRecuperado = archivoTarget.readText()
                        etCoordenadas.setText(textoRecuperado)
                        Toast.makeText(this, "Mapa cargado correctamente", Toast.LENGTH_SHORT).show()
                    } else {
                        etNombreMapa.error = "El plano especificado no existe en este sector"
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al acceder al flujo de lectura: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombreMapa.error = "Especifica qué plano deseas consultar"
            }
        }
    }

    private fun esAlmacenamientoExternoEscribible(): Boolean {
        val estado = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == estado
    }

    private fun esAlmacenamientoExternoLeible(): Boolean {
        val estado = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == estado || Environment.MEDIA_MOUNTED_READ_ONLY == estado
    }
}