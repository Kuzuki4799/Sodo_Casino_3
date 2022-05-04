package com.rmtkds.sunwin.utils

import android.content.Context
import android.view.LayoutInflater
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder

abstract class BaseDialog(context: Context, gravity: Int, isCancel: Boolean) {

    abstract fun getLayoutId(): Int

    var dialogPlus: DialogPlus? = null

    init {
        val view = LayoutInflater.from(context).inflate(getLayoutId(), null)
        dialogPlus = DialogPlus.newDialog(context)
            .setGravity(gravity)
            .setCancelable(isCancel)
            .setContentBackgroundResource(android.R.color.transparent)
            .setContentHolder(ViewHolder(view))
            .create()
    }

    open fun <W> initView(id: Int): W {
        return dialogPlus?.findViewById(id) as W
    }

    open fun show() {
        if (dialogPlus != null) dialogPlus?.show()
    }

    open fun dismiss() {
        if (dialogPlus != null) dialogPlus?.dismiss()
    }

    interface OnDialogCallback {
        fun onDialogListener()
    }

    interface OnDialogDataCallback<T> {
        fun onDialogListener(value: T)
    }

    interface OnDialogDataPositionCallback{
        fun onDialogListener(position: Int)
    }

    interface OnDialogObjectCallback<T> {
        fun onDialogListener(value: T, dialogPlus: DialogPlus?)
    }
}