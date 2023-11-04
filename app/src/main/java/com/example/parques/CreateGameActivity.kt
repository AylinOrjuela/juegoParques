package com.example.parques

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.parques.databinding.ActivityCreateGameBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CreateGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGameBinding
    private var id: Int = 0

    val db = Firebase.firestore
    val partida = db.collection("Juego").document("Partida")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        asignarId()
    }

    private fun asignarId() {

        partida.addSnapshotListener { consulta, error ->
            error?.let {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            consulta?.let {
                if (consulta.getBoolean("EstadoJ1") == false) {
                    id = 1
                    binding.tvName1.text = "pedro"

                }
                if (consulta.getBoolean("EstadoJ2") == false) {
                    id = 2
                    binding.tvName2.text = "manuel"
                }
            }

        }
    }
}