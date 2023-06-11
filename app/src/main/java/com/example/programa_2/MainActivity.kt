package com.example.programa_2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.programa_2.adapters.CWWAdapter
import com.example.programa_2.databinding.ActivityMainBinding
import com.example.programa_2.model.Character_Wizarding_World
import com.example.programa_2.network.Hogwarts_API
import com.example.programa_2.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if (bundle != null) {
            val option = bundle.getString("option", "")
            val call: Call<ArrayList<Character_Wizarding_World>>

            call = when (option) {
                "students" -> {
                    RetrofitService.getRetrofit().create(Hogwarts_API::class.java)
                        .getStudents()
                }
                "professors" -> {
                    RetrofitService.getRetrofit().create(Hogwarts_API::class.java)
                        .getStaff()
                }
                else -> {
                    return
                }
            }

            call.enqueue(object: Callback<ArrayList<Character_Wizarding_World>> {

                override fun onResponse(
                    call: Call<ArrayList<Character_Wizarding_World>>,
                    response: Response<ArrayList<Character_Wizarding_World>>
                ) {
                    binding.pbConexion.visibility = View.GONE

                    binding.characterMain.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.characterMain.adapter = CWWAdapter(this@MainActivity,
                        response.body()!!
                    ) { characterSelected: Character_Wizarding_World->
                        mainSelect(characterSelected)
                    }
                }

                //Función de conexión a la API no exitosa.
                override fun onFailure(call: Call<ArrayList<Character_Wizarding_World>>, t: Throwable) {
                    Toast.makeText(this@MainActivity,
                        "No hay conexión...", Toast.LENGTH_SHORT).show()
                    binding.pbConexion.visibility = View.GONE //Se quita el símbolo de cargando.
                    Log.d("LOGTAG", "Error")
                }
            })

        } else {
            Log.d("LOGTAG", "No se recibió nada...")
        }

    }

    private  fun mainSelect(character: Character_Wizarding_World) {
        val bundle = Bundle()
        bundle.putString("id", character.id)

        val intent = Intent(this, ActivityCharacterWizardingWorldDetail::class.java)
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

        mediaPlayer = MediaPlayer.create(this, R.raw.musica1)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

}