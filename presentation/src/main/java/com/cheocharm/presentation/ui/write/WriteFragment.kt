package com.cheocharm.presentation.ui.write

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentWriteBinding
import com.cheocharm.presentation.model.FontSize
import com.cheocharm.presentation.model.Page
import com.cheocharm.presentation.model.TextAlign
import com.cheocharm.presentation.model.TextColor
import com.cheocharm.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.richeditor.RichEditor

@AndroidEntryPoint
class WriteFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write), MenuProvider {
    private val locationViewModel: LocationViewModel by navGraphViewModels(R.id.write)
    private val writeViewModel: WriteViewModel by navGraphViewModels(R.id.write) { defaultViewModelProviderFactory }
    val writeFontViewModel: WriteFontViewModel by viewModels()

    private lateinit var editor: RichEditor
    private var diaryId: Long? = null

    private lateinit var writeFontAdapter: WriteFontAdapter
    private lateinit var viewPager: ViewPager2

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            val fragment = writeFontAdapter.fragments[position]
            val view = fragment.view
            val titles = arrayOf(
                resources.getString(R.string.write_select_font_family_title),
                resources.getString(R.string.write_select_font_size_title)
            )

            view?.post {
                val wMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

                view.measure(wMeasureSpec, hMeasureSpec)

                if (viewPager.layoutParams.height != view.measuredHeight) {
                    viewPager.layoutParams =
                        (viewPager.layoutParams as ConstraintLayout.LayoutParams).also { lp ->
                            lp.height = view.measuredHeight
                        }
                }
            }

            writeViewModel.updatePage(Page(position + 1, titles[position]))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = writeViewModel

        locationViewModel.result.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Log.d(logTag, "이미지 업로드 성공")
                diaryId = it.data
            } else {
                Log.e(logTag, "이미지 업로드 실패: ${it.message}")
            }
        }

        writeViewModel.editorTextColor.observe(viewLifecycleOwner) {
            updateTextColor(it)
        }

        editor = binding.editorWrite.apply {
            setPlaceholder(getString(R.string.write_editor_placeholder))
            setOnTextChangeListener {
                if (it.length > MAX_CONTENT_LENGTH) {
                    Toast.makeText(
                        context,
                        "${MAX_CONTENT_LENGTH}자를 초과할 수 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    html = html.dropLast(1)
                }
            }
        }

        with(binding.toolbarWrite) {
            (activity as MainActivity).setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                val action = WriteFragmentDirections.actionWriteFragmentToLocationFragment()
                findNavController().navigate(action)
            }
        }

        binding.btnWriteColor.setOnClickListener {
            binding.run {
                groupWriteTools.isVisible = false
                groupWriteToolColor.isVisible = true
            }
        }

        binding.btnWriteColorClose.setOnClickListener {
            binding.run {
                groupWriteToolColor.isVisible = false
                groupWriteTools.isVisible = true
            }
        }

        val fontDetailView = binding.writeFontDetail.root

        binding.btnWriteFont.setOnClickListener {
            fontDetailView.isVisible = fontDetailView.isVisible.not()
        }

        writeFontAdapter = WriteFontAdapter(this)
        viewPager = binding.writeFontDetail.vpWriteToolDetail
        viewPager.adapter = writeFontAdapter
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)

        writeFontViewModel.selectedFontSize.observe(viewLifecycleOwner) {
            Log.d(logTag, "글꼴 크기: $it")
            editor.setFontSize(it.id)
        }

        binding.writeFontDetail.btnToolDetailClose.setOnClickListener {
            fontDetailView.isVisible = false
        }

        val alignDetailView = binding.writeAlignDetail.root

        binding.btnWriteAlign.setOnClickListener {
            alignDetailView.isVisible = alignDetailView.isVisible.not()
            editor.setAlignCenter()
        }

        writeViewModel.textAlign.observe(viewLifecycleOwner) {
            setTextAlign(it)
        }

        binding.writeAlignDetail.btnToolDetailClose.setOnClickListener {
            alignDetailView.isVisible = false
        }

        binding.btnWriteBold.setOnClickListener {
            editor.setBold()
        }

        binding.btnWriteUnderline.setOnClickListener {
            editor.setUnderline()
        }

        binding.btnWriteEmoticon.setOnClickListener {
            editor.insertImage(
                "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Emoji/IMG_6148.JPG",
                "emoticon",
                150
            )
        }

        binding.btnWritePicture.setOnClickListener {
            // TODO: 사진 목록
        }

        writeViewModel.result.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Toast.makeText(context, "일기 작성 성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "일기 작성 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setTextAlign(textAlign: TextAlign) {
        when (textAlign) {
            TextAlign.Left -> editor.setAlignLeft()
            TextAlign.Center -> editor.setAlignCenter()
            TextAlign.Right -> editor.setAlignRight()
        }
    }

    private fun updateTextColor(textColor: TextColor) {
        editor.setTextColor(ResourcesCompat.getColor(resources, textColor.id, null))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_base, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_base_confirm -> {
                // TODO: 일기 작성 완료 화면으로 이동

                // TODO: 테스트 완료 후 복구
                Log.d(logTag, editor.html.toString())
//                diaryId?.let {
//                    writeViewModel.writeDiary(
//                        it,
//                        binding.etWriteTitle.text.toString(),
//                        editor.html ?: ""
//                    )
//                }

                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
        super.onDestroyView()
    }

    companion object {
        private const val MAX_CONTENT_LENGTH = 2000
        private val logTag = this::class.java.name
    }
}
