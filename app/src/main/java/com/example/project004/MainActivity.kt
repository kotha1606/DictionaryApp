package com.example.project004

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project004.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter:MeaningAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sound.visibility=View.INVISIBLE
        binding.searchBtn.setOnClickListener {
            val word = binding.etTxt.text.toString()
            hideKeyboard(binding.etTxt)
            if (word.isBlank()) {
                binding.etTxt.error = "Please enter word to search"
            } else {
                callApi(word)
            }
        }
        //recycler view setup
        adapter= MeaningAdapter(emptyList())
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter

    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun callApi(word: String) {
        setInProgress(true)
        GlobalScope.launch {
            val response = RetrofitInstance.apiInterface.dictcall(word)
            runOnUiThread {
                setInProgress(false)
                response.body()?.first()?.let {
                    setUi(it)
                }
            }
        }
    }
    private fun setUi(it:WordResult) {
        binding.word.text = it.word

        val phono= it.phonetic.toString()
             Log.i("ApiTag",phono)
            binding.phonetic.text =phono

        val url = it.phonetics.firstOrNull { phonetic -> phonetic.audio.isNotEmpty() }?.audio
        binding.sound.visibility=View.VISIBLE
        binding.sound.setOnClickListener {
            if (!url.isNullOrBlank()) {
                try {
                    val media = MediaPlayer()
                    media.setDataSource(url)
                    media.prepare()
                    media.start()
                } catch (e: Exception) {
                    Log.i("ApiTag", url)
                    Log.e("ApiTag", "Error ${e.localizedMessage}")
                }
            } else {
                Log.i("ApiTag", "No audio URL found.")
            }
        }
        adapter.updateData(it.meanings)
    }


    private fun setInProgress(inprogress: Boolean) {
        if (inprogress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.searchBtn.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.searchBtn.visibility = View.VISIBLE
        }
    }
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

