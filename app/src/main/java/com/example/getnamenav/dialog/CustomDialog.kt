package com.example.getname.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getnamenav.R


class CustomDialog : DialogFragment() {
    var Names:List<String> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_layout, container, false)
        view.setBackgroundResource(R.drawable.zakrugl)
        dialog!!.window!!.setBackgroundDrawableResource(R.drawable.zakrugl)
        var NamesRecyclerView = view.findViewById<RecyclerView>(R.id.NamesResiklerView)
        NamesRecyclerView.layoutManager=LinearLayoutManager(view.context)
        val adapter = MyAdapter(Names)
        NamesRecyclerView.adapter=adapter

        return view
    }
    fun setName(names:List<String>){
        Names = names
    }
}
