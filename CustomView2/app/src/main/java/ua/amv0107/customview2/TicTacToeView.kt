package ua.amv0107.customview2

import android.content.Context
import android.util.AttributeSet
import android.view.View

class TicTacToeView(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
): View(context, attributesSet, defStyleAttr, defStyleRes) {

    constructor(context: Context, attributesSet: AttributeSet?, defStyleAttr: Int) : this(context, attributesSet, defStyleAttr, R.style.DefaultTicTacToeStyle)
    constructor(context: Context, attributesSet: AttributeSet?) : this(context, attributesSet, R.attr.ticTacToeFieldStyle)
    constructor(context: Context) : this(context, null)

    init {
        if (attributesSet != null){
            initAttributes(attributesSet, defStyleAttr, defStyleRes)
        }
    }

    private fun initAttributes(attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {

    }
}