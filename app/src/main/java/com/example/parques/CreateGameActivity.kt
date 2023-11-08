package com.example.parques

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.parques.databinding.ActivityCreateGameBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class CreateGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGameBinding
    private var id: Int = 0
    private lateinit var firebaseInstance: FirebaseInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)
        initListeners()

    }

    private fun initListeners(){
        binding.btnPlayer1.setOnClickListener {
            setPlayer(1)
            setUpListeners()
        }
        binding.btnPlayer2.setOnClickListener {
            setPlayer(2)
            setUpListeners()
        }
    }

    private fun setPlayer(btn:Int){//Asignar los jugadores
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                var aux = data?.second
                if (aux != null) {
                    if(btn==1){
                        aux.EstadoJ1=true
                        firebaseInstance.writeOnFirebase(aux)
                        binding.btnPlayer1.isEnabled = false
                        binding.btnPlayer2.isEnabled = false
                        id = 1
                    }else if(btn == 2){
                        aux.EstadoJ2=true
                        firebaseInstance.writeOnFirebase(aux)
                        binding.btnPlayer2.isEnabled = false
                        binding.btnPlayer1.isEnabled = false
                        id = 2
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("Error onCancelled", error.details)
            }
        }
        firebaseInstance.setupDatabaseListener(postListener)
    }

    private fun setUpListeners() {//Mostrar los jugadores
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                var aux = data?.second
                if (aux != null) {
                    if(aux.EstadoJ1 == true){
                        binding.tvName1.text = "Conectado"
                    }
                    if(aux.EstadoJ2 == true){
                        binding.tvName2.text = "Conectado"
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