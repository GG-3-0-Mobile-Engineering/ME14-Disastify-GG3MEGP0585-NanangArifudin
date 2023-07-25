package com.arfdn.disastify.presentation.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arfdn.disastify.data.model.DisasterDummy
import com.arfdn.disastify.databinding.ItemDisasterBinding
import com.arfdn.disastify.utils.loadImage

typealias OnClickDisaster = (DisasterDummy) -> Unit
class DisasterAdapter(private val listDisaster: List<DisasterDummy>, private val onClickDisaster: OnClickDisaster) :
    RecyclerView.Adapter<DisasterAdapter.ItemDisasterViewHolder>() {
    inner class ItemDisasterViewHolder(private val binding: ItemDisasterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DisasterDummy) {
            with(binding) {
                txtDisasterName.text = data.nameDisaster
                txtDisasterType.text = data.disasterType
                imgDisaster.loadImage(itemView.context, data.disasterImage)
                itemView.setOnClickListener {
                    onClickDisaster(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDisasterViewHolder {
        val binding = ItemDisasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDisasterViewHolder(binding)
    }

    override fun getItemCount(): Int = listDisaster.size

    override fun onBindViewHolder(holder: ItemDisasterViewHolder, position: Int) {
        holder.bind(listDisaster[position])
    }
}