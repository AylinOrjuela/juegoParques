package com.example.parques

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.parques.databinding.ActivityMainScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var firebaseInstance: FirebaseInstance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)
        val reset = partida()
        firebaseInstance.updateJ1(false)
        firebaseInstance.updateJ2(false)
        firebaseInstance.updateEstadoPartida("esperando")
        firebaseInstance.updatePosJ1("home")
        firebaseInstance.updatePosJ2("home")
        firebaseInstance.updateTurno(true)
        initListeners()
    }
    private fun initListeners() {
        binding.btnRules.setOnClickListener {
            val intent = Intent(this, RulesActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, CreateGameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}