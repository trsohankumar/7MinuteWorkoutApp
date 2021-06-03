package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private var resetTimer:CountDownTimer?=null
    private var resetProgress:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarExerciseActivity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }
        setupResetView()
    }

    private fun setupResetView(){
        if(resetTimer != null){
            resetTimer!!.cancel()
            resetProgress = 0
        }

        setProgressBar()
    }


    private fun  setProgressBar(){
        binding.progressBar.progress = resetProgress
        resetTimer = object : CountDownTimer(10000,1000) {
            override fun onTick(millisUntilFinished: Long) {
               resetProgress++
                binding.progressBar.progress = 10 - resetProgress
                binding.tvTimer.text = (10 - resetProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Timer has been completed",Toast.LENGTH_SHORT).show()
            }
            
        }.start()
    }



    public override fun onDestroy() {
        if(resetTimer != null){
            resetTimer!!.cancel()
            resetProgress = 0
        }
        super.onDestroy()
    }

}