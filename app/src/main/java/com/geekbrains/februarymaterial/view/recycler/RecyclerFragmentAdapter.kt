package com.geekbrains.februarymaterial.view.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerItemEarthBinding
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerItemHeaderBinding
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerItemMarsBinding


class RecyclerFragmentAdapter (val onClickItemListener: OnClickItemListener,val onStartDragListener: OnStartDragListener): RecyclerView.Adapter<RecyclerFragmentAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    private lateinit var listData: MutableList<Pair<Data, Boolean>>

    fun setData(listData: MutableList<Pair<Data, Boolean>>) {
        this.listData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding = FragmentRecyclerItemEarthBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                EarthViewHolder(binding.root)
            }
            TYPE_HEADER -> {
                val binding = FragmentRecyclerItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding.root)
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }

    override fun getItemCount() = listData.size


    fun appendItem() { //метод генерации нового элемента
        listData.add(generateData())
        listData.add(Pair(Data("TEST", "TEST DESCRIPTION", type = TYPE_EARTH), false))

        //notifyDataSetChanged() //заливаем и перерисовываем полностью все изменения (перезагрузит весь RecyclerView)
        notifyItemInserted(listData.size - 1) //вставляем новые данные в -1 позицию в списке плавно с анимацией
    }


    fun generateData() = Pair(Data("NEW MARS", type = TYPE_MARS), false) //не совсем понял а смысл так выносить если одни данные

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<Data, Boolean>)
    }

    /*двигаем состояние элемента писка*/
    /*Адаптер отслеживает  движение*/
    override fun onItemMove(fromPosition: Int, toPosition: Int) { //двигаем удаляя из позиции и добавляем в позицию
        listData.removeAt(fromPosition).apply {
            listData.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }
    /*Адаптер отслеживает удаление*/
    override fun onItemDismiss(position: Int) {
        listData.removeAt(position) //в listdate, удаляем элемент по позиции
        notifyItemRemoved(position) //обновляем после удаления
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                tvName.text = data.first.name
                itemView.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
            }
        }
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            FragmentRecyclerItemEarthBinding.bind(itemView).apply {
                tvName.text = data.first.name
                tvName.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
                tvDescription.text = data.first.description
                ivEarth.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view),
        ItemTouchHelperViewHolder { //RecyclerView.ViewHolder
        override fun bind(data: Pair<Data, Boolean>) {
            FragmentRecyclerItemMarsBinding.bind(itemView).apply {
                tvName.text = data.first.name
                ivMars.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
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
                /*скрываем описание взависимости от состояние first или second*/
                marsDescriptionTextView.visibility = if (listData[layoutPosition].second) View.VISIBLE else View.GONE
                /*раскрываем по клику взависимости от состояния*/
                itemView.setOnClickListener {
                    listData[layoutPosition] =
                        listData[layoutPosition].let { statusVisibleTextView ->
                            statusVisibleTextView.first to !statusVisibleTextView.second //состояния
                        }
                    notifyItemChanged(layoutPosition)
                }
                /*рычаг передвижения*/
                dragHandleImageView.setOnTouchListener { v, event ->
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) { //если нажали кнопку
                            onStartDragListener.onStartDrag(this@MarsViewHolder)
                    }
                    false
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.CYAN)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }


    /*interface кликабельности*/
    fun interface OnClickItemListener {
        fun onItemClick(data: Data) //по позиции из списка
    }

    /*Перетаскивание элемент по рычагу*/
    fun interface OnStartDragListener {
        fun onStartDrag(view: RecyclerView.ViewHolder)
    }
}


