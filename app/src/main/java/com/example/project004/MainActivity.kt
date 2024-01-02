package com.example.project004

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project004.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val word="world"
        GlobalScope.launch {

            val response= RetrofitInstance.apiInterface.dictcall(word)
            runOnUiThread {

                response.body()?.first()?.let{
                    binding.hello.text= it.phonetic

                }
            }
        }

    }
}
