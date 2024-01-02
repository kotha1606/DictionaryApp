package com.example.project004

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project004.databinding.RecyclerRowMeaningBinding

class MeaningAdapter(private var meaningList: List<Meaning>) :
    RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    inner class MeaningViewHolder(private val binding: RecyclerRowMeaningBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meaning: Meaning) {
            binding.partofSpechTv.text = meaning.partOfSpeech
            binding.definationItemsTv.text = meaning.definitions.joinToString("\n\n") {
                val currentIndex = meaning.definitions.indexOf(it)
                (currentIndex + 1).toString() + ". " + it.definition
            }
            if(meaning.synonyms.isEmpty()){
                binding.synonysTitleTv.visibility=View.GONE
                binding.synonymsItemsTV.visibility=View.GONE
            }else{
                binding.synonysTitleTv.visibility=View.VISIBLE
                binding.synonymsItemsTV.visibility=View.VISIBLE
                binding.synonymsItemsTV.text=meaning.synonyms.joinToString(separator = ",")
            }
            if(meaning.antonyms.isEmpty()){
                binding.antonymsTitleTv.visibility=View.GONE
                binding.antonymsItemsTV.visibility=View.GONE
            }else{
                binding.antonymsItemsTV.visibility=View.VISIBLE
                binding.antonymsTitleTv.visibility=View.VISIBLE
                binding.antonymsTitleTv.text=meaning.synonyms.joinToString(",")
            }


        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMeaningList: List<Meaning>) {
        meaningList = newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding =
            RecyclerRowMeaningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
       holder.bind(meaningList[position])
    }

}