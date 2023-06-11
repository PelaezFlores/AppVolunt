package com.example.appvolunt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val txtEmail: EditText = findViewById(R.id.txtEmail)
        val txtPassword:EditText= findViewById(R.id.txtPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val tvbtnRegistro: TextView = findViewById(R.id.textView4)
        val db = FirebaseAuth.getInstance()

        tvbtnRegistro.setOnClickListener{
            // Aquí se coloca la lógica para abrir la actividad de registro o realizar otra acción relacionada
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        btnLogin.setOnClickListener{
            val correo = txtEmail.text.toString()
            val clave = txtPassword.text.toString()

            db.signInWithEmailAndPassword(correo,clave )
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "inicio satisfactorio", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity ::class.java))
                    }else{
                            Toast.makeText(this,"correo y / o clave incorrecta",Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }
