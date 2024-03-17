package com.cheocharm.presentation.ui.write.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.TempDiary
import com.cheocharm.domain.model.WriteDiaryRequest
import com.cheocharm.domain.usecase.write.RequestWriteDiaryUseCase
import com.cheocharm.presentation.common.Event
import com.cheocharm.presentation.model.Sticker
import com.cheocharm.presentation.model.TextAlign
import com.cheocharm.presentation.model.TextColor
import com.cheocharm.presentation.model.TextEditTool
import com.cheocharm.presentation.model.ToolDetailPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val requestWriteDiaryUseCase: RequestWriteDiaryUseCase
) : ViewModel() {
    lateinit var temp: TempDiary
    lateinit var stickers: List<Sticker>

    private val _openTool = MutableLiveData<TextEditTool?>()
    val openTool: LiveData<TextEditTool?> = _openTool

    private val _textColor = MutableLiveData(TextColor.Default)
    val textColor: LiveData<TextColor> = _textColor

    private val _page = MutableLiveData<ToolDetailPage>()
    val page: LiveData<ToolDetailPage> = _page

    private val _textAlign = MutableLiveData(TextAlign.Left)
    val textAlign: LiveData<TextAlign> = _textAlign

    private val _bold = MutableLiveData<Boolean>()
    val bold: LiveData<Boolean> = _bold

    private val _underline = MutableLiveData<Boolean>()
    val underline: LiveData<Boolean> = _underline

    private val _diaryWrittenEvent = MutableLiveData<Event<Long>?>()
    val diaryWrittenEvent: LiveData<Event<Long>?> = _diaryWrittenEvent

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    fun onClickTool(tool: TextEditTool?) {
        _openTool.value = if (openTool.value == tool) null else tool
    }

    fun changeTextColor(textColor: TextColor) {
        if (this.textColor.value != textColor) {
            _textColor.value = textColor
        } else {
            _textColor.value = TextColor.Default
        }
    }

    fun updatePage(page: ToolDetailPage) {
        _page.value = page
    }

    fun changeTextAlign(textAlign: TextAlign) {
        _textAlign.value = textAlign
    }

    fun setBold() {
        val bold = bold.value ?: false
        _bold.value = bold.not()
    }

    fun setUnderline() {
        val underline = underline.value ?: false
        _underline.value = underline.not()
    }

    fun writeDiary(diaryId: Long, title: String, content: String) {
        viewModelScope.launch {
            requestWriteDiaryUseCase.invoke(WriteDiaryRequest(diaryId, title, content))
                .onSuccess {
                    _diaryWrittenEvent.value = Event(it)
                }
                .onFailure {
                    _toastText.value = Event(it.message ?: "")
                }
        }
    }
}
