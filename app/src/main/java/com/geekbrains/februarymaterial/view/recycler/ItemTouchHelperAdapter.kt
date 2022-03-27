package com.geekbrains.februarymaterial.view.recycler

/*interface для перетаскивания элемента писка*/

interface ItemTouchHelperAdapter {
        fun onItemMove(fromPosition:Int, toPosition:Int) // движение с позиции по позиции
        fun onItemDismiss(position:Int) //удаление позиции
    }


/*Изменение состояния для viewHolder*/

interface ItemTouchHelperViewHolder {
        fun onItemSelected() // тестово изменить состояние например цвет
        fun onItemClear() //отменить состояние удержания
    }
