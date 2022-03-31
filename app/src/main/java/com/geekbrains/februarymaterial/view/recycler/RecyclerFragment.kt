package com.geekbrains.februarymaterial.view.recycler


import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerBinding

class RecyclerFragment: Fragment() {

    companion object {
        fun newInstance() = RecyclerFragment()
    }

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter : RecyclerFragmentAdapter //для diffutils выносим адаптер
    private var isNewList = false // какой список flag

    private lateinit var itemTouchHelper: ItemTouchHelper
    var flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        OneRecyclerTest()

    }

    private fun OneRecyclerTest() {

        /*для раскрывающего списка с примерами*/
        val lat = 20
        val lon = 23
        val time = 23

        val myCoordinate1 = lat to lon
        myCoordinate1.first// то же самое что и lat /состояние №1
        myCoordinate1.second// то же самое что и lon /состояние №1

        val myCoordinate2 = Pair(lat, lon)
        myCoordinate2.first// то же самое что и lat
        myCoordinate2.second// то же самое что и lon

        val myCoordinate3 = Triple(lat, lon, time)
        myCoordinate3.first// то же самое что и lat
        myCoordinate3.second// то же самое что и lon
        myCoordinate3.third// то же самое что и time


        val listData = arrayListOf(

            Pair(Data(id = 1,name = getString(R.string.earth), description =  "Дополнительный текст"), false),
            Pair(Data(id = 2,name = getString(R.string.earth), description =  "Дополнительный текст"), false),
            Pair(Data(id = 3,name = getString(R.string.mars), type = TYPE_MARS), false),
            Pair(Data(id = 4,name = getString(R.string.mars), type = TYPE_MARS), false)
            )

        listData.shuffle() //перемешиваем
        listData.add(0, Pair(Data(id = 0,name = getString(R.string.header), type = TYPE_HEADER), false)) //сетим хедер как элемент списка

        /*привязываем интерфейсы кликабельности*/
        adapter = RecyclerFragmentAdapter(object : RecyclerFragmentAdapter.OnClickItemListener { //просто кликабельность
            override fun onItemClick(data: Data) {
                Toast.makeText(context, "Мы супер ${data.name}", Toast.LENGTH_SHORT).show()
            }
        }, object : RecyclerFragmentAdapter.OnStartDragListener{ //перетаскивание за рычаг
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }
        })

        /*сетим в адаптер listData*/
        adapter.setData(listData)
        binding.recyclerView.adapter = adapter
        //binding.recyclerView.setHasFixedSize(true)

        binding.recyclerActivityFAB.setOnClickListener {
            flag = !flag
            if (flag) {
                ObjectAnimator.ofFloat(binding.recyclerActivityFAB, View.ROTATION, 0f, 405f)
                    .setDuration(2000L).start()
                adapter.appendItem() //по кнопке генерируем
            } else {
                ObjectAnimator.ofFloat(binding.recyclerActivityFAB, View.ROTATION, 405f, 0f)
                    .setDuration(2000L).start()
                adapter.appendItem()
            }
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount) //скролим к новым позициям в списке плавно
        }

        binding.recyclerDiffUltilsFAB.setOnClickListener {
            isNewList = !isNewList
            adapter.setData(createItemList(isNewList))
        }

        /*состояние перетаскивания из коробки*/
        //ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView) // связываем ItemTouchHelper с adapter на все элементы
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun createItemList(instanceNumber: Boolean): MutableList<Pair<Data,Boolean>>{
        return when(instanceNumber){
            false -> arrayListOf(
                Pair(Data(0,"header"),false),
                Pair(Data(1,"mars"),false),
                Pair(Data(2,"mars"),false),
                Pair(Data(3,"mars"),false),
                Pair(Data(4,"mars"),false)
            )
            true -> arrayListOf(
                Pair(Data(0,"header"),false),
                Pair(Data(1,"mars"),false),
                Pair(Data(2,"jupiter"),false),
                Pair(Data(3,"earth"),false),
                Pair(Data(4,"mars"),false)
            )
        }
    }

    /*перетаскиваение элемента списка в recyclerview из коробки*/
    class ItemTouchHelperCallback(val recyclerActivityAdapter: RecyclerFragmentAdapter):ItemTouchHelper.Callback(){

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            /*настраиваем как двигать можем элементы*/
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags,swipeFlags) //возвращаем функции
        }

        override fun onMove(
            recyclerView: RecyclerView,
            from: RecyclerView.ViewHolder,
            to: RecyclerView.ViewHolder
        ): Boolean {
            recyclerActivityAdapter.onItemMove(from.adapterPosition,to.adapterPosition) //движение с одной позиции на другую передаем адаптеру
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            recyclerActivityAdapter.onItemDismiss(viewHolder.adapterPosition) //по свайпу передаем удалении позиции
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if(viewHolder is RecyclerFragmentAdapter.MarsViewHolder) //если viewHolder у нас Mars то делаем то то то..
                if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE) // бездействие состояние
                    (viewHolder as RecyclerFragmentAdapter.MarsViewHolder).onItemSelected() //состоянрие нажатия
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            if(viewHolder is RecyclerFragmentAdapter.MarsViewHolder)
                (viewHolder as RecyclerFragmentAdapter.MarsViewHolder).onItemClear() // очистка состояния в адаптере
        }
    }
    /*перетаскиваение элемента списка в recyclerview*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}