package com.geekbrains.februarymaterial.view.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.databinding.FragmentChipsBinding

class ChipsFragment : Fragment() {
    private var _binding: FragmentChipsBinding? = null
    private val binding: FragmentChipsBinding
        get() = _binding!!

    companion object {
        fun newInstance() = ChipsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        *//*Кликабельность chips для group *//*
        binding.chipGroup.setOnCheckedChangeListener{ group, checkedId ->
            binding.chipGroup.findViewById<Chip>(checkedId)?.let{
            //Toast.makeText(requireContext(),"chip $checkedId ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}