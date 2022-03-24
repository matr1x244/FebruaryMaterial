package com.geekbrains.februarymaterial.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerItemEarthBinding
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerItemMarsBinding

class RecyclerFragmentAdapter (val onClickItemListener: OnClickItemListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var listData: List<Data>

    fun setData(listData: List<Data>) {
        this.listData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding = FragmentRecyclerItemEarthBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                EarthViewHolder(binding.root)
            }
            else -> {
                val binding = FragmentRecyclerItemMarsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MarsViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).bind(listData[position])
            }
            else -> {
                (holder as MarsViewHolder).bind(listData[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun getItemCount() = listData.size


    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecyclerItemEarthBinding.bind(itemView).apply {
                tvName.text = data.name
                tvName.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
                tvDescription.text = data.description
                ivEarth.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecyclerItemMarsBinding.bind(itemView).apply {
                tvName.text = data.name
                ivMars.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    fun interface OnClickItemListener {
        fun onItemClick(data: Data) //по позиции из списка
    }
}


