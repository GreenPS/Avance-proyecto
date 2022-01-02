package com.example.greenps.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenps.R

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val backMenu = supportActionBar
        if(backMenu != null){
            backMenu.title = "Perfil"
        }
        backMenu!!.setDisplayHomeAsUpEnabled(true)
    }
}