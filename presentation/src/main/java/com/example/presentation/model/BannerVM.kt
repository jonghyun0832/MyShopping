package com.example.presentation.model

import com.example.domain.model.Banner
import com.example.presentation.delegate.BannerDelegate

class BannerVM(
    model: Banner,
    bannerDelegate: BannerDelegate
): PresentationVM<Banner>(model), BannerDelegate by bannerDelegate {

}