package com.example.presentation.model

import com.example.domain.model.BannerList
import com.example.presentation.delegate.BannerDelegate

class BannerListVM(
    model: BannerList,
    private val bannerDelegate: BannerDelegate
): PresentationVM<BannerList>(model) {
    fun openBannerList(bannerId: String) {
        bannerDelegate.openBanner(bannerId)
    }
}