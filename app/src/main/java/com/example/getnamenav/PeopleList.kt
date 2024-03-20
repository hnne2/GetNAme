package com.example.getname.jsonReader

import com.google.gson.annotations.SerializedName

data class PeopleList(
    @SerializedName("array")
    val people: List<Person>
)

data class Person(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Gender")
    val gender: String,
    @SerializedName("Nation")
    val nationality: String,
    @SerializedName("Characters")
    val сharacters: Int
)

