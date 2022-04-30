package com.tbrm.sodocasino

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.tbrm.sodocasino.utils.DialogName
import com.tbrm.sodocasino.utils.ShareUtils

class HomeActivity : AppCompatActivity() {

    private val imgRegister: AppCompatImageView by lazy { findViewById(R.id.imgRegister) }
    private val imgLogin: AppCompatImageView by lazy { findViewById(R.id.imgLogin) }
    private val cardLoad: CardView by lazy { findViewById(R.id.cardLoad) }

    var urlRegister = "https://www.sodo15.com/?inviteCode=06023152&regAgentJumpFlag=1"

    var urlLogin = "https://www.sodo15.com/mobile/#/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        imgRegister.setOnClickListener {
            val isGetPhone = ShareUtils.getString(this, "url", "")
            if (isGetPhone?.isEmpty() == true) {
                DialogName(this, object : DialogName.OnLoading {
                    override fun onLoading(
                        linkDrive: String, phone: String
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
                                        Intent(Intent.ACTION_VIEW, Uri.parse(urlRegister))
                                    )
                                    ShareUtils.putString(this@HomeActivity, "url", urlRegister)
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
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlRegister)))
            }
        }

        imgLogin.setOnClickListener {
            val isGetPhone = ShareUtils.getString(this, "url", "")
            if (isGetPhone?.isEmpty() == true) {
                DialogName(this, object : DialogName.OnLoading {
                    override fun onLoading(
                        linkDrive: String, phone: String
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
                                        Intent(Intent.ACTION_VIEW, Uri.parse(urlLogin))
                                    )
                                    ShareUtils.putString(this@HomeActivity, "url", urlLogin)
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
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlLogin)))
            }
        }
    }
}