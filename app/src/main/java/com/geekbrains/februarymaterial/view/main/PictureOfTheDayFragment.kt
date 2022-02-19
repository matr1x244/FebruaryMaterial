package com.geekbrains.februarymaterial.view.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import coil.load
import com.geekbrains.februarymaterial.ExtensionFun.showSnackBarNoAction
import com.geekbrains.februarymaterial.ExtensionFun.showToastShort
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentMainBinding
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PictureOfTheDayFragment : Fragment() {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java) } // почему так?? а не просто: private val viewModel: PictureOfTheDayViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*Выдвижная панель*/
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<ConstraintLayout> //<Всегда нужен контейнер>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequest()

        /*Сетим в поиск введёный текст в поиск WIKIPEDIA*/
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                binding.mainLayout.showToastShort("Поехали искать ${binding.inputEditText.text.toString()}")
            })
        }

        /*Выдвижная панель*/
        bottomSheetBehavior = BottomSheetBehavior.from(binding.includedBsl.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED // Задаем как выдвигать
        bottomSheetBehavior.setPeekHeight(200,true) //Выдвигаем только на 200 вверх
        bottomSheetBehavior.setHideable(false) //указываем можно скрыть или нет


        /*Ловим как выдвигается состояния*/
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                     /*НАДО ИЗУЧИТЬ ПРИ КАКИХ ДЕЙСТВИЯХ ЧТО БУДЕТ*/

                    /*BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
                    BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
                    BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")*/
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("onSlide", "$slideOffset slideOffset")
                binding.fragmentMain.showToastShort("$slideOffset")
            }
        })

    }

    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {
                binding.progressBarMain.visibility = View.VISIBLE
                binding.fragmentMain.visibility = View.GONE
                binding.cardViewMain.visibility = View.INVISIBLE
                binding.includedBsl.bottomSheetContainer.visibility = View.INVISIBLE
                binding.fragmentMain.showSnackBarNoAction("Ошибка загрузки") // Хотелось бы получить ответ как заставить если ошибка перезапустить запрос, надо использовать showSnackBarAction, но не могу понять после текста как сделать :(
            }
            is PictureOfTheDayAppState.Loading -> {
                binding.progressBarMain.visibility = View.VISIBLE
                binding.includedBsl.bottomSheetContainer.visibility = View.INVISIBLE
                binding.cardViewMain.visibility = View.INVISIBLE
                binding.fragmentMain.showSnackBarNoAction("Загружаем")
                // TODO HW можно loaderbar
            }
            is PictureOfTheDayAppState.Success -> {
                binding.progressBarMain.visibility = View.GONE
                binding.includedBsl.bottomSheetContainer.visibility = View.VISIBLE
                /*СЕТИМ ДАННЫЕ*/
                binding.includedBsl.bottomSheetDescriptionHeader.text = pictureOfTheDayAppState.serverResponseData.title
                binding.includedBsl.bottomSheetDescription.text = pictureOfTheDayAppState.serverResponseData.explanation
                binding.includedBsl.bottomSheetDate.text = pictureOfTheDayAppState.serverResponseData.date
                binding.includedBsl.bottomSheetCopyright.text = pictureOfTheDayAppState.serverResponseData.copyright

                binding.imageView.load(pictureOfTheDayAppState.serverResponseData.url){
                    error(R.drawable.ic_load_error_vector)
                }
                binding.imageView.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(pictureOfTheDayAppState.serverResponseData.url)
                    })
                }
                binding.imageView.visibility = View.VISIBLE
                binding.cardViewMain.visibility = View.VISIBLE // тут когда перед тем как загружается картинка вначале пустой cardview и буквально меньше чем 1 сек прогружается картинка, как сделать синхронно?
                binding.fragmentMain.showSnackBarNoAction("Успешно")
                //  TODO HW Добавьте описание (приходит с сервера) под фотографией в виде BottomSheet. textview?
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}