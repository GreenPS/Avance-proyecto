package com.example.greenps.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenps.R
import com.example.greenps.databinding.ActivityPerfilBinding

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //n setContentView(R.layout.activity_perfil)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}