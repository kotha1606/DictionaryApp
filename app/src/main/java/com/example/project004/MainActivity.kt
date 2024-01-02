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
    lateinit var binding: ActivityMainBinding
    lateinit var adapter:MeaningAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            var word = binding.etTxt.text.toString()
            hideKeyboard(binding.etTxt)
            if (word.isNullOrBlank()) {
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
            runOnUiThread() {
                setInProgress(false)
                response.body()?.first().let {
                    Log.i("Api",it.toString())
                }
            }
        }
    }
    private fun setui(it:DictmodelItem) {
        binding.word.text = it.word
        binding.phonetic.text = it.phonetic
        var url=it.phonetics.first().audio
        binding.sound.setOnClickListener(){
            val media=MediaPlayer()
            media.setDataSource(url)
            media.prepare()
            media.start()
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

