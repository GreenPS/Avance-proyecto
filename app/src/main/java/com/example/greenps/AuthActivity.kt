package com.example.greenps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.greenps.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //splash
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_auth)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        binding.signUpButton.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //string vacio, no deberia pasar
                        showMain()
                        //showTest(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
        binding.logInButton.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //string vacio, no deberia pasar
                        showMain()
                        //showTest(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlert1(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("flag")
        builder.setMessage("flagg")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showTest(email: String, provider: ProviderType){

        val testIntent = Intent(this, TestActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(testIntent)
    }

    private fun showMain(){
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }
}