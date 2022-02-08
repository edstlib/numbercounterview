package id.co.edtslib.numbercounterview.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.edtslib.numbercountervie.NumberCounterView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberCounterView = findViewById<NumberCounterView>(R.id.numberCounterView)
        numberCounterView.setValue(1)
    }
}