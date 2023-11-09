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
    private val extra = intent.extras
    private var par:Boolean = false
    val id = extra?.getInt("id")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)
        initListener()
        title()
        enabledDice()
    }

    private fun initListener(){
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
    private fun title(){
        if (id != null) {
            if(id == 1){
                binding.Titulo.text = "Jugador 1"
            }else if(id == 2){
                binding.Titulo.text = "Jugador 2"
            }
        }
    }
    private fun dice(): Int{
        var dado1:Int = Random.nextInt(1,7)
        var dado2:Int = Random.nextInt(1,7)

        binding.tvDado1.text = dado1.toString()
        binding.tvDado2.text = dado2.toString()

        if (dado1 == dado2){
            par = true
        }else{
            par = false
        }

        return dado2 + dado1

    }

    private fun enabledDice(){
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                var aux = data?.second

                if(aux!= null){
                    if(aux.TurnoJugador == true && id == 1 || aux.TurnoJugador == false && id == 2){
                        binding.btnCast.isEnabled = true
                    }else{
                        binding.btnCast.isEnabled = false
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("Error onCancelled", error.details)
            }

        }

        firebaseInstance.setupDatabaseListener(postListener)
    }

    private fun play(){

        var botonRojo = Button(this)
        botonRojo.setBackgroundColor(resources.getColor(R.color.btn_color_player1))
        val botonR = (botonRojo.background as? ColorDrawable)?.color
        var botonAmarillo = Button(this)
        botonAmarillo.setBackgroundColor(resources.getColor(R.color.btn_color_player2))
        val botonA = (botonAmarillo.background as? ColorDrawable)?.color

        var recorrido: Int = dice()

        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                var aux = data?.second

                if(aux!= null){
                    aux.TurnoJugador
                    if(id == 1 && aux.TurnoJugador == true || id == 2 && aux.TurnoJugador == false){
                        if (par == true && binding.btnPlayer1Home.backgroundTintList?.defaultColor == botonR && id == 1){
                            binding.btn1.setBackgroundColor(resources.getColor(R.color.btn_color_player1))
                        }
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
