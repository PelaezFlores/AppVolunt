package com.example.appvolunt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.appvolunt.ui.fragments.RegistroOrganizacionFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } // catch block to handle NullPointerException
        catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.btnLogin)

        val etEmail: EditText = findViewById(R.id.txtEmail)
        val etContraseña: EditText = findViewById(R.id.txtPassword)

        val Registro: TextView = findViewById(R.id.txtRegistrar)
        val db = FirebaseFirestore.getInstance()

        val email1: String = "admin@esan.pe"

        Registro.setOnClickListener{
            this.goRegistro()
        }




        btnLogin.setOnClickListener{
        val email= etEmail.text.toString()
            val clave = etContraseña.text.toString()
            if(email.isNotEmpty() && clave.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, clave).addOnCompleteListener{
                        if (it.isSuccessful){
                            db.collection("users").document(email).get().addOnSuccessListener{

                                val validacion = it.get("Tipo") as String?
                                if (validacion == "Voluntario"){
                                    goMain()
                                    etEmail.text.clear()
                                    etContraseña.text.clear()

                                }
                                if (validacion == "organización"){
                                    goAdmin()
                                    etEmail.text.clear()
                                    etContraseña.text.clear()
                                }

                                goMain()
                            }
                        }else{
                            showAlert()
                        }
                    }
            }


        }

    }


    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goMain(){
        val intent: Intent = Intent(this, PerfilFragment::class.java)
        startActivity(intent)

    }

    private fun goAdmin(){
        val intent: Intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)

    }

    private fun goRegistro(){
        val intent: Intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)

    }
}
