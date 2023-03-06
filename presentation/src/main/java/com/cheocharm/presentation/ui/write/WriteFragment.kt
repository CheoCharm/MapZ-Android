package com.cheocharm.presentation.ui.write

import android.content.Intent
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentWriteBinding
import com.cheocharm.presentation.model.Page
import com.cheocharm.presentation.model.Sticker
import com.cheocharm.presentation.model.TextAlign
import com.cheocharm.presentation.ui.DiaryActivity
import com.cheocharm.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.richeditor.RichEditor

@AndroidEntryPoint
class WriteFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write), MenuProvider {
    private val locationViewModel: LocationViewModel by navGraphViewModels(R.id.write)
    private val writeViewModel: WriteViewModel by viewModels()
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

        val rvWriteImage = binding.rvWriteImage
        val rvWriteSticker = binding.rvWriteSticker

        setupToolbar()
        setupEditor()
        setupTextColor()
        setupFont()
        setupTextAlign()

        locationViewModel.result.observe(viewLifecycleOwner) {
            it?.let { result ->
                if (result.isSuccessful) {
                    result.data?.let { data ->
                        diaryId = data.diaryId
                        rvWriteImage.adapter =
                            WriteImageAdapter(data.imageUrls, ::onImageClickListener)
                    }
                }
            }
        }

        locationViewModel.stickers.observe(viewLifecycleOwner) {
            rvWriteSticker.adapter = WriteStickerAdapter(it, ::onStickerClickListener)
        }

        writeViewModel.bold.observe(viewLifecycleOwner) {
            it?.let {
                editor.focusEditor()
                editor.setBold()
            }
        }

        writeViewModel.underline.observe(viewLifecycleOwner) {
            it?.let {
                editor.focusEditor()
                editor.setUnderline()
            }
        }

        writeViewModel.result.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Toast.makeText(context, "일기 작성 성공", Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, DiaryActivity::class.java)
                startActivity(intent)
            } else if (it.isSuccessful.not()) {
                Toast.makeText(context, "일기 작성 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupToolbar() {
        val toolbarWrite = binding.toolbarWrite

        (activity as MainActivity).setSupportActionBar(toolbarWrite)

        with(toolbarWrite) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                Log.d(logTag, "navigationOnClick 호출")
                val action = WriteFragmentDirections.actionWriteFragmentToLocationFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setupEditor() {
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
    }

    private fun setupTextColor() {
        writeViewModel.textColor.observe(viewLifecycleOwner) {
            editor.setTextColor(ResourcesCompat.getColor(resources, it.id, null))
        }
    }

    private fun setupFont() {
        writeFontAdapter = WriteFontAdapter(this)
        viewPager = binding.writeFontDetail.vpWriteToolDetail.apply {
            adapter = writeFontAdapter
            registerOnPageChangeCallback(onPageChangeCallback)
        }

        writeFontViewModel.selectedFontSize.observe(viewLifecycleOwner) {
            Log.d(logTag, "글꼴 크기: $it")
            editor.setFontSize(it.id)
        }
    }

    private fun setupTextAlign() {
        writeViewModel.textAlign.observe(viewLifecycleOwner) {
            when (it) {
                null -> editor.setAlignLeft()
                TextAlign.Left -> editor.setAlignLeft()
                TextAlign.Center -> editor.setAlignCenter()
                TextAlign.Right -> editor.setAlignRight()
            }
        }
    }

    private fun onStickerClickListener(sticker: Sticker) {
        editor.focusEditor()
        editor.insertImage(
            sticker.url,
            sticker.name,
            150
        )
    }

    private fun onImageClickListener(imageUrl: String) {
        editor.focusEditor()
        editor.insertImage(
            imageUrl,
            imageUrl,
            150
        )
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_base, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_base_confirm -> {
                Log.d(logTag, editor.html ?: "")

                diaryId?.let {
                    writeViewModel.writeDiary(
                        it,
                        binding.etWriteTitle.text.toString(),
                        editor.html ?: ""
                    )
                }

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
