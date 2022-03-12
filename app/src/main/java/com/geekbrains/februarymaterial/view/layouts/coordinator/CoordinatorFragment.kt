package com.geekbrains.februarymaterial.view.layouts.coordinator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.databinding.FragmentCoordinatorBinding

class CoordinatorFragment : Fragment() {

    private var _binding: FragmentCoordinatorBinding? = null
    private val binding: FragmentCoordinatorBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        invisibleBtn()
    }

    private fun invisibleBtn() {
        val behavior = ButtonBehavior(requireContext())
        (binding.myButton.getLayoutParams() as CoordinatorLayout.LayoutParams).behavior = behavior
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoordinatorFragment()
    }



}