package com.geekbrains.februarymaterial.view.layouts.constraint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.februarymaterial.databinding.FragmentConstraintBinding
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel


class ConstraintFragment : Fragment() {

    private var _binding: FragmentConstraintBinding? = null
    private val binding: FragmentConstraintBinding
        get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(
        PictureOfTheDayViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConstraintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testConstraint()
        testServer()

    }

    private fun testServer() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequest()
    }

   private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {
                binding.fragmentConstraint.visibility = View.GONE
            }
            is PictureOfTheDayAppState.Loading -> {
                //....
            }
            is PictureOfTheDayAppState.Success -> {
                binding.textView.text = pictureOfTheDayAppState.serverResponseData.title
                binding.btnOne.text = pictureOfTheDayAppState.serverResponseData.date
                binding.fragmentConstraint.visibility = View.VISIBLE
            }
        }
    }

    private fun testConstraint() {
        binding.groupBtn.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConstraintFragment()
    }



}