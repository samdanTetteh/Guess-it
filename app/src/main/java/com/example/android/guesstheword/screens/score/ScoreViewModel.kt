package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {

    private val _playAgainEvent = MutableLiveData<Boolean>()
    val playAgainEvent: LiveData<Boolean>
        get() = _playAgainEvent

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score


    init {
       _score.value = finalScore
    }


    fun onPlayAgainEvent(){
        _playAgainEvent.value = true
    }


    fun onPlayAgainEventCompleted(){
        _playAgainEvent.value = false
    }
}