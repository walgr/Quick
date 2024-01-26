package com.wpf.app.quick.demo.model

import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.HolderMessageMyBinding
import com.wpf.app.quickrecyclerview.data.QuickAbilityData
import com.wpf.app.quickrecyclerview.data.binding
import com.wpf.app.quickrecyclerview.data.suspension
import com.wpf.app.quickrecyclerview.data.with

class AbilityDataTest(
    val data1: String = ""
) : QuickAbilityData(
    layoutId = R.layout.holder_message_my,
    abilityList = suspension {
        return@suspension false
    }.with(
        binding<HolderMessageMyBinding, AbilityDataTest>() {

        }
    )
)