package com.example.appvolunt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appvolunt.R
import com.example.appvolunt.ui.fragments.Model.PostulacionModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID


class PublicacionProyectosVoluntariadoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =
            inflater.inflate(R.layout.fragment_postular_proyecto_voluntariado, container, false)
        val btnRegistrar: Button = view.findViewById(R.id.btnRegistrar)

        val tvNombreVolunatario: TextView = view.findViewById(R.id.etNombreVoluntario)
        val tvEdad: TextView = view.findViewById(R.id.etEdad)
        val tvDisponibilidad: Spinner = view.findViewById(R.id.spinnerDisponibilidad)
        val tvHabilidades: Spinner = view.findViewById(R.id.spinnerHabilidades)
        val tvExperencia: TextView = view.findViewById(R.id.etDescripcionExperencia)

        val db = FirebaseFirestore.getInstance()
        btnRegistrar.setOnClickListener {
            val NombreVoluntario = tvNombreVolunatario.text.toString()
            val Edad = tvEdad.text.toString()
            val Disponibilidad = tvDisponibilidad.selectedItem.toString()
            val Habilidades = tvHabilidades.selectedItem.toString()
            val Experiencia = tvExperencia.text.toString()

            val nuevaPostulacion = PostulacionModel(NombreVoluntario,Edad,Disponibilidad,Habilidades,Experiencia)
            val id: UUID = UUID.randomUUID()

            db.collection("postulacion")
                .document(id.toString())
                .set(nuevaPostulacion)
                .addOnSuccessListener {
                    showAlert(view,"Postulancion registrada correctamente")
                    tvNombreVolunatario.text = ""
                    tvEdad.text = ""
                    tvDisponibilidad.setSelection(0)
                    tvHabilidades.setSelection(0)
                    tvExperencia.text =""
                }.addOnFailureListener{
                    showAlert(view,"Ocurrio un error al registrar ")
                }

        }
        return view
    }

    private fun showAlert(vista: View, mensaje: String) {
        Snackbar.make(vista,mensaje, Snackbar.LENGTH_LONG)

    }
}



