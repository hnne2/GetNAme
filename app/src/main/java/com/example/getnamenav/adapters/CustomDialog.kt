package com.example.getnamenav.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getnamenav.R


class CustomDialog : DialogFragment() {
    var Names:List<String> = emptyList()


    override fun onCreateView(       //кастомный диалог содержащий ресайклер вью
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_layout, container, false)
        view.setBackgroundResource(R.drawable.zakrugl)
        dialog!!.window!!.setBackgroundDrawableResource(R.drawable.zakrugl)    //поставил закругленный фон
        var NamesRecyclerView = view.findViewById<RecyclerView>(R.id.NamesResiklerView)
        NamesRecyclerView.layoutManager=LinearLayoutManager(view.context)
        Log.e("onRestored",Names.toString())
        savedInstanceState?.let {
            Names = it.getStringArrayList("key")?.toList() ?: emptyList()

        }
        val adapter = MyAdapter(Names)   //создал кастомный адаптер для ресайклер вью
        NamesRecyclerView.adapter=adapter

        return view
    }
    fun setName(names:List<String>){   //перед открытием диалога получает список
        Names = names
    }
    override fun onSaveInstanceState(outState: Bundle) {   //сохраняет список для получения его при пересоздании активити
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("key", ArrayList(Names))
    }
}
