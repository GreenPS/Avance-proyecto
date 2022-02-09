package com.example.greenps.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.greenps.Comentarios
import com.example.greenps.R
import com.example.greenps.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Perfil : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //n setContentView(R.layout.activity_perfil)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        val bundle = intent.extras
        val nombre = bundle?.getString("nombre")
        val email = bundle?.getString("email")
        val bio = bundle?.getString("bio")
        val password = bundle?.getString("password")

        setup(nombre?: "",email?: "",bio?: "",password?: "")
    }

    private fun setup(nombre:String,email:String,bio:String,password:String,){
        binding.user.setText(email)
        binding.txtNameProfile.setText(nombre)
        binding.txtEmailProfile.setText(email)
        binding.txtBioProfile.setText(bio)
        binding.txtPassProfile.setText(password)
        binding.btnUp.setOnClickListener{
            Toast.makeText(this, "Funcion por implementar", Toast.LENGTH_SHORT).show()
        }

    }

}