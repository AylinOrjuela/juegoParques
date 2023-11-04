package com.example.parques

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.parques.databinding.ActivityCreateGameBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CreateGameActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCreateGameBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Registro1 = db.collection("Partida").document("Jugador1")
        val Registro2 = db.collection("Partida").document("Jugador2")

        Registro1.get()
            .addOnSuccessListener { registro ->
                if (registro.exists()) {
                    binding.tvName1.text = "Jugador1"
                }
            }

        Registro2.get()
            .addOnSuccessListener { registro ->
                if (registro.exists()) {
                    binding.tvName2.text = "Jugador2"
                }
            }

    }
}