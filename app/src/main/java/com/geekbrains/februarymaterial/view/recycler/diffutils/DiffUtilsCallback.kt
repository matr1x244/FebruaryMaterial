package com.geekbrains.februarymaterial.view.recycler.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.geekbrains.februarymaterial.view.recycler.Data

/*сравниваем по ID элементы списка*/

class DiffUtilsCallback(
    /*создаем два списка*/
    private val oldItems: List<Pair<Data,Boolean>>,
    private val newItems: List<Pair<Data,Boolean>>)

    : DiffUtil.Callback() {

    /*сравниваем старый и новы список по размеру*/
    override fun getOldListSize(): Int{
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    /*ищем различия по конкретным данным*/
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        //если нужно сравнивать множество параметров
/*        return (oldItems[oldItemPosition].first.name == newItems[newItemPosition].first.name)
                &&(oldItems[oldItemPosition].first.description == newItems[newItemPosition].first.description)*/
        //сраваниваем только по name
        return oldItems[oldItemPosition].first.name == newItems[newItemPosition].first.name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return Change(oldItem,newItem)
    }
}