package id.co.edtslib.numbercountervie

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class NumberEditText: AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        if (filters.isEmpty()) {
            filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                source.toString().filterNot { !it.isDigit() }
            })
        }
        else {
            filters[filters.size-1] = InputFilter { source, _, _, _, _, _ ->
                source.toString().filterNot { !it.isDigit() }
            }
        }

    }
}