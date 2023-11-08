package com.example.parques

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.parques.databinding.ActivityBoardGameBinding

class BoardGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extra = intent.extras
        val id = extra?.getInt("id")

        if(id == 1){
            binding.Titulo.text = "Jugador 1"
        }else if(id == 2){
            binding.Titulo.text = "Jugador 2"
        }

    }
}