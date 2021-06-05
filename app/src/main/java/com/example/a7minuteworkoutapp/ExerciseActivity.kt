package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() ,TextToSpeech.OnInitListener{
    private lateinit var binding: ActivityExerciseBinding
    private var restTimer:CountDownTimer?=null
    private var restProgress:Int = 0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress:Int = 0
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1
    private var tts:TextToSpeech?=null

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
        binding.llExerciseView.visibility =View.GONE
        exerciseList = Exercise.defaultExerciseList()
        tts = TextToSpeech(this,this)
        setupRestView()


    }
//Rest view is the view when there is no exercise
    private fun setupRestView(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        binding.tvNextExercise.text = exerciseList!![currentExercisePosition+1].name
        setRestProgressBar()
    }

    private fun setupExerciseView(){


        binding.ivExerciseImage.setImageResource(exerciseList!![currentExercisePosition].image)
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].name
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].name)
        setExerciseProgressBar()
    }


    private fun  setRestProgressBar(){
        binding.restProgressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000,1000) {
            override fun onTick(millisUntilFinished: Long) {
               restProgress++
                binding.restProgressBar.progress = 10 - restProgress
                binding.tvRestTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                binding.llRestView.visibility = View.GONE
                binding.llExerciseView.visibility = View.VISIBLE
                currentExercisePosition++
                setupExerciseView()

            }
            
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.exerciseProgressBar.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.exerciseProgressBar.progress = 30 - exerciseProgress
                binding.tvExerciseTimer.text = (30-exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! -1){
                    binding.llRestView.visibility = View.VISIBLE
                    binding.llExerciseView.visibility = View.GONE
                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity,"Congratulations you have completed the 7 min workout",Toast.LENGTH_SHORT).show()
                }

            }
        }.start()
    }


    public override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if(tts != null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    //Code for the text to Speech converter
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("tts","Language not supported")
            }
        }else{
            Log.e("tts","Initialisation failed")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}