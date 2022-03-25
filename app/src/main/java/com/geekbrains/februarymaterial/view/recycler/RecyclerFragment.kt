package com.geekbrains.februarymaterial.view.recycler


import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerBinding

class RecyclerFragment: Fragment() {

    companion object {
        fun newInstance() = RecyclerFragment()
    }
    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!

    var flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerBinding.inflate(inflater,container,false)
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

            Pair(Data(getString(R.string.earth), "Дополнительный текст"),false),
            Pair(Data(getString(R.string.earth), "Дополнительный текст"),false),
            Pair(Data(getString(R.string.earth), "Дополнительный текст"),false),
            Pair(Data(getString(R.string.earth), "Дополнительный текст"),false),
            Pair(Data(getString(R.string.mars), type = TYPE_MARS),false),
            Pair(Data(getString(R.string.mars), type = TYPE_MARS),false),
            Pair(Data(getString(R.string.mars), type = TYPE_MARS),false),

        )
        listData.shuffle() //перемешиваем
        listData.add(0,Pair(Data(getString(R.string.header), type = TYPE_HEADER),false)) //сетим хедер как элемент списка

        val adapter = RecyclerFragmentAdapter { dataClick ->
            Toast.makeText(context,"Мы супер ${dataClick.name}", Toast.LENGTH_SHORT).show()
        }

        adapter.setData(listData)
        binding.recyclerView.adapter = adapter
        //binding.recyclerView.setHasFixedSize(true)

        binding.recyclerActivityFAB.setOnClickListener {
            flag = !flag
            if(flag){
                    ObjectAnimator.ofFloat( binding.recyclerActivityFAB,View.ROTATION,0f,405f).setDuration(2000L).start()
                    adapter.appendItem() //по кнопке генерируем
            }else{
                    ObjectAnimator.ofFloat( binding.recyclerActivityFAB,View.ROTATION,405f,0f).setDuration(2000L).start()
                    adapter.appendItem()
            }
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount) //скролим к новым позициям в списке плавно
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}