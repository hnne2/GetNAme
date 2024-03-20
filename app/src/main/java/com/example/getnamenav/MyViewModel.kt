package com.example.getnamenav

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val GetSelectedCharaters: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val GetSelectedCauntName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val GetSelectedGender: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val GetSelectedNation: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


    val GetNameButonCleked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


    fun GetNameButtonClicked(){
        Log.e("tag","clkekid")
        GetNameButonCleked.value = true
    }

}