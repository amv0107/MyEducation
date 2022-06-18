package ua.amv0107.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import ua.amv0107.customviews.databinding.PartButtonsBinding

enum class BottomButtonAction {
    POSITIVE, NEGATIVE
}

typealias OnBottomButtonActionListener = (BottomButtonAction) -> Unit

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: PartButtonsBinding
    private var listener: OnBottomButtonActionListener? = null

    var isProgressMode: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.positiveButton.visibility = INVISIBLE
                binding.negativeButton.visibility = INVISIBLE
                binding.progress.visibility = VISIBLE
            } else {
                binding.positiveButton.visibility = VISIBLE
                binding.negativeButton.visibility = VISIBLE
                binding.progress.visibility = GONE
            }
        }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        R.style.MyBottomButtonsStyle
    )

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.bottomButtonStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)
        binding = PartButtonsBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
        initListener()
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            defStyleRes
        )
        with(binding) {
            val positiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
            setPositiveButtonText(positiveButtonText)

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            setNegativeButtonText(negativeButtonText)

            val positiveButtonBgColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomPositiveButtonBackgroundColor,
                Color.BLACK
            )
            positiveButton.backgroundTintList = ColorStateList.valueOf(positiveButtonBgColor)

            val negativeButtonBgColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomNegativeButtonBackgroundColor,
                Color.WHITE
            )
            negativeButton.backgroundTintList = ColorStateList.valueOf(negativeButtonBgColor)

            this@BottomButtonsView.isProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)

        }
        typedArray.recycle()
    }

    private fun initListener() {
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.POSITIVE)
        }
        binding.negativeButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.NEGATIVE)
        }
    }

    fun setListener(listener: OnBottomButtonActionListener?) {
        this.listener = listener
    }

    fun setPositiveButtonText(text: String?) {
        binding.positiveButton.text = text ?: "Ok"
    }

    fun setNegativeButtonText(text: String?) {
        binding.negativeButton.text = text ?: "Cancel"
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()!!
        val savedState = SavedState(superState)
        savedState.positiveButtonText = binding.positiveButton.text.toString()
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        binding.positiveButton.text = savedState.positiveButtonText
    }

    class SavedState : BaseSavedState {
        var positiveButtonText: String? = null

        constructor(superState: Parcelable) : super(superState)
        constructor(parcel: Parcel): super(parcel) {
            positiveButtonText = parcel.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(positiveButtonText)
        }

        companion object{
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(p0: Parcel): SavedState {
                    return SavedState(p0)
                }

                override fun newArray(p0: Int): Array<SavedState?> {
                    return Array(p0) { null }
                }
            }
        }
    }
}