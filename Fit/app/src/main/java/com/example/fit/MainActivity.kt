package com.example.fit

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Creo el binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnMenu.setOnClickListener {

            // Abro la view del menu
            val intent = Intent(this, MainActivity2::class.java)
            // Lanzo la nueva activity
            startActivity(intent)
        }
    }
}