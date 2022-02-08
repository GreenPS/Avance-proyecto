package com.example.greenps.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.greenps.MainActivity
import com.example.greenps.R
import com.example.greenps.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference
    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }
    private fun setup() {
        database = Firebase.database.reference
        database.child("registros").child(binding.txtEmailLogin.text.toString())
        binding.btnRegister.setOnClickListener {
            if (binding.txtEmailLogin.text.isNotEmpty() && binding.txtPassReg.text.isNotEmpty()) {
                mAuth!!.createUserWithEmailAndPassword(binding.txtEmailLogin.text.toString(), binding.txtPassReg.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            database!!.child("Nombre").setValue(binding.txtName.text)
                            database!!.child("Bio").setValue("")
                            database!!.child("Email").setValue(binding.txtEmailLogin.text)
                            database!!.child("Pass").setValue(binding.txtPassReg.text)
                            showTest()
                        }else{
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showTest(){

        val testIntent = Intent(this, MainActivity::class.java).apply {
        }
        startActivity(testIntent)
    }

    private fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}