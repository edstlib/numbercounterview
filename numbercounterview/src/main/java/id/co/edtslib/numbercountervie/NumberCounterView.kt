package id.co.edtslib.numbercountervie

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import java.lang.NumberFormatException

class NumberCounterView: FrameLayout {
    private var editText: EditText? = null
    private var min = 0
    private var max = Int.MAX_VALUE
    private var step = 1
    var delegate: NumberCounterDelegate? = null

    constructor(context: Context) : super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val view = inflate(context, R.layout.view_number_counter, this)

        editText = view.findViewById(R.id.editText)
        editText?.addTextChangedListener {
            delegate?.onChangeValue(getValue())
        }

        view.findViewById<View>(R.id.tvAdd).setOnClickListener {
            editText?.setText(String.format("%d", add()))

        }
        view.findViewById<View>(R.id.tvMinus).setOnClickListener {
            editText?.setText(String.format("%d", minus()))
        }

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.NumberCounterView,
                0, 0
            )

            min = a.getInt(R.styleable.NumberCounterView_numberCounterMin,
                0)
            max = a.getInt(R.styleable.NumberCounterView_numberCounterMax,
                Int.MAX_VALUE)
            step = a.getInt(R.styleable.NumberCounterView_numberCounterStep,
                1)

            val value = a.getInt(R.styleable.NumberCounterView_numberCounterValue,
                1)

            editText?.setText(String.format("%d", value))

            a.recycle()
        }

    }
    
    fun setValue(value: Int) {
        editText?.setText(String.format("%d", value))
    }

    private fun getValue(): Int {
        return try {
            val s = editText?.text?.toString()
            return s?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun add(): Int {
        return try {
            val s = editText?.text?.toString()
            if (s == null) {
                0
            } else {
                val d = s.toInt()
                return if (d+step > max) max else d+step
            }
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun minus(): Int {
        return try {
            val s = editText?.text?.toString()
            if (s == null) {
                0
            } else {
                val d = s.toInt()
                return if (d-step < min) min else d-step
            }
        } catch (e: NumberFormatException) {
            0
        }
    }
}