package org.wit.greencommunity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.greencommunity.databinding.CardAdBinding
import org.wit.greencommunity.models.AdModel

interface AdListener {
    fun onAdClick(ad: AdModel)
}

class AdAdapter(private var ads: List<AdModel>, private val listener: AdListener) :
    RecyclerView.Adapter<AdAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardAdBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val ad = ads[holder.adapterPosition]
        holder.bind(ad, listener)
    }

    override fun getItemCount(): Int = ads.size

    class MainHolder(private val binding: CardAdBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ad: AdModel, listener: AdListener) {
            binding.adTitle.text = ad.title
            /*
            binding.adDescription.text = ad.description
            binding.adPrice.text = ad.price.toString()

             */
            binding.root.setOnClickListener { listener.onAdClick(ad)}
        }
    }
}