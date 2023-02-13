package com.cheocharm.presentation.ui.write

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseFragment
import com.cheocharm.presentation.databinding.FragmentWriteBinding
import com.cheocharm.presentation.ui.MainActivity
import jp.wasabeef.richeditor.RichEditor

class WriteFragment : BaseFragment<FragmentWriteBinding>(R.layout.fragment_write) {
    private lateinit var editor: RichEditor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editor = binding.editorWrite.apply {
            setPlaceholder(getString(R.string.write_editor_placeholder))
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
            editor.insertImage(
                "https://raw.githubusercontent.com/wasabeef/art/master/twitter.png",
                "twitter"
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_base, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_base_confirm -> {
                // TODO: POST 요청 및 일기 상세 화면으로 이동
                println(editor.html)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
