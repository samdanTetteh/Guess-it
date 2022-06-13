package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    // LiveData is an observable data holder class that is lifecycle aware

    // The current word as Mutable live data
    private val _word = MutableLiveData<String>()


    private var timer: CountDownTimer

    /** This is to encapsulate my LiveData word object so it cannot be modified except for in the VM
     * Using the kotlin baking field feature to do this
     */
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score


    private val _gameFinishedEvent = MutableLiveData<Boolean>()
    val gameFinished : LiveData<Boolean>
        get() = _gameFinishedEvent

    private val _countDownTime = MutableLiveData<Long>()


    //Transformations help to combine live data value together.
    val countDownTimerString = Transformations.map(_countDownTime) { countDownTime ->
        DateUtils.formatElapsedTime(countDownTime / ONE_SECOND)
    }


    init {
        Log.d("Game ViewModel", "GameViewModel Created!!")
        resetList()
        nextWord()

        _score.value = 0
        _gameFinishedEvent.value = false



        timer = object : CountDownTimer(COUNT_DOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _countDownTime.value = millisUntilFinished
            }

            override fun onFinish() {
                _gameFinishedEvent.value = true
            }
        }.start()


    }


    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.d("Game ViewModel","GameViewModel Destroyed")
    }

    /** Methods for buttons presses **/

     fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        _score.value = _score.value?.plus(1)
        nextWord()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
//            _gameFinishedEvent.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    fun onGameFinishedComplete() { _gameFinishedEvent.value = false }



    companion object {

        private const val COUNT_DOWN_TIME = 10000L

        private const val ONE_SECOND = 1000L

        private const val done = 0L

    }
}