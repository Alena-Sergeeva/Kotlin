package com.example.my_application_homework

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val numbers = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val fab = findViewById<View>(R.id.fab)

        setupRecyclerView()

        fab.setOnClickListener {
            val newNumber = numbers.size + 1
            numbers.add(newNumber)
            recyclerView.adapter?.notifyItemInserted(numbers.size - 1)
        }
    }

    private fun setupRecyclerView() {
        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 3
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.adapter = SimpleAdapter(numbers)
        recyclerView.setPadding(0, 0, 0, 250) // Отступ для FAB
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val spanCount = if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 3
        (recyclerView.layoutManager as? GridLayoutManager)?.spanCount = spanCount
    }
}

class SimpleAdapter(private val numbers: List<Int>) : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tvNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = numbers[position]
        holder.textView.text = number.toString()
        holder.textView.setBackgroundColor(if (number % 2 == 0) Color.RED else Color.BLUE)
    }

    override fun getItemCount(): Int = numbers.size
}