package com.example.actividaddeimagenedinamicas

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener referencias a los elementos de la interfaz
        val etName: EditText = findViewById(R.id.etName)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPhone: EditText = findViewById(R.id.etPhone)
        val ivProfileImage: ImageView = findViewById(R.id.ivProfileImage)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        // Filtro para permitir solo letras en el campo de nombre
        etName.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!source[i].isLetter() && source[i] != ' ') {  // Solo letras y espacios permitidos
                    return@InputFilter ""
                }
            }
            null
        })

        // Validación adicional con TextWatcher para asegurar que contiene al menos 3 letras
        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Filtra solo letras para contar la cantidad de letras válidas
                val lettersOnly = s.toString().filter { it.isLetter() }
                if (lettersOnly.length < 3) {
                    etName.error = "El nombre debe contener al menos 3 letras"
                } else {
                    etName.error = null // Limpiar error si es válido
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validar que el correo electrónico tenga "@" y "."
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Para un correo electrónico válido
                if (!s.toString().matches(Regex("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,4}$"))) {
                    etEmail.error = "Correo electrónico no válido"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Limitar el campo de teléfono a 10 dígitos
        val maxLength = 10
        etPhone.filters = arrayOf(InputFilter.LengthFilter(maxLength))

        // Validar que el número de teléfono tenga 10 dígitos
        etPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Validar que el teléfono tenga exactamente 10 dígitos
                if (s.toString().length != 10 || !s.toString().matches(Regex("\\d{10}"))) {
                    etPhone.error = "El número de teléfono debe tener 10 dígitos"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Botón para enviar el formulario
        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            // Validación de los campos
            var isValid = true

            if (name.isEmpty()) {
                etName.error = "El nombre es obligatorio"
                isValid = false
            } else if (name.filter { it.isLetter() }.length < 3) {
                etName.error = "El nombre debe contener al menos 3 letras"
                isValid = false
            } else {
                etName.error = null
            }

            if (email.isEmpty()) {
                etEmail.error = "El correo es obligatorio"
                isValid = false
            } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+".toRegex())) {
                etEmail.error = "Correo electrónico inválido"
                isValid = false
            } else {
                etEmail.error = null
            }

            if (phone.isEmpty()) {
                etPhone.error = "El teléfono es obligatorio"
                isValid = false
            } else if (phone.length != 10) {
                etPhone.error = "El teléfono debe contener exactamente 10 dígitos"
                isValid = false
            } else {
                etPhone.error = null
            }

            // Si todos los campos son válidos
            if (isValid) {
                ivProfileImage.setImageResource(R.drawable.paloma)
                Toast.makeText(this, "Formulario enviado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}