package com.example.parques

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class FirebaseInstance(context: Context) {

    private val dataBase = Firebase.database
    private val myRef = dataBase.reference

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase(game:partida) {
        myRef.setValue(getGenericPartidaItem(game.EstadoJ1, game.EstadoJ2, game.EstadoPartida, game.PosJ1, game.PosJ2, game.TurnoJugador))
    }

    fun setupDatabaseListener(postListener: ValueEventListener) {
        dataBase.reference.addValueEventListener(postListener)
    }

    private fun getGenericPartidaItem(ej1:Boolean, ej2:Boolean, estadop:String, posj1:String, posj2:String, turnoj:Boolean): partida{
        val partida = partida(ej1, ej2,estadop,posj1,posj2,turnoj)
        return partida
    }
}