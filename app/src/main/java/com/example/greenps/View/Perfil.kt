package com.example.greenps.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenps.R
import com.example.greenps.databinding.ActivityPerfilBinding
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
        val email = bundle?.getString("email")
        //bsetup(email?: "")
    }

    /*private fun setup(email:String){
        database.child("registros").child(email).get().addOnSuccessListener {
            binding.txtNameProfile.text = it.child("nombre").value.toString()
        }
    }*/

}