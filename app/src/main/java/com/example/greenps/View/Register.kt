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
        binding.btnRegister.setOnClickListener {
            if (binding.txtEmailLogin.text.isNotEmpty() && binding.txtPassReg.text.isNotEmpty()) {
                mAuth!!.createUserWithEmailAndPassword(binding.txtEmailLogin.text.toString(), binding.txtPassReg.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val latlang:HashMap<String,String> = HashMap<String,String>()
                            latlang.put("nombre", binding.txtName.text.toString())
                            latlang.put("email", binding.txtEmailLogin.text.toString())
                            latlang.put("bio", "")
                            latlang.put("password", binding.txtPassReg.text.toString())
                            database.child("registros").push().setValue(latlang)
                            showTest(binding.txtEmailLogin.text.toString())
                        }else{
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showTest(email:String){

        val testIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
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