package com.example.parques

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.parques.databinding.ActivityWinerBinding

class WinerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityWinerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWinerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var extra = intent.extras
        val idP = extra?.getInt("idP")!!

        if(idP == 1){
            binding.tvWinner.text = "Ha ganado el jugador 1 🎇✨🎉"
        }else if(idP == 2){
            binding.tvWinner.text = "Ha ganado el jugador 2 🎇✨🎉"
        }

        binding.btnMain.setOnClickListener {
            val intent = Intent(this,MainScreen::class.java)
            startActivity(intent)
        }

    }
}