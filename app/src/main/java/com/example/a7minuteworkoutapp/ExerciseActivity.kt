package com.example.a7minuteworkoutapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() ,TextToSpeech.OnInitListener{
    private lateinit var binding: ActivityExerciseBinding
    private var restTimer:CountDownTimer?=null
    private var restProgress:Int = 0
    private var restDuration:Long = 10
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress:Int = 0
    private var exerciseDuration:Long = 30
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1
    private var tts:TextToSpeech?=null
    private var player:MediaPlayer?=null

    private var exerciseAdapter:ExerciseStatusAdapter? = null


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
        setUpExerciseStatusView()

    }
//Rest view is the view when there is no exercise
    private fun setupRestView(){
        try{
            player = MediaPlayer.create(applicationContext,R.raw.press_start)
            player!!.isLooping = false //To prevent constant looping
            player!!.start()
        }catch(e:Exception){
            e.printStackTrace()
        }
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
        restTimer = object : CountDownTimer(restDuration*1000,1000) {
            override fun onTick(millisUntilFinished: Long) {
               restProgress++
                binding.restProgressBar.progress = 10 - restProgress
                binding.tvRestTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                binding.llRestView.visibility = View.GONE
                binding.llExerciseView.visibility = View.VISIBLE
                currentExercisePosition++
                exerciseList!![currentExercisePosition].isSelected  = true
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()

            }
            
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.exerciseProgressBar.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(exerciseDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.exerciseProgressBar.progress = 30 - exerciseProgress
                binding.tvExerciseTimer.text = (30-exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! -1){
                    binding.llRestView.visibility = View.VISIBLE
                    binding.llExerciseView.visibility = View.GONE
                    exerciseList!![currentExercisePosition].isSelected = false
                    exerciseList!![currentExercisePosition].isCompleted = true
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
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
        if(player != null){
            player!!.stop()
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

    private fun setUpExerciseStatusView(){
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!,this)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }
}