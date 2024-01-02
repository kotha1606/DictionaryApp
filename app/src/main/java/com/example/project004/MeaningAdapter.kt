package com.example.project004

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project004.databinding.RecyclerRowMeaningBinding

class MeaningAdapter(private var meaninglist:List<Meaning>):RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    inner class MeaningViewHolder(private val binding: RecyclerRowMeaningBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(meaning: Meaning){
            binding.partofSpechTv.text=meaning.partOfSpeech
            binding.definationItemsTv.text=meaning.definitions.joinToString("\n\n") {
                var currentIndex=meaning.definitions.indexOf(it)
                (currentIndex+1).toString()+". "+it.definition.toString() }
        }
    }

    fun updateData(newMeaningList: List<Meaning>){
        meaninglist=newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        var binding= RecyclerRowMeaningBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
     return meaninglist.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
     holder.bind(meaninglist[position])
    }
}