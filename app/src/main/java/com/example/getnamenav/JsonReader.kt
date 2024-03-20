package com.example.getname.jsonReader

import android.content.Context
import com.example.getnamenav.R
import com.google.gson.Gson
import java.io.InputStreamReader

public class JsonReader {
 fun readJsonFile(context: Context, resourceId: Int): String {
  val inputStream = context.resources.openRawResource(resourceId)
  val reader = InputStreamReader(inputStream)
  return reader.readText()
 }
 fun GetNames(context: Context):PeopleList{
  val json = JsonReader().readJsonFile(context, R.raw.names)   //получаю список имен
  val gson = Gson()
  return gson.fromJson(json, PeopleList::class.java)
 }

}