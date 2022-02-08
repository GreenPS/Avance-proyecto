package com.example.greenps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenps.databinding.ActivityComentariosBinding

class Comentarios : AppCompatActivity() {
    private lateinit var binding: ActivityComentariosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_comentarios)
        binding = ActivityComentariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}