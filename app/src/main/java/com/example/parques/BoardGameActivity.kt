package com.example.parques

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.parques.databinding.ActivityBoardGameBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class BoardGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardGameBinding
    private lateinit var firebaseInstance: FirebaseInstance
    private var id: Int = 0
    private var par: Boolean = false
    private lateinit var listaBotones: MutableList<Button>
    private var posicion: String = ""
    private var posicion2: String = ""
    private var inicio: Boolean = false
    private var igual: Boolean = false
    private var turno: Boolean = true
    private var final: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var extra = intent.extras
        id = extra?.getInt("id")!!

        listaBotones = createButtonList()

        binding.btnPlayer1Home0.setBackgroundColor(Color.RED)
        binding.btnPlayer2Home0.setBackgroundColor(Color.YELLOW)

        firebaseInstance = FirebaseInstance(this)

        enabledDice()
        route()
        initListener()
        title()
    }

    private fun initListener() {
        binding.btnCast.setOnClickListener {
            play()
        }
        binding.btnMain.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }
        binding.btnRules.setOnClickListener {
            val intent = Intent(this, RulesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun title() {
        if (id != null) {
            if (id == 1) {
                binding.Titulo.text = "Jugador 1"
            } else if (id == 2) {
                binding.Titulo.text = "Jugador 2"
            }
        }
    }

    private fun dice(): Int {
        var dado1: Int = Random.nextInt(1, 7)
        var dado2: Int = Random.nextInt(1, 7)
        binding.tvDado1.text = dado1.toString()
        binding.tvDado2.text = dado2.toString()
        par = dado1 == dado2
        return dado2 + dado1
    }

    private fun enabledDice() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                var aux = data?.second
                if (aux != null) {
                    if (id == 1) {
                        binding.btnCast.isEnabled = aux.TurnoJugador
                    } else if (id == 2) {
                        binding.btnCast.isEnabled = !aux.TurnoJugador
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Error onCancelled", error.details)
            }
        }
        firebaseInstance.setupDatabaseListener(postListener)

    }

    private fun start() {
        if (id == 1 && par) {
            firebaseInstance.updatePosJ1("btn58")
            binding.btnPlayer1Home0.setBackgroundColor(Color.TRANSPARENT)
        } else if (id == 2 && par) {
            firebaseInstance.updatePosJ2("btn24")
            binding.btnPlayer2Home0.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun play() {
        var recorrido: Int = dice() //Obteniendo el valor a recorrer del jugador

        turn() //Habilitamos o deshabilitamos el boton para saber de que jugador es el turno
        start()
        moveToken(recorrido)
    }

    private fun pares() {
        if (par == true && id == 1 && posicion == "home" || par == true && id == 2 && posicion == "home") {
            if (id == 1) {
                binding.btn58.setBackgroundColor(resources.getColor(R.color.btn_color_player1))
                inicio = true
            } else if (id == 2) {
                binding.btn24.setBackgroundColor(resources.getColor(R.color.btn_color_player2))
                inicio = true
            }
        }
    }

    private fun turn() {
        var prueba = partida()
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                var aux = data?.second
                if (aux != null) {
                    prueba = aux
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Error onCancelled", error.details)
            }
        }
        firebaseInstance.setupDatabaseListener(postListener)

        if (id == 1 && prueba.TurnoJugador == true) {
            prueba.TurnoJugador = false
            turno = prueba.TurnoJugador
        }
        if (id == 2 && prueba.TurnoJugador == false) {
            prueba.TurnoJugador = true
            turno = prueba.TurnoJugador
        }

        pares()

        if (par) {
            firebaseInstance.updateTurno(!prueba.TurnoJugador)

        } else {
            firebaseInstance.updateTurno(prueba.TurnoJugador)
        }
    }

    private fun getCleanSnapshot(snapshot: DataSnapshot): Pair<String, partida>? {
        val partidaKey = "1"
        val partidaValue = snapshot.getValue(partida::class.java)

        return Pair(partidaKey!!, partidaValue!!)
    }

    private fun createButtonList(): MutableList<Button> {
        val lista = mutableListOf<Button>()
        val btn1 = binding.btn1
        val btn2 = binding.btn2
        val btn3 = binding.btn3
        val btn4 = binding.btn4
        val btn5 = binding.btn5
        val btn6 = binding.btn6
        val btn7 = binding.btn7
        val btn8 = binding.btn8
        val btn9 = binding.btn9
        val btn10 = binding.btn10
        val btn11 = binding.btn11
        val btn12 = binding.btn12
        val btn13 = binding.btn13
        val btn14 = binding.btn14
        val btn15 = binding.btn15
        val btn16 = binding.btn16
        val btn17 = binding.btn17
        val btn18 = binding.btn18
        val btn19 = binding.btn19
        val btn20 = binding.btn20
        val btn21 = binding.btn21
        val btn22 = binding.btn22
        val btn23 = binding.btn23
        val btn24 = binding.btn24
        val btn25 = binding.btn25
        val btn26 = binding.btn26
        val btn27 = binding.btn27
        val btn28 = binding.btn28
        val btn29 = binding.btn29
        val btn30 = binding.btn30
        val btn31 = binding.btn31
        val btn32 = binding.btn32
        val btn33 = binding.btn33
        val btn34 = binding.btn34
        val btn35 = binding.btn35
        val btn36 = binding.btn36
        val btn37 = binding.btn37
        val btn38 = binding.btn38
        val btn39 = binding.btn39
        val btn40 = binding.btn40
        val btn41 = binding.btn41
        val btn42 = binding.btn42
        val btn43 = binding.btn43
        val btn44 = binding.btn44
        val btn45 = binding.btn45
        val btn46 = binding.btn46
        val btn47 = binding.btn47
        val btn48 = binding.btn48
        val btn49 = binding.btn49
        val btn50 = binding.btn50
        val btn51 = binding.btn51
        val btn52 = binding.btn52
        val btn53 = binding.btn53
        val btn54 = binding.btn54
        val btn55 = binding.btn55
        val btn56 = binding.btn56
        val btn57 = binding.btn57
        val btn58 = binding.btn58
        val btn59 = binding.btn59
        val btn60 = binding.btn60
        val btn61 = binding.btn61
        val btn62 = binding.btn62
        val btn63 = binding.btn63
        val btn64 = binding.btn64
        val btn65 = binding.btn65
        val btn66 = binding.btn66
        val btn67 = binding.btn67
        val btn68 = binding.btn68

        lista.add(btn1)
        lista.add(btn2)
        lista.add(btn3)
        lista.add(btn4)
        lista.add(btn5)
        lista.add(btn6)
        lista.add(btn7)
        lista.add(btn8)
        lista.add(btn9)
        lista.add(btn10)
        lista.add(btn11)
        lista.add(btn12)
        lista.add(btn13)
        lista.add(btn14)
        lista.add(btn15)
        lista.add(btn16)
        lista.add(btn17)
        lista.add(btn18)
        lista.add(btn19)
        lista.add(btn20)
        lista.add(btn21)
        lista.add(btn22)
        lista.add(btn23)
        lista.add(btn24)
        lista.add(btn25)
        lista.add(btn26)
        lista.add(btn27)
        lista.add(btn28)
        lista.add(btn29)
        lista.add(btn30)
        lista.add(btn31)
        lista.add(btn32)
        lista.add(btn33)
        lista.add(btn34)
        lista.add(btn35)
        lista.add(btn36)
        lista.add(btn37)
        lista.add(btn38)
        lista.add(btn39)
        lista.add(btn40)
        lista.add(btn41)
        lista.add(btn42)
        lista.add(btn43)
        lista.add(btn44)
        lista.add(btn45)
        lista.add(btn46)
        lista.add(btn47)
        lista.add(btn48)
        lista.add(btn49)
        lista.add(btn50)
        lista.add(btn51)
        lista.add(btn52)
        lista.add(btn53)
        lista.add(btn54)
        lista.add(btn55)
        lista.add(btn56)
        lista.add(btn57)
        lista.add(btn58)
        lista.add(btn59)
        lista.add(btn60)
        lista.add(btn61)
        lista.add(btn62)
        lista.add(btn63)
        lista.add(btn64)
        lista.add(btn65)
        lista.add(btn66)
        lista.add(btn67)
        lista.add(btn68)

        return lista
    }

    private fun route() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                var aux = data?.second
                if (aux != null) {
                    if (id == 1) {
                        cleanSecondPlayer()//Borramos la posicion anterior del jugador 2
                        posicion = aux.PosJ1
                        posicion2 = aux.PosJ2
                        finalJourney()
                        printSecondPlayer()
                        dismatch()
                        equalsPositions()
                    } else if (id == 2) {
                        cleanSecondPlayer()
                        posicion = aux.PosJ2
                        posicion2 = aux.PosJ1
                        finalJourney()
                        printSecondPlayer()
                        dismatch()
                        equalsPositions()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Error onCancelled", error.details)
            }
        }
        firebaseInstance.setupDatabaseListener(postListener)
    }

    private fun home() {
        if (posicion == "home") {
            if (id == 1 && inicio) {
                firebaseInstance.updatePosJ1("btn58")
            } else if (id == 2 && inicio) {
                firebaseInstance.updatePosJ2("btn24")
            }
        }
    }

    private fun moveToken(num: Int) {//recorrido
        val regex = "([a-zA-Z]+)(\\d+)".toRegex()
        val matchResult = regex.find(posicion)

        var clear: Int

        home()

        if (matchResult != null) {
            val (letras, numero) = matchResult.destructured
            var Posicion = numero.toInt()
            if (posicion != "home") { //Recorrido para jugador 1
                if (id == 1) {
                    clear = Posicion
                    Posicion += num
                    Posicion = validatePosition(Posicion)
                    listaBotones[Posicion - 1].setBackgroundColor(Color.RED)
                    firebaseInstance.updatePosJ1("btn$Posicion")
                    clearRoute(clear)
                } else if (id == 2) {
                    clear = Posicion
                    Posicion += num
                    Posicion = validatePosition(Posicion)
                    listaBotones[Posicion - 1].setBackgroundColor(Color.YELLOW)
                    firebaseInstance.updatePosJ2("btn$Posicion")
                    clearRoute(clear)
                }
            }
        }
    }

    private fun printSecondPlayer() {//Imprimir la posicion del jugador 2
        val regex = "([a-zA-Z]+)(\\d+)".toRegex()
        val matchResult = regex.find(posicion2)

        if (matchResult != null) {
            val (letras, numero) = matchResult.destructured
            var Pos2 = numero.toInt()
            if (posicion2 != "" && posicion2 != "home") {
                if (id == 1) {
                    listaBotones[Pos2 - 1].setBackgroundColor(Color.YELLOW)
                } else if (id == 2) {
                    listaBotones[Pos2 - 1].setBackgroundColor(Color.RED)
                }
            }
        }
    }

    private fun equalsPositions() {
        if (posicion == posicion2) {
            val regex = "([a-zA-Z]+)(\\d+)".toRegex()
            val matchResult = regex.find(posicion) //Posicion del jugador 1
            if (matchResult != null) {//Player principal
                val (letras, numero) = matchResult.destructured
                val Pos = numero.toInt()
                listaBotones[Pos - 1].setBackgroundColor(Color.GREEN)
            }
            igual = true
        }
    }

    private fun dismatch() {
        if (igual && id == 1 && turno && (posicion != posicion2)) {
            val regex = "([a-zA-Z]+)(\\d+)".toRegex()
            val matchResult = regex.find(posicion)
            if (matchResult != null) {
                val (letras, numero) = matchResult.destructured
                val Pos = numero.toInt()
                listaBotones[Pos - 1].setBackgroundColor(Color.RED)
                igual = false
            }
        }else if(igual && id == 2 && !turno && (posicion != posicion2)){
            val regex = "([a-zA-Z]+)(\\d+)".toRegex()
            val matchResult = regex.find(posicion)
            if (matchResult != null) {
                val (letras, numero) = matchResult.destructured
                val Pos = numero.toInt()
                listaBotones[Pos - 1].setBackgroundColor(Color.YELLOW)
                igual = false
            }
        }
    }

    private fun cleanSecondPlayer() {//Falta borrar rastro del jugador 2
        val regex = "([a-zA-Z]+)(\\d+)".toRegex()
        val matchResult = regex.find(posicion2)

        if (matchResult != null) {
            val (letras, numero) = matchResult.destructured
            val Pos2 = numero.toInt()
            if (posicion2 != "" && posicion2 != "home") {
                if (id == 1) {
                    binding.btnPlayer2Home0.setBackgroundColor(Color.TRANSPARENT)
                } else if (id == 2) {
                    binding.btnPlayer1Home0.setBackgroundColor(Color.TRANSPARENT)
                }
                listaBotones[Pos2 - 1].setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    private fun clearRoute(P: Int) {
        listaBotones[P - 1].setBackgroundColor(Color.TRANSPARENT)
    }

    private fun validatePosition(P: Int): Int {
        var POS: Int
        if((final == true && id == 1) || (final == true && id == 2)){
            return finalPosicion(P)
        } else if (P > 68 && P != null) {
            POS = P - 68
            return POS
        } else {
            return P
        }
    }

    private fun finalJourney(){
        val regex = "([a-zA-Z]+)(\\d+)".toRegex()
        val matchResult = regex.find(posicion)
        if(id == 1 && matchResult != null){
            val (letras, numero) = matchResult.destructured
            val Pos = numero.toInt()
            if(Pos in 41..53){
                final = true
            }
        }else if(id == 2 && matchResult != null){
            val (letras, numero) = matchResult.destructured
            val Pos = numero.toInt()
            if(Pos in 7..19){
                final = true
            }
        }
    }

    private fun finalPosicion(num: Int):Int{
        val regex = "([a-zA-Z]+)(\\d+)".toRegex()
        val matchResult = regex.find(posicion)
        if(final == true && id == 1 && matchResult != null){
            val (letras, numero) = matchResult.destructured
            var Pos = numero.toInt()
            if(Pos > 59){
                Pos -= 59
                return when (Pos) {
                    1 -> 100
                    2 -> 101
                    3 -> 102
                    4 -> 103
                    5 -> 104
                    6 -> 105
                    7 -> 106
                    8 -> 107
                    else -> 107
                }

            }else{
                return num
            }
        }else if(final == true && id == 2 && matchResult != null){
            val (letras, numero) = matchResult.destructured
            var Pos = numero.toInt()
            if(Pos > 59){
                Pos -= 59
                return when (Pos) {
                    1 -> 108
                    2 -> 109
                    3 -> 110
                    4 -> 111
                    5 -> 112
                    6 -> 113
                    7 -> 114
                    8 -> 115
                    else -> 115
                }

            }else{
                return num
            }
        }else{
            return num
        }

    }

}

