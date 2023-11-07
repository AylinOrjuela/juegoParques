package com.example.parques

data class partida(
    var EstadoJ1:Boolean = false,
    var EstadoJ2:Boolean = false,
    var EstadoPartida:String = "esperando",
    var PosJ1:String = "home",
    var PosJ2:String = "home",
    var TurnoJugador:Boolean = true
    )
