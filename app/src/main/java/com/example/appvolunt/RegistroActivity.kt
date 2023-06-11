package com.example.appvolunt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val db = FirebaseFirestore.getInstance()
        val btnCrearUsuario: Button = findViewById(R.id.btnRegistrar)
        val textNombres: TextView = findViewById(R.id.etNombresReg)
        val textdni: TextView = findViewById(R.id.etdniReg)
        val textEmail: TextView = findViewById(R.id.etemailReg)
        val textPassword: TextView = findViewById(R.id.etpasswordReg)


        btnCrearUsuario.setOnClickListener{
            db.collection("users").document(textEmail.text.toString()).set(
                hashMapOf("nombre" to textNombres.text.toString(),
                    "DNI" to textdni.text.toString(),
                    "Password" to textPassword.text.toString(),
                    "Tipo" to "cliente")
            )

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(textEmail.text.toString(), textPassword.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showConfirm()
                    }else{
                        showAlert()
                    }
                }

            textNombres.text = ""
            textdni.text = ""
            textEmail.text = ""
            textPassword.text = ""

        }

    }

    private fun showConfirm(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro")
        builder.setMessage("Se ha registrado nuevo usuario. Volver a login")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}