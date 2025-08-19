package com.example.myshopping.di

import android.app.Application
import com.example.myshopping.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyShoppingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}