package id.co.edtslib.numbercountervie

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import java.lang.NumberFormatException

class NumberCounterView: FrameLayout {
    private var editText: EditText? = null
    private var min = 0
    private var max = Int.MAX_VALUE
    private var step = 1
    private var lastValue = -1
    var delegate: NumberCounterDelegate? = null
    private var textWatcher: TextWatcher? = null

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
        setEditTextListener()

        editText?.setOnFocusChangeListener { _, _ ->
            if (editText!!.text.toString().isEmpty()) {
                editText?.setText(String.format("%d", min))
            }
        }

        view.findViewById<View>(R.id.tvAdd).setOnClickListener {
            add()
        }
        view.findViewById<View>(R.id.tvMinus).setOnClickListener {
            minus()
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

    private fun removeEditTextListener() {
        if (textWatcher != null) {
            editText?.removeTextChangedListener(textWatcher)
        }
    }

    private fun setEditTextListener() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.length > 1 && s.startsWith("0")) {
                        editText?.setText(s.toString().trimStart('0'))
                    } else {
                        var value = getValue()
                        if (value > max && value > lastValue) {
                            value = lastValue

                            removeEditTextListener()
                            editText?.setText(String.format("%d", value))
                            setEditTextListener()
                        } else {
                            lastValue = value
                        }

                        delegate?.onChangeValue(value)
                    }
                }
            }
        }

        editText?.addTextChangedListener(textWatcher)
    }
    
    fun setValue(value: Int) {
        editText?.setText(String.format("%d", value))
    }

    fun setMaxValue(value: Int) {
        max = value
    }

    fun setMaxValue(value: Int, force: Boolean) {
        if (force) {
            editText?.removeTextChangedListener(textWatcher)
        }
        max = value

        setEditTextListener()
    }

    fun setMinValue(value: Int) {
        min = value
    }

    private fun getValue(): Int {
        return try {
            val s = editText?.text?.toString()
            return s?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun add() {
        try {
            val s = editText?.text?.toString()
            if (s != null) {
                val d = s.toInt()
                val d1 = d+step
                if (d1 <= max && d != d1) {
                    removeEditTextListener()
                    editText?.setText(String.format("%d", d1))
                    delegate?.onChangeValue(d1)
                    setEditTextListener()
                }
            }
        } catch (ignored: NumberFormatException) {

        }
    }

    private fun minus() {
        try {
            val s = editText?.text?.toString()
            if (s != null) {
                val d = s.toInt()
                val d1  = d - step
                if (d1 >= min && d != d1) {
                    removeEditTextListener()
                    editText?.setText(String.format("%d", d1))
                    delegate?.onChangeValue(d1)
                    setEditTextListener()
                }
            }
        } catch (ignored: NumberFormatException) {

        }
    }
}