package com.example.appvolunt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PerfilFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var spinnerTipoDocumento: Spinner
    private lateinit var editTextDNI: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var spinnerTipo: Spinner
    private lateinit var editTextCelular: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var spinnerAreaInteres: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_perfil, container, false)

        database = FirebaseDatabase.getInstance().getReference("PERFIL")
        auth = FirebaseAuth.getInstance()

        val btnGuardar: Button = view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            saveProfile()
        }

        return view
    }

    private fun saveProfile() {
        val tipoDocumento = spinnerTipoDocumento.selectedItem.toString()
        val nroDocumento = editTextDNI.text.toString()
        val nombre = editTextNombre.text.toString()
        val tipo = spinnerTipo.selectedItem.toString()
        val celular = editTextCelular.text.toString()
        val direccion = editTextDireccion.text.toString()
        val areaInteres = spinnerAreaInteres.selectedItem.toString()

        val user = auth.currentUser
        val userId = user?.uid

        userId?.let {
            val profileId = database.child(it).push().key
            val profile = Profile(profileId, tipoDocumento, nroDocumento, nombre, tipo, celular, direccion, areaInteres)

            profileId?.let {
                database.child(it).setValue(profile)
                    .addOnCompleteListener {
                        // Registro exitoso
                    }
                    .addOnFailureListener {
                        // Error al registrar
                    }
            }
        }
    }

    data class Profile(
        val id: String? = null,
        val tipoDocumento: String? = null,
        val nroDocumento: String? = null,
        val nombre: String? = null,
        val tipo: String? = null,
        val celular: String? = null,
        val direccion: String? = null,
        val areaInteres: String? = null
    )
}