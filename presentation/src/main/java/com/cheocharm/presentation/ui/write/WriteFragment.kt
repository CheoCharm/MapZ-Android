package com.cheocharm.presentation.ui.write

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentWriteBinding
import com.cheocharm.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.richeditor.RichEditor

@AndroidEntryPoint
class WriteFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write), MenuProvider {
    private val locationViewModel: LocationViewModel by navGraphViewModels(R.id.write)
    private val writeViewModel: WriteViewModel by navGraphViewModels(R.id.write) { defaultViewModelProviderFactory }

    private lateinit var editor: RichEditor
    private var diaryId: Long? = null

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

        locationViewModel.result.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Log.d(javaClass.name, "이미지 업로드 성공")
                diaryId = it.data
            } else {
                Log.e(javaClass.name, "이미지 업로드 실패: ${it.message}")
            }
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
                groupWriteTools.visibility = View.INVISIBLE
                groupWriteToolColor.visibility = View.VISIBLE
            }
        }

        binding.btnWriteColorClose.setOnClickListener {
            binding.run {
                groupWriteToolColor.visibility = View.INVISIBLE
                groupWriteTools.visibility = View.VISIBLE
            }
        }

        binding.btnWriteColorRed.setOnClickListener {
            editor.setTextColor(Color.RED)
        }

        binding.btnWriteFont.setOnClickListener {
            editor.setFontSize(22)
        }

        binding.btnWriteAlign.setOnClickListener {
            editor.setAlignCenter()
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_base, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_base_confirm -> {
                // TODO: 일기 작성 완료 화면으로 이동

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

    companion object {
        private const val MAX_CONTENT_LENGTH = 2000
    }
}
