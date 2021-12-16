package com.example.greenps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Comentarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentarios)

        val backMenu = supportActionBar
        if(backMenu != null){
            backMenu.title = "Comentarios"
        }
        backMenu!!.setDisplayHomeAsUpEnabled(true)
    }
}