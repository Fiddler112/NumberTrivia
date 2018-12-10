package com.example.jonat.numbertrivia

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NumberAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var numbers = emptyList<Number>() // Cached copy of games

    inner class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number: TextView = itemView.findViewById(R.id.numberId)
        val numberText: TextView = itemView.findViewById(R.id.numberText)

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NumberViewHolder {
        val itemView = inflater.inflate(R.layout.number_item, p0, false)
        return NumberViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: NumberViewHolder, p1: Int) {
        val current = numbers[p1]
        p0.number.text = current.numberId.toString()
        p0.numberText.text = current.number
    }

    internal fun setNumbers(games: List<Number>) {
        this.numbers = games
        notifyDataSetChanged()
    }

    override fun getItemCount() = numbers.size

    }
