package ua.amv0107.navigationactivity.model

import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Options(
    val boxCount: Int,
    val isTimerEnabled: Boolean
): Parcelable {
    companion object{
        @JvmStatic val DEFAULT = Options(boxCount = 3, isTimerEnabled = false)
    }
}