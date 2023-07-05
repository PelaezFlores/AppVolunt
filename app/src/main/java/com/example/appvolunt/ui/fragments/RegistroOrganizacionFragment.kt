package com.example.appvolunt.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import com.example.appvolunt.R
import com.example.appvolunt.ui.fragments.Model.OrganizacionModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class RegistroOrganizacionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_registro_organizacion, container, false)
        val btnRegistrar: Button = view.findViewById(R.id.btnRegistrar)
        val tvNonbreOrganaizacion: TextView = view.findViewById(R.id.etNombreOrganizacion)
        val tvPropietario: TextView = view.findViewById(R.id.etPropietario)
        val tvDireccion: TextView = view.findViewById(R.id.etDireccion)
        val tvRuc: TextView = view.findViewById(R.id.etRuc)
        val tvCelular: TextView = view.findViewById(R.id.etCelular)
        val tvCorreo: TextView = view.findViewById(R.id.etCorreo)
        val tvContraseña: TextView = view.findViewById(R.id.etContraseña)
        val db = FirebaseFirestore.getInstance()

        btnRegistrar.setOnClickListener {
            val NombreOrganizacion = tvNonbreOrganaizacion.text.toString()
            val Propietario = tvPropietario.text.toString()
            val Direccion = tvDireccion.text.toString()
            val Ruc = tvRuc.text.toString()
            val Celular = tvCelular.text.toString()
            val Correo = tvCorreo.text.toString()
            val Contraseña = tvContraseña.text.toString()

            val Organizacion = OrganizacionModel(
                NombreOrganizacion, Propietario,
                Direccion, Ruc, Celular, Correo, Contraseña
            )
            val id: UUID = UUID.randomUUID()
            db.collection("organizacion")
                .document(id.toString())
                .set(Organizacion)
                .addOnSuccessListener {
                    tvNonbreOrganaizacion.text = ""
                    tvPropietario.text = ""
                    tvDireccion.text = ""
                    tvRuc.text = ""
                    tvCelular.text = ""
                    tvCorreo.text = ""
                    tvContraseña.text = ""


                }.addOnFailureListener {
                    showAlert(view, "Ocurrio un error al registrar organizacion")
                }
        }
        return view
}
private fun showAlert(vista:View,mensaje:String){
    Snackbar.make(vista,mensaje,Snackbar.LENGTH_LONG).show()
}
}