package com.geekbrains.februarymaterial.view.main


import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentMainBinding
import com.geekbrains.februarymaterial.extensionFun.showSnackBarAction
import com.geekbrains.februarymaterial.extensionFun.showSnackBarNoAction
import com.geekbrains.februarymaterial.extensionFun.showToastShort
import com.geekbrains.februarymaterial.view.MainActivity
import com.geekbrains.februarymaterial.view.animations.AnimationFragment
import com.geekbrains.februarymaterial.view.bottomNavigation.BottomNavigationDrawerFragment
import com.geekbrains.februarymaterial.view.chips.ChipsFragment
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.*
import java.util.regex.Pattern


class PictureOfTheDayFragment : Fragment() {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java) } // ???????????? ???????? ?? ???? ????????????: private val viewModel: PictureOfTheDayViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    /*?????????????????? ????????????*/
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<ConstraintLayout> //<???????????? ?????????? ??????????????????>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuActionBar() /*?????? menu and actionBar*/
        bottomSheetBehavior() /*?????? bottomSheetBehavior*/
        //fabClick() /*FloatActionButtonClick*/
        chipsClick() /*???????????????????????? ????????*/
        wikipediaSearch() /*?????????? wikipedia*/

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
                /*?????????? ????????????*/
                binding.includedBsl.bottomSheetDescriptionHeader.text =
                    pictureOfTheDayAppState.serverResponseData.title
//                binding.includedBsl.bottomSheetDescription.text = pictureOfTheDayAppState.serverResponseData.explanation
//                binding.includedBsl.bottomSheetDate.text = pictureOfTheDayAppState.serverResponseData.date
                binding.includedBsl.bottomSheetCopyright.text =
                    pictureOfTheDayAppState.serverResponseData.copyright

                /*???????? 7???? ???????????? ?? ??????????????*/
                val textDate = pictureOfTheDayAppState.serverResponseData.date
                val textExplanation = pictureOfTheDayAppState.serverResponseData.explanation
                var spannableStringBuilderDate = SpannableStringBuilder(textDate)
                val spannableStringBuilderExplanation = SpannableStringBuilder(textExplanation)
                val spannableString = SpannableString(textExplanation)
                val spannedString = SpannedString(spannableStringBuilderExplanation)

                binding.includedBsl.bottomSheetDate.setText(
                    spannableStringBuilderDate,
                    TextView.BufferType.EDITABLE
                )
                spannableStringBuilderDate =
                    binding.includedBsl.bottomSheetDate.text as SpannableStringBuilder

                /*???????????? ?????????? ???? ??????????????*/

/*                Handler(Looper.getMainLooper()).postDelayed({
                    spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red_900)), 0, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ??????
                    spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red_900)), 4, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ??????????
                    spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_blue_nasa_icon)), 8, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ????????
                    binding.includedBsl.bottomSheetDate.text = spannableStringBuilderDate
                }, 5000L)*/

                CoroutineScope(Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
                    println("@@@ + $throwable")
                }).launch {
                    var timer = object : CountDownTimer(10000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.color_blue_nasa_icon_transparent)), 0, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ??????
                            spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red_200)), 4, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ??????????
                            spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)), 8, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ????????
                            binding.includedBsl.bottomSheetDate.text = spannableStringBuilderDate
                        }
                        override fun onFinish() {
                            spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red_900)), 0, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ??????
                            spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red_900)), 4, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ??????????
                            spannableStringBuilderDate.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_blue_nasa_icon)), 8, textDate.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE) //?????????????????????? ????????
                            binding.includedBsl.bottomSheetDate.text = spannableStringBuilderDate
                        }
                    }
                    timer.start()
                }


//                textExplanation?.split("\n")?.forEach { it.length } //?????????????? ???? ???????????? ??????????????
//                spannableStringBuilderExplanation.insert(1,"\n") // ?????????????????? ?????????????? ???? 4 ????????????
                spannableStringBuilderExplanation.setSpan(
                    BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.red_200), 10), 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                ) // ?????????????????? ?????????? ?????? ??????????????

                /*???????????????? ?????????? "??" ???? ????????????????*/
//                spannableStringBuilderExplanation.indices.forEach {
//                    if (spannableStringBuilderExplanation[it] == ('o')) { // ???????????????? ?????????? "??" ???? ????????????????
//                        spannableStringBuilderExplanation.setSpan(ImageSpan(requireContext(), R.drawable.swipe_indicator_active, DynamicDrawableSpan.ALIGN_BASELINE), it, it + 1, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
//                    }
//                }

                /*?????????? ?????????????????? ???????????? ?? ??????????*/
                val request = FontRequest(
                    "com.google.android.gms.fonts", "com.google.android.gms",
                    "Mouse Memoirs", R.array.com_google_android_gms_fonts_certs
                )
                val callback = object : FontsContractCompat.FontRequestCallback() {
                    override fun onTypefaceRetrieved(typeface: Typeface?) {
                        binding.includedBsl.bottomSheetDescription.typeface = typeface
                        super.onTypefaceRetrieved(typeface)
                    }
                }
                FontsContractCompat.requestFont(requireContext(), request, callback, Handler(Looper.myLooper()!!))
                /*?????????? ?????????????????? ???????????? ?? ??????????*/
                binding.includedBsl.bottomSheetDescription.text = spannableStringBuilderExplanation

                /*VideoPlayer youtube*/
                if (pictureOfTheDayAppState.serverResponseData.mediaType == "video") {
                    //showAVideoUrl(pictureOfTheDayState.serverResponseData.url) //?????????????? ????????????
                    binding.youtubePlayer.visibility = View.VISIBLE
                    binding.imageView.visibility = View.GONE
                    showNasaVideo(extractYTId(pictureOfTheDayAppState.serverResponseData.url))
                }else{
                    binding.youtubePlayer.visibility = View.GONE
                    binding.imageView.visibility = View.VISIBLE
                    binding.imageView.load(pictureOfTheDayAppState.serverResponseData.url){
                        error(R.drawable.ic_load_error_vector)
                    }
                }
                binding.imageView.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(pictureOfTheDayAppState.serverResponseData.url)
                    })
                }
                binding.includedBsl.image.load(pictureOfTheDayAppState.serverResponseData.url)
                if(pictureOfTheDayAppState.serverResponseData.mediaType == "video"){
                    binding.textViewDate.visibility = View.GONE
                } else{
                    binding.textViewDate.visibility = View.VISIBLE
                    binding.textViewDate.text = pictureOfTheDayAppState.serverResponseData.date
                }
                binding.textViewDate.text = pictureOfTheDayAppState.serverResponseData.date
                binding.cardViewMain.visibility = View.VISIBLE
                binding.fragmentMain.showSnackBarNoAction(getString(R.string.success))
            }
        }
    }

    /*?????????????? ????????*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar,menu)
    }

    /*?????????????????? ???????????? ?? ????????*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_fav ->
                requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                    //???????????????? ????????????????
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                ).replace(R.id.container_main_activity, AnimationFragment.newInstance()).addToBackStack("").commit()
            R.id.app_bar_settings ->
                requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                    //???????????????? ????????????????
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                ).replace(R.id.container_main_activity, ChipsFragment.newInstance()).addToBackStack("").commit()
            android.R.id.home ->  {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager,"") //?????????????????? ???????????? ????????
            }
            R.id.app_bar_search ->  Toast.makeText(requireContext(),"???????????????? ??????????", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun menuActionBar(){
        /*?????? ????????*/
        setHasOptionsMenu(true) // ?????????????????? menu
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar) //???????????????????????????? ?????????????? ??????
    }

    private fun bottomSheetBehavior(){
        /*?????????????????? ????????????*/
        bottomSheetBehavior = BottomSheetBehavior.from(binding.includedBsl.bottomSheetContainer) //?????????????????? behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED // ???????????? ?????? ??????????????????
        bottomSheetBehavior.setPeekHeight(0,true) //?????????????????? ???????????? ???? 200 ??????????
        bottomSheetBehavior.setHideable(false) //?????????????????? ?????????? ???????????? ?????? ??????

        binding.includedBsl.bottomSheetDescription.visibility =  View.GONE
        binding.includedBsl.bottomSheetDate.visibility = View.GONE
        //binding.includedBsl.bottomSheetCopyright.visibility = View.GONE
        binding.includedBsl.image.visibility = View.GONE

        /*?????????????? ?? ?????????????????? ????????????*/
        var fabSheet = false
        binding.fabSheet.setOnClickListener {
            fabSheet = !fabSheet
            if (fabSheet) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                rotateFabSheet()
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                rotateFabSheet()
            }
        }
/*        binding.includedBsl.bottomSheetContainer.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_DRAGGING
        }*/

        /*?????????? ?????? ?????????????????????? ??????????????????*/
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                   /* ???????? ?????????????? ?????? ?????????? ?????????????????? ?????? ??????????*/
//                    BottomSheetBehavior.STATE_DRAGGING -> animationBottomSheetBehavior()
//                    BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
                      BottomSheetBehavior.STATE_EXPANDED -> animationBottomSheetBehavior()
//                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("onSlide", "$slideOffset slideOffset")
                binding.fragmentMain.showToastShort("$slideOffset")
            }
        })
    }

    private fun rotateFabSheet(){
        var flag = false
        var duration = 3000L
        flag = !flag
            if (flag) {
                ObjectAnimator.ofFloat(binding.fabSheet, View.ROTATION, 0f, 405f)
                    .setDuration(duration).start()
            } else{
                ObjectAnimator.ofFloat( binding.fabSheet,View.ROTATION,405f,200f)
                    .setDuration(duration).start()
            }
        }


    private fun animationBottomSheetBehavior() {
        var flag = false
        val transition = TransitionSet()
        val slide = Slide()
        slide.duration = 2000
        val changeBounds = ChangeBounds()
        changeBounds.duration = 2000
        transition.ordering = TransitionSet.ORDERING_SEQUENTIAL
        transition.addTransition(slide)
        transition.addTransition(changeBounds)
        TransitionManager.beginDelayedTransition(binding.includedBsl.bottomSheetContainer, transition)
        binding.includedBsl.bottomSheetDescription.visibility =  View.VISIBLE
        binding.includedBsl.bottomSheetDate.visibility = View.VISIBLE
        //binding.includedBsl.bottomSheetCopyright.visibility = View.VISIBLE
        binding.includedBsl.image.visibility = View.VISIBLE

        binding.imageView.setOnClickListener {
            val changeBounds = ChangeImageTransform()
            changeBounds.duration = 3000
            TransitionManager.beginDelayedTransition(binding.includedBsl.bottomSheetContainer, changeBounds)
            flag = !flag
            binding.imageView.scaleType = if (flag) ImageView.ScaleType.CENTER else ImageView.ScaleType.CENTER_INSIDE
        }
    }


    /*FloatActionButtonClick*/
/*    private fun fabClick(){
        var isMainClick: Boolean = true

        binding.fab.setOnClickListener{
            if(isMainClick){
                binding.bottomAppBar.navigationIcon = null // ???????????????? ????????
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END // ???????????? ?????????????????? ???????????? (CENTER / END)
                binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_fab)) // ???????????? ???????????? ????????????
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen) // ???????????? ????????
            }else{
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_plus_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
            isMainClick = !isMainClick
        }
    }*/

    /*?????????????????? ?? ?????????? ???????????? ?????? ??????????*/
    fun extractYTId(ytUrl: String): String {
        var viewId: String? = null
        val pattern = Pattern.compile(
            "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
            Pattern.CASE_INSENSITIVE
        )
        val matcher = pattern.matcher(ytUrl)
        if (matcher.matches()) {
            viewId = matcher.group(1)
        }
        return viewId!!
    }

    /*???????? ??????????*/
    private fun showNasaVideo(videoId: String) {
        with(binding) {
            lifecycle.addObserver(binding.youtubePlayer)
            youtubePlayer.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)
                    //youTubePlayer.cueVideo(videoId, 0f)
                    // youTubePlayer.pause()
                }
            })
        }
    }

    /*?????????????????? url ???? ?????????? ???????? ???? ?????????? ??????????????????????????*/
    private fun showAVideoUrl(videoUrl: String) {
        with(binding) {
            imageView.visibility = View.GONE
            videoOfTheDay.visibility = View.VISIBLE
            videoOfTheDay.text = "?????????????? ?? ?????? ?????? ???????????????? ??????, ???? ????????  ?????????? ??????! " + "${videoUrl.toString()} \n ???????????? >??????????< ?????????? ?????????????? ?? ?????????? ????????"
            videoOfTheDay.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(videoUrl)
                }
                startActivity(i)
            }
        }
    }

    private fun wikipediaSearch() {
        /*?????????? ?? ?????????? ???????????????? ?????????? ?? ?????????? WIKIPEDIA*/
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                binding.mainLayout.showToastShort("?????????????? ???????????? ${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun chipsClick() {

        /*???????????????????????????? chips ?????? group */
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