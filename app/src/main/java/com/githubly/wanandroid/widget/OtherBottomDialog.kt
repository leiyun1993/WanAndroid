package com.githubly.wanandroid.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubly.wanandroid.R
import kotlinx.android.synthetic.main.dialog_other_bottom.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.withArguments

/**
 * 类名：OtherBottomDialog
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2018-10-23 15:05
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
const val TYPE_SHARE = 0
const val TYPE_COLLECT = 1
const val TYPE_COPY = 2
const val TYPE_BROWSER = 3

class OtherBottomDialog : BottomSheetDialogFragment() {

    companion object {
        fun getDialog(collect: Boolean): OtherBottomDialog {
            val dialog = OtherBottomDialog()
            dialog.withArguments("collect" to collect)
            return dialog
        }
    }

    var onItemClick: (type: Int) -> Unit = {}
    private var collect: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.style_common_dialog)
        collect = arguments?.getBoolean("collect") ?: false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_other_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCancel.setOnClickListener {
            dismiss()
        }
        collectImageView.setColorFilter(act.resources.getColor(if (collect) R.color.red else R.color.black))
        collectTV.text = if (collect) "取消收藏" else "收藏"
        btnShare.setOnClickListener {
            onItemClick(TYPE_SHARE)
        }
        btnCollect.setOnClickListener {
            onItemClick(TYPE_COLLECT)
        }
        btnCopy.setOnClickListener {
            onItemClick(TYPE_COPY)
        }
        btnBrowser.setOnClickListener {
            onItemClick(TYPE_BROWSER)
        }
    }
}