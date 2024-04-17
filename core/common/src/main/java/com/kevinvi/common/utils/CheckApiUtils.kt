package com.kevinvi.common.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = VERSION_CODES.S)
fun supportDynamicTheme() = isAtLeastS()

@ChecksSdkIntAtLeast(api = VERSION_CODES.TIRAMISU)
fun isAtLeastT() = SDK_INT >= VERSION_CODES.TIRAMISU

@ChecksSdkIntAtLeast(api = VERSION_CODES.S)
fun isAtLeastS() = SDK_INT >= VERSION_CODES.S

@ChecksSdkIntAtLeast(api = VERSION_CODES.R)
fun isAtLeastR() = SDK_INT >= VERSION_CODES.R

@ChecksSdkIntAtLeast(api = VERSION_CODES.Q)
fun isAtLeastQ() = SDK_INT >= VERSION_CODES.Q

@ChecksSdkIntAtLeast(api = VERSION_CODES.P)
fun isAtLeastP() = SDK_INT >= VERSION_CODES.P
