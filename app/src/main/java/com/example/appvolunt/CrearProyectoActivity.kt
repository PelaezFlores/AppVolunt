package com.example.appvolunt

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appvolunt.databinding.ActivityCrearProyectoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CrearProyectoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearProyectoBinding
    private val db = FirebaseFirestore.getInstance()
    private val habilidadesList = ArrayList<String>()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearProyectoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener lista de habilidades desde Firebase
        obtenerHabilidadesDesdeFirebase()

        // Crear adaptador para habilidades
        val habilidadesAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, habilidadesList)
        binding.habilidadesRequeridas.setAdapter(habilidadesAdapter)

        binding.botonPublicar.setOnClickListener {
            validarYEnviar()
        }
    }

    private fun validarYEnviar() {
        val nombreProyecto = binding.nombreProyecto.text.toString()
        val descripcionProyecto = binding.descripcionProyecto.text.toString()
        val habilidadesRequeridas = binding.habilidadesRequeridas.text.toString()
        val ubicacion = binding.ubicacion.text.toString()

        val calendar = Calendar.getInstance()
        calendar.set(binding.fecha.year, binding.fecha.month, binding.fecha.dayOfMonth,
            binding.hora.hour, binding.hora.minute)
        val fechaHora = calendar.timeInMillis

        val cantidadVoluntarios = binding.cantidadVoluntarios.text.toString()
        val estado = binding.estado.selectedItem.toString()

        val userId = auth.currentUser?.uid ?: ""

        // Validaciones
        if (nombreProyecto.isEmpty()) {
            Toast.makeText(this, "El nombre del proyecto no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        if (userId.isEmpty()) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear un objeto de proyecto
        val proyecto = hashMapOf(
            "nombre" to nombreProyecto,
            "descripcion" to descripcionProyecto,
            "habilidadesRequeridas" to habilidadesRequeridas,
            "ubicacion" to ubicacion,
            "fechaHora" to fechaHora,
            "cantidadVoluntarios" to cantidadVoluntarios,
            "estado" to estado,
            "userId" to userId
        )

        // Enviar a Firebase
        db.collection("proyectos")
            .add(proyecto)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Proyecto publicado exitosamente", Toast.LENGTH_SHORT).show()
                Log.d("CrearProyectoActivity", "Documento agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al publicar el proyecto", Toast.LENGTH_SHORT).show()
                Log.w("CrearProyectoActivity", "Error al agregar documento", e)
            }
    }

    private fun obtenerHabilidadesDesdeFirebase() {
        // Obtener habilidades desde Firebase y agregarlas a la lista habilidadesList
        val habilidadesRef = db.collection("habilidades")
        habilidadesRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val habilidad = document.getString("descripcion")
                    habilidad?.let {
                        habilidadesList.add(it)
                    }
                }
                // Notificar al adaptador que los datos han cambiado
                (binding.habilidadesRequeridas.adapter as ArrayAdapter<String>).notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("CrearProyectoActivity", "Error al obtener las habilidades desde Firebase", exception)
            }
    }
}