package com.example.project004

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project004.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchBtn.setOnClickListener {
            val word = binding.etTxt.text.toString()
            callApi(word)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApi(word: String) {
        GlobalScope.launch {
            val response = RetrofitInstance.apiInterface.dictcall(word)
            Log.i("responsefromApi",response.body().toString())
        }
    }

}
