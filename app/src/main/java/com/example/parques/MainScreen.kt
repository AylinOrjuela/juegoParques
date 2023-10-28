package com.example.parques

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.parques.databinding.ActivityMainScreenBinding

class MainScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()

    }

    private fun initListeners() {
        binding.btnRules.setOnClickListener {
            val intent = Intent(this, RulesActivity::class.java)
            startActivity(intent)
        }
        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, CreateGameActivity::class.java)
            startActivity(intent)
        }
    }

}