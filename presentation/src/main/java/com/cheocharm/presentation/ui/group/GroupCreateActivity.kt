package com.cheocharm.presentation.ui.group

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.cheocharm.presentation.R
import com.cheocharm.presentation.base.BaseActivity
import com.cheocharm.presentation.databinding.ActivityGroupCreateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupCreateActivity :
    BaseActivity<ActivityGroupCreateBinding>(R.layout.activity_group_create) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboardWhenOutsideTouched(ev)
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboardWhenOutsideTouched(ev: MotionEvent?) {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val rect = Rect()
                v.getGlobalVisibleRect(rect)
                if (!rect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
    }
}
