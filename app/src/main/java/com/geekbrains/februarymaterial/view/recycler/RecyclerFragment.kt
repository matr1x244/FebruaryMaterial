package com.geekbrains.februarymaterial.view.recycler

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
            Data(getString(R.string.mars), type = TYPE_MARS),
            Data(getString(R.string.mars), type = TYPE_MARS),
            Data(getString(R.string.mars), type = TYPE_MARS)
        )
        listData.shuffle() //перемешиваем

        val adapter = RecyclerFragmentAdapter { dataClick ->
            Toast.makeText(context,"Мы супер ${dataClick.name}", Toast.LENGTH_SHORT).show()
        }

        adapter.setData(listData)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}