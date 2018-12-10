package com.example.jonat.numbertrivia


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var numberViewModel: NumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recylcerView)
        val adapter = NumberAdapter(this)
        recylcerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        numberViewModel = ViewModelProviders.of(this).get(NumberViewModel::class.java)
        numberViewModel.allNumbers.observe(this, Observer { number ->
            number?.let { adapter.setNumbers(it) }
        })

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            //Called when a user swipes left or right on a ViewHolder
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                //Get the index corresponding to the selected position
                val position = viewHolder.adapterPosition
                numberViewModel.delete(numberViewModel.allNumbers.value!![position!!])
                adapter.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        var fab = findViewById<FloatingActionButton>(R.id.randomNumber)
        fab.setOnClickListener {
            var url = "http://numbersapi.com/random/trivia?json"

            val request = Request.Builder().url(url).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                    println(body)

                    val gson = GsonBuilder().create()

                    val homefeed = gson.fromJson(body, NumberFeedText::class.java)

                    val number = Number(homefeed.text, homefeed.number)

                    numberViewModel.insert(number)
                }

                override fun onFailure(call: Call, e: IOException) {
                    println("Failed")
                }
            }
            )}
    }
}

class NumberFeedText(val text: String, val number: Long)