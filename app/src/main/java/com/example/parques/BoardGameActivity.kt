package com.example.parques

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var extra = intent.extras
        id = extra?.getInt("id")!!

        binding.btnPlayer1Home.setBackgroundColor(Color.RED)
        binding.btnPlayer2Home.setBackgroundColor(Color.YELLOW)

        firebaseInstance = FirebaseInstance(this)

        enabledDice()
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

    private fun start(){
        if(id == 1 && par){
            binding.btn58.setBackgroundColor(Color.RED)
            binding.btnPlayer1Home.setBackgroundColor(Color.TRANSPARENT)
        }else if(id == 2 && par){
            binding.btn24.setBackgroundColor(Color.YELLOW)
            binding.btnPlayer2Home.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    //asdasdsa
    private fun play() {
        var recorrido: Int = dice() //Obteniendo el valor a recorrer del jugador

        turn() //Habilitamos o deshabilitamos el boton para saber de que jugador es el turno
        start()
    }

    private fun pares(p: partida) {
        if (par == true && id == 1 || par == true && id == 2) {
            if (id == 1) {
                binding.btn58.setBackgroundColor(resources.getColor(R.color.btn_color_player1))
            } else if (id == 2) {
                binding.btn24.setBackgroundColor(resources.getColor(R.color.btn_color_player2))
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
        }
        if (id == 2 && prueba.TurnoJugador == false) {
            prueba.TurnoJugador = true
        }

        firebaseInstance.updateTurno(prueba.TurnoJugador)
        pares(prueba)

    }

    private fun color() {
        var botonRojo = Button(this)
        botonRojo.setBackgroundColor(resources.getColor(R.color.btn_color_player1))
        val botonR = (botonRojo.background as? ColorDrawable)?.color
        var botonAmarillo = Button(this)
        botonAmarillo.setBackgroundColor(resources.getColor(R.color.btn_color_player2))
        val botonA = (botonAmarillo.background as? ColorDrawable)?.color
    }

    private fun getCleanSnapshot(snapshot: DataSnapshot): Pair<String, partida>? {
        val partidaKey = "1"
        val partidaValue = snapshot.getValue(partida::class.java)

        return Pair(partidaKey!!, partidaValue!!)
    }
}

