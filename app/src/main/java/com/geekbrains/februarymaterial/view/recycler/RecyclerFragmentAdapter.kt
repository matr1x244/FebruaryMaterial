package com.geekbrains.februarymaterial.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerItemEarthBinding
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerItemHeaderBinding
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerItemMarsBinding


class RecyclerFragmentAdapter (val onClickItemListener: OnClickItemListener):RecyclerView.Adapter<RecyclerFragmentAdapter.BaseViewHolder>() {

    private lateinit var listData: MutableList<Data>

    fun setData(listData: MutableList<Data>) {
        this.listData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding = FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EarthViewHolder(binding.root)
            }
            TYPE_HEADER-> {
                val binding = FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                HeaderViewHolder(binding.root)
            }
            else -> {
                val binding = FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MarsViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun getItemCount() = listData.size


    fun appendItem() { //метод генерации нового элемента
        listData.add(generateData())
        listData.add(Data("TEST","TEST DESCRIPTION",type = TYPE_EARTH))
        //notifyDataSetChanged() //заливаем и перерисовываем полностью все изменения (перезагрузит весь RecyclerView)
        notifyItemInserted(listData.size-1) //вставляем новые данные в -1 позицию в списке плавно с анимацией
    }


    fun generateData() = Data("NEW MARS", type = TYPE_MARS) //не совсем понял а смысл так выносить если одни данные

    abstract class BaseViewHolder(view:View):RecyclerView.ViewHolder(view){
        abstract fun bind(data: Data)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
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

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) { //RecyclerView.ViewHolder
        override fun bind(data: Data) {
            FragmentRecyclerItemMarsBinding.bind(itemView).apply {
                tvName.text = data.name
                ivMars.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
                addItemImageView.setOnClickListener {
                    listData.add(layoutPosition, generateData())
                    notifyItemInserted(layoutPosition)
                }
                removeItemImageView.setOnClickListener {
                    listData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                moveItemUp.setOnClickListener {
                    /*Фиксируем что выше header (0) нельзя подняться*/
                    if (layoutPosition > 1) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition - 1, this)
                            notifyItemMoved(layoutPosition, layoutPosition - 1)
                        }
                    }
                }
                moveItemDown.setOnClickListener {
                    /*Фиксируем что есть конец списка и нельзя уйти ниже*/
                    if (layoutPosition < listData.size - 1) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition + 1, this)
                            notifyItemMoved(layoutPosition, layoutPosition + 1)
                        }
                    }
                }
            }
        }
    }

    inner class HeaderViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Data){
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                tvName.text = data.name
                itemView.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    fun interface OnClickItemListener {
        fun onItemClick(data: Data) //по позиции из списка
    }
}


