package com.example.greenps.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.greenps.MainActivity
import com.example.greenps.R
import com.example.greenps.databinding.ActivityUserPassBinding
import com.google.firebase.auth.FirebaseAuth

class UserPass : AppCompatActivity() {
    private lateinit var binding: ActivityUserPassBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        binding = ActivityUserPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup1()
    }

    private fun setup1() {
        binding.btnLogin.setOnClickListener {
            if (binding.txtEmailLogin.text.isNotEmpty() && binding.txtPassLogin.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.txtEmailLogin.text.toString(),
                    binding.txtPassLogin.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //string vacio, no deberia pasar
                        showTest(it.result?.user?.email ?: "", MainActivity.ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showTest(email: String, provider: MainActivity.ProviderType){

        val testIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
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