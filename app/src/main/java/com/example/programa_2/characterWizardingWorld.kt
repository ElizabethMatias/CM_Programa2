package com.example.programa_2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class characterWizardingWorld : AppCompatActivity() {

    private lateinit var option: String
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_wizarding_world)
    }

    fun students(view: View) {
        option = "students"
        getCharactersWizardingWorld()
    }

    fun professors(view: View) {
        option = "professors"
        getCharactersWizardingWorld()
    }

    fun getCharactersWizardingWorld() {
        val intent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString("option", option)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }

    override fun onResume() {
        super.onResume()

        // Si el objeto MediaPlayer no es nulo, reiniciarlo
        if (mediaPlayer != null) {
            mediaPlayer!!.seekTo(0)
            mediaPlayer!!.start()
        }
    }

    override fun onStart() {
        super.onStart()

        mediaPlayer = MediaPlayer.create(this, R.raw.music2)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }
}