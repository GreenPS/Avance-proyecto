package com.example.greenps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenps.databinding.ActivityAuthBinding
import com.example.greenps.databinding.ActivityTestBinding
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_test)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String){
        binding.emailTextView.text = email
        binding.passwordTextView.text = provider

        binding.logOutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}