package com.geekbrains.februarymaterial.view.main


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentMainBinding
import com.geekbrains.februarymaterial.extensionFun.showSnackBarAction
import com.geekbrains.februarymaterial.extensionFun.showSnackBarNoAction
import com.geekbrains.februarymaterial.extensionFun.showToastShort
import com.geekbrains.februarymaterial.view.MainActivity
import com.geekbrains.februarymaterial.view.bottomNavigation.BottomNavigationDrawerFragment
import com.geekbrains.februarymaterial.view.chips.ChipsFragment
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
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

        menuActionBar() /*Для menu and actionBar*/
        bottomSheetBehavior() /*Для bottomSheetBehavior*/
        fabClick() /*FloatActionButtonClick*/
        chipsClick() /*Переключение дней*/
        wikipediaSearch() /*Поиск wikipedia*/

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequest()
    }


    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {
                binding.progressBarMain.visibility = View.VISIBLE
                binding.fragmentMain.visibility = View.GONE
                binding.cardViewMain.visibility = View.INVISIBLE
                binding.includedBsl.bottomSheetContainer.visibility = View.INVISIBLE
                binding.fragmentMain.showSnackBarAction(getString(R.string.error_load), getString(R.string.reload)){
                    viewModel.sendServerRequest()
                }
            }
            is PictureOfTheDayAppState.Loading -> {
                binding.progressBarMain.visibility = View.VISIBLE
                binding.includedBsl.bottomSheetContainer.visibility = View.INVISIBLE
                binding.cardViewMain.visibility = View.INVISIBLE
                binding.fragmentMain.showSnackBarNoAction(getString(R.string.load))
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
                binding.textViewDate.text = pictureOfTheDayAppState.serverResponseData.date
                binding.imageView.visibility = View.VISIBLE
                binding.cardViewMain.visibility = View.VISIBLE
                binding.fragmentMain.showSnackBarNoAction(getString(R.string.success))
            }
        }
    }

    /*Создаем меню*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar,menu)
    }

    /*обработка кликов в меню*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_fav ->
                Toast.makeText(requireContext(),"app_bar_fav",Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings ->
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container_main_activity, ChipsFragment.newInstance()).addToBackStack("").commit()
            android.R.id.home ->  {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager,"") //выдвигаем бургер меню
            }
            R.id.app_bar_search ->  Toast.makeText(requireContext(),"работает поиск", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun menuActionBar(){
        /*Для меню*/
        setHasOptionsMenu(true) // запускаем menu
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar) //кликабельность говорим где
    }

    private fun bottomSheetBehavior(){
        /*Выдвижная панель*/
        bottomSheetBehavior = BottomSheetBehavior.from(binding.includedBsl.bottomSheetContainer) //извлекаем behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_SETTLING // Задаем как выдвигать
        bottomSheetBehavior.setPeekHeight(450,true) //Выдвигаем только на 200 вверх
        bottomSheetBehavior.setHideable(false) //указываем можно скрыть или нет

        /*Кликаем и выдвигаем панель*/
        binding.includedBsl.bottomSheetContainer.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

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

    /*FloatActionButtonClick*/
    private fun fabClick(){
        var isMainClick: Boolean = true

        binding.fab.setOnClickListener{
            if(isMainClick){
                binding.bottomAppBar.navigationIcon = null // скрываем меню
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END // меняем положение кнопки (CENTER / END)
                binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_fab)) // задаем другую иконку
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen) // меняем меню
            }else{
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_plus_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
            isMainClick = !isMainClick
        }
    }

    private fun wikipediaSearch() {
        /*Сетим в поиск введёный текст в поиск WIKIPEDIA*/
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                binding.mainLayout.showToastShort("Поехали искать ${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun chipsClick() {

        /*Кликабельность chips для group */
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.yesterdayChip-> {
                    viewModel.sendServerYesterday()
                }
                R.id.todayChip-> {
                    viewModel.sendServerRequest()
                }
            }
        }
        binding.astronautChip.setOnClickListener {
            viewModel.sendServerAstronautDay()
        }
    }

/*        binding.astronautChip.setOnClickListener {
            viewModel.sendServerAstronautDay()
        }
        binding.todayChip.setOnClickListener {
            viewModel.sendServerRequest()
        }
        binding.yesterdayChip.setOnClickListener {
            viewModel.sendServerYesterday()
        }*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}