package com.geekbrains.februarymaterial.view.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentMarsBinding
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel


class MarsFragment : Fragment() {


    private var _binding: FragmentMarsBinding? = null
    val binding: FragmentMarsBinding
        get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(
        PictureOfTheDayViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerMarsDay()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {
                binding.fragmentMars.visibility = View.GONE
            }
            is PictureOfTheDayAppState.Loading -> {
            //....
            }
            is PictureOfTheDayAppState.Success -> {
                binding.imageViewMars.load(pictureOfTheDayAppState.serverResponseData.url){
                    error(R.drawable.ic_load_error_vector)
                }
                binding.imageViewMars.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(pictureOfTheDayAppState.serverResponseData.url)
                    })
                }
                binding.imageViewMars.visibility = View.VISIBLE
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }
}