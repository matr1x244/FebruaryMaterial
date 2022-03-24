package com.geekbrains.februarymaterial.view.recycler

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentRecyclerBinding
import kotlinx.coroutines.delay

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
        val listData = arrayListOf(
            Data(getString(R.string.earth), "Дополнительный текст"),
            Data(getString(R.string.earth), "Дополнительный текст"),
            Data(getString(R.string.earth), "Дополнительный текст"),
            Data(getString(R.string.earth), "Дополнительный текст"),
            Data(getString(R.string.earth), "Дополнительный текст"),
            Data(getString(R.string.mars), type = TYPE_MARS),
            Data(getString(R.string.mars), type = TYPE_MARS),
            Data(getString(R.string.mars), type = TYPE_MARS),
            Data(getString(R.string.mars), type = TYPE_MARS),
            Data(getString(R.string.mars), type = TYPE_MARS),
            Data(getString(R.string.mars), type = TYPE_MARS),
            Data(getString(R.string.mars), type = TYPE_MARS)
        )
        listData.shuffle() //перемешиваем
        listData.add(0,Data(getString(R.string.header), type = TYPE_HEADER)) //сетим хедер как элемент списка

        val adapter = RecyclerFragmentAdapter { dataClick ->
            Toast.makeText(context,"Мы супер ${dataClick.name}", Toast.LENGTH_SHORT).show()
        }

        adapter.setData(listData)
        binding.recyclerView.adapter = adapter

        binding.recyclerActivityFAB.setOnClickListener {
            flag = !flag
            if(flag){
                    ObjectAnimator.ofFloat( binding.recyclerActivityFAB,View.ROTATION,0f,405f).setDuration(2000L).start()
                    adapter.appendItem() //по кнопке генерируем
            }else{
                    ObjectAnimator.ofFloat( binding.recyclerActivityFAB,View.ROTATION,405f,0f).setDuration(2000L).start()
                    adapter.appendItem()
            }
                 binding.recyclerView.smoothScrollToPosition(adapter.itemCount) //скролим к новым позициям в списке
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}