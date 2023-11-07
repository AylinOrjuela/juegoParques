package com.example.parques

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.parques.databinding.ActivityCreateGameBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class CreateGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGameBinding
    private var id: Int = 0
    private lateinit var firebaseInstance: FirebaseInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvName1.text = "Desconectado"
        binding.tvName2.text = "Desconectado"
        firebaseInstance = FirebaseInstance(this)

        setUpListeners()
    }

    private fun setUpListeners() {
        val postListener = object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                var aux = data?.second
                if (aux != null) {
                    if (aux.EstadoJ1 == false){
                        id = 1
                        binding.tvName1.text = "Conectado"
                        aux.EstadoJ1 = true
                        firebaseInstance.writeOnFirebase(aux)
                    }else if (aux.EstadoJ2 == false){
                            id = 2
                            binding.tvName2.text = "Conectado"
                            aux.EstadoJ2 = true
                            firebaseInstance.writeOnFirebase(aux)
                        }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("Error onCancelled", error.details)
            }

        }

        firebaseInstance.setupDatabaseListener(postListener)

    }

    private fun getCleanSnapshot(snapshot: DataSnapshot): Pair<String,partida>?{
        val partidaKey = "1"
        val partidaValue = snapshot.getValue(partida::class.java)

        return Pair(partidaKey!!,partidaValue!!)
    }


}