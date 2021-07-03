package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkoutapp.data.HistoryViewModel
import com.example.a7minuteworkoutapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var mHistoryViewModel: HistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistory)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "HISTORY"
        }
        binding.toolbarHistory.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates()

    }
    private fun getAllCompletedDates() {

    mHistoryViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        //val allCompletedDatesList =
            mHistoryViewModel.readAllData.observe(this, { allCompletedDatesList ->

                if (allCompletedDatesList.isNotEmpty()) {
                    // Here if the List size is greater then 0 we will display the item in the recycler view or else we will show the text view that no data is available.
                    binding.tvHistory.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNoDataAvailable.visibility = View.GONE

                    // Creates a vertical Layout Manager
                    binding.rvHistory.layoutManager = LinearLayoutManager(this)

                    // History adapter is initialized and the list is passed in the param.
                    val historyAdapter = HistoryAdapter(this)

                    // Access the RecyclerView Adapter and load the data into it
                    binding.rvHistory.adapter = historyAdapter
                    historyAdapter.setData(allCompletedDatesList)

                } else {
                    binding.tvHistory.visibility = View.GONE
                    binding.rvHistory.visibility = View.GONE
                    binding.tvNoDataAvailable.visibility = View.VISIBLE
                }
            })


        // END
    }
}