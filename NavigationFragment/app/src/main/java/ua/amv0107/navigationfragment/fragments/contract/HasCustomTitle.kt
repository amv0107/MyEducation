package ua.amv0107.navigationfragment.fragments.contract

import androidx.annotation.StringRes

interface HasCustomTitle {
    @StringRes
    fun getTitleRes(): Int
}