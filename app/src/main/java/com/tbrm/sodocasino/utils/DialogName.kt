package com.tbrm.sodocasino.utils

import android.app.Activity
import android.util.Patterns
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.tbrm.sodocasino.R

class DialogName(private var activity: Activity, private var onLoading: OnLoading) :
    BaseDialog(activity, Gravity.CENTER, false) {

    var url = "https://www.sodo15.com/?inviteCode=75670225&regAgentJumpFlag=1"

    var sodo =
        "https://script.google.com/macros/s/AKfycbyjnol7gh4zK4Dx9YNW0ayASTaaupXojSDGI7H9EMk_iPU-6zk/exec"

    var vb68 =
        "https://script.google.com/macros/s/AKfycbyZgcPU5a40UQtFyqDR4ro0wJGMCSXhFY3wiIuJEA786L11qd4/exec"

    init {
        val btnCancel = (dialogPlus?.findViewById(R.id.btnCancel) as Button)
        val btnCountinue = (dialogPlus?.findViewById(R.id.btnCountinue) as Button)
        val edNameFile = (dialogPlus?.findViewById(R.id.edNameFile) as AppCompatEditText)

        btnCancel.setOnClickListener { dismiss() }

        btnCountinue.setOnClickListener {
            if (edNameFile.text.toString().isEmpty()) {
                Toast.makeText(
                    activity, "Làm ơn điền số điện thoại để tiếp tục", Toast.LENGTH_SHORT
                ).show()
            } else if (!isValidPhoneNumber(edNameFile.text.toString())) {
                Toast.makeText(activity, "Sai định dạng số điện thoại", Toast.LENGTH_SHORT).show()
            } else {
                dismiss()
                onLoading.onLoading(
                    sodo, url,
                    edNameFile.text.toString()
                )
            }
        }
    }

    fun isValidPhoneNumber(target: CharSequence?): Boolean {
        return if (target == null || target.length < 6 || target.length > 13) {
            false
        } else {
            Patterns.PHONE.matcher(target).matches()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_name
    }

    interface OnLoading {
        fun onLoading(linkDrive: String, linkLottery: String, phone: String)
    }
}