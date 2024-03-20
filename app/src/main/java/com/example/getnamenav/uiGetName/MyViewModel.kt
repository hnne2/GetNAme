package com.example.getnamenav.uiGetName

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getnamenav.data.Mymap
import com.example.getnamenav.data.PeopleList

class MyViewModel : ViewModel() {



    var mymap=Mymap()
    val mutablePeopleList: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

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
    fun GetFilterList(peopleList: PeopleList){
        val filteredNames = peopleList.people.filter { person ->                      // фильтрует список имен проходя по всем, если все соответсвующие строки в текущем person равны соответсвующем выбранному пункту в приложении то оставляет в списке
            (person.gender == mymap.GenderMap.get(this.GetSelectedGender.value) || mymap.GenderMap.get(
                this.GetSelectedGender.value
            ) == "Любое") && (person.nationality == mymap.NationMap.get(this.GetSelectedNation.value) || mymap.NationMap.get(   //или соответсвуют или равны "Любое"
                this.GetSelectedNation.value
            ) == "Любое") && person.сharacters == this.GetSelectedCharaters.value!!.toInt()
        }.map { it.name }
       mutablePeopleList.value=filteredNames
    }

}