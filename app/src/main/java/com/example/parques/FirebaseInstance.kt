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

    fun setupDatabaseListener(postListener: ValueEventListener) {
        dataBase.reference.addValueEventListener(postListener)
    }

    fun singleValueEventListener(postListener: ValueEventListener){
        dataBase.reference.addListenerForSingleValueEvent(postListener)
    }

    private fun getGenericPartidaItem(ej1:Boolean, ej2:Boolean, estadop:String, posj1:String, posj2:String, turnoj:Boolean): partida{
        val partida = partida(ej1, ej2,estadop,posj1,posj2,turnoj)
        return partida
    }

    fun updateJ1(estado:Boolean){
        myRef.child("estadoJ1").setValue(estado)
    }
    fun updateJ2(estado:Boolean){
        myRef.child("estadoJ2").setValue(estado)
    }
    fun updateEstadoPartida(estado:String){
        myRef.child("estadoPartida").setValue(estado)
    }
    fun updatePosJ1(posicion:String){
        myRef.child("posJ1").setValue(posicion)
    }
    fun updatePosJ2(posicion:String){
        myRef.child("posJ2").setValue(posicion)
    }
    fun updateTurno(turno: Boolean){
        myRef.child("turnoJugador").setValue(turno)
    }
}