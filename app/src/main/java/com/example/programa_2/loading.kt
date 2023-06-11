package com.example.programa_2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.thread
import com.example.programa_2.databinding.ActivityLoadingBinding

class loading : AppCompatActivity() {

    private lateinit var binding:ActivityLoadingBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.music3)
        mediaPlayer.isLooping = true

        thread {
            mediaPlayer.start()
            Thread.sleep(3000)
            val intent = Intent(this, characterWizardingWorld::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Detener y liberar el MediaPlayer al cerrar la actividad
        mediaPlayer.release()
    }
}