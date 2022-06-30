package com.example.android_asyncscrollview_example

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android_asyncscrollview_example.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Default).launch {
            val countList = List(100) { it }
            countList.forEach {
                Log.d("myLog", "start inflating - $it")
                val inflatedView = layoutInflater.inflate(R.layout.item_count, binding.scrollArea, false).apply {
                    findViewById<TextView>(R.id.text_view).text = "$it"

                    var i = 0
                    while (i < if (it % 2 == 0) 1000000000 else 100) {
                        i++
                    }
                }
                Log.d("myLog", "inflated view - $it")

                withContext(Dispatchers.Main) {
                    binding.scrollArea.addView(inflatedView)
                    Log.d("myLog", "view added - $it")
                }
            }
        }
    }

}
