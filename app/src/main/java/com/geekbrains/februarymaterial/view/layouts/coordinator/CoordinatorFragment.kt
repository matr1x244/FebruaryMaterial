package com.geekbrains.februarymaterial.view.layouts.coordinator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentCoordinatorBinding
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel

class CoordinatorFragment : Fragment() {

    private var _binding: FragmentCoordinatorBinding? = null
    private val binding: FragmentCoordinatorBinding
        get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(
        PictureOfTheDayViewModel::class.java) }

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
        testServer()
    }

    private fun testServer() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequest()
    }

    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {
                binding.fragmentCoordinator.visibility = View.GONE
            }
            is PictureOfTheDayAppState.Loading -> {
                //....
            }
            is PictureOfTheDayAppState.Success -> {
                binding.textView.text = pictureOfTheDayAppState.serverResponseData.explanation
                binding.mainBackdrop.load(pictureOfTheDayAppState.serverResponseData.url){
                    error(R.drawable.ic_load_error_vector)
                }
                binding.fragmentCoordinator.visibility = View.VISIBLE

            }
        }
    }

    /*Создаем логику для исчезновения кнопки*/
    private fun invisibleBtn() {
        val behavior = ButtonBehavior(requireContext())
        (binding.myButton.getLayoutParams() as CoordinatorLayout.LayoutParams).behavior = behavior
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoordinatorFragment()
    }



}