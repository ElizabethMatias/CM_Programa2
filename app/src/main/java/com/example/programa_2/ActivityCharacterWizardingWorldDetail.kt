package com.example.programa_2

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.programa_2.databinding.ActivityCharacterWizardingWorldDetailBinding
import com.example.programa_2.model.character_Wizarding_World_Detail
import com.example.programa_2.network.Hogwarts_API
import com.example.programa_2.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityCharacterWizardingWorldDetail : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterWizardingWorldDetailBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterWizardingWorldDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle=intent.extras
        val id_character= bundle?.getString("id", "")
        if (id_character != null){
            val call = RetrofitService.getRetrofit().create(Hogwarts_API::class.java).getCharacterId(id_character)

            call.enqueue(object: Callback<ArrayList<character_Wizarding_World_Detail>> {
                override fun onResponse(
                    call: Call<ArrayList<character_Wizarding_World_Detail>>,
                    response: Response<ArrayList<character_Wizarding_World_Detail>>
                ) {
                    binding.pbCon.visibility = View.GONE
                    binding.tvTitle.text = response.body()!![0].name.toString()
                    if(response.body()!![0].wizard.toString() != "false")
                        if(response.body()!![0].gender.toString() == "male")
                            binding.tvWizardValue.text = "Mago"
                        else if(response.body()!![0].gender.toString() == "female")
                            binding.tvWizardValue.text = "Bruja"
                        else
                            binding.tvWizardValue.text = "No es mago"
                    else
                        binding.tvWizardValue.text = "No es mago"

                    if(response.body()!![0].species.toString() !=  "")
                        binding.tvSpeciesValue.text = response.body()!![0].species ?: ""
                    else
                        binding.tvSpeciesValue.text = "No hay información"

                    if(response.body()!![0].house.toString() !=  "")
                        binding.tvHouseValue.text = response.body()!![0].house?: ""
                    else
                        binding.tvHouseValue.text = "No cuenta con casa"

                    if(response.body()!![0].ancestry.toString() !=  "")
                        binding.tvAncestryValue.text = response.body()!![0].ancestry ?: ""
                    else
                        binding.tvAncestryValue.text= "No hay información"

                    if(response.body()!![0].alive.toString() == "true")
                        binding.tvAliveValue.text = "Vivo"
                    else
                        binding.tvAliveValue.text = "Fallecido"

                    if(response.body()!![0].patronus.toString() != "")
                        binding.tvPatronusValue.text = response.body()!![0].patronus ?: ""
                    else
                        binding.tvPatronusValue.text = "No tiene patronus"

                    if (response.body()!![0].wand?.wood.toString()== "" &&
                            response.body()!![0].wand?.core.toString()== "" &&
                            response.body()!![0].wand?.length.toString()== "" ){
                        binding.tvWandWoodValue.text = "No cuenta con varita"
                        binding.tvWandCoreValue.text = ""
                        binding.tvWandLengthValue.text =""}
                    else if (response.body()!![0].wand != null) {
                        if (response.body()!![0].wand?.wood.toString() != "")
                            binding.tvWandWoodValue.text =  "Madera " +response.body()!![0].wand?.wood
                        else
                            binding.tvWandWoodValue.text = "Madera No definida"

                        if (response.body()!![0].wand?.core.toString() != "")
                            binding.tvWandCoreValue.text =  "Núcleo " +response.body()!![0].wand?.core
                        else
                            binding.tvWandCoreValue.text = "Núcleo No definido"

                        if (response.body()!![0].wand?.length.toString() != "")
                            binding.tvWandLengthValue.text =  "Longitud " +response.body()!![0].wand?.length
                        else
                            binding.tvWandLengthValue.text = "Longitud No definida"
                    }
                    if (response.body()!![0].dateOfBirth != null)
                        binding.tvDataOfBirthValue.text = response.body()!![0].dateOfBirth ?: ""
                    else
                        binding.tvDataOfBirthValue.text = "No hay información"

                    if (response.body()!![0].image != "") {
                        Glide.with(this@ActivityCharacterWizardingWorldDetail)
                            .load(response.body()!![0].image)
                            .into(binding.ivImage)
                    }
                    else {
                        Glide.with(this@ActivityCharacterWizardingWorldDetail)
                            .load(R.drawable.profile)
                            .into(binding.ivImage)
                    }
                }

                override fun onFailure(call: Call<ArrayList<character_Wizarding_World_Detail>>, t: Throwable) {
                    binding.pbCon.visibility = View.GONE
                    Toast.makeText(this@ActivityCharacterWizardingWorldDetail,
                        "No se puede consultar este personaje de momento",
                        Toast.LENGTH_LONG).show()
                    finish()
                }

            })
        }

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

        mediaPlayer = MediaPlayer.create(this, R.raw.music4)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }


}