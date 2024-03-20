package com.example.getnamenav.uiGetName

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.getname.dialog.CustomDialog
import com.example.getname.jsonReader.JsonReader
import com.example.getnamenav.databinding.FragmentMyBinding

class MyFragment() : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MyFragment()
    }

    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        val view: View = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.viewModel = viewModel



        val GenderSpinerListOfItems = listOf("Мужское", "Женское", "Любое")
        var GenderSpiner = binding.spinnerGender
        val GendSpinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, GenderSpinerListOfItems)
        GendSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        GenderSpiner.adapter=GendSpinnerAdapter



        val NationSpinerListOfItems = listOf("Русское", "Американское", "Греческое","Любое")
        var NationSpiner =  binding.spinnerNation
        val NationSpinerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, NationSpinerListOfItems)
        NationSpinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        NationSpiner.adapter=NationSpinerAdapter

        NationSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.GetSelectedNation.value=position

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Вызывается, если ничего не выбрано
            }
        }
        GenderSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                viewModel.GetSelectedGender.value=position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        return view
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        viewModel.GetSelectedGender.observe(requireActivity()) { text ->
            if (isAdded()) {
                Log.e("gender", text.toString())
                var gspiner = binding.spinnerGender
                gspiner.post(Runnable { gspiner.setSelection(text.toInt()) })
            }

        }
        viewModel.GetSelectedNation.observe(requireActivity()) { text ->
            if (isAdded()) {
                Log.e("nation", text.toString())
                var NatSpiner = binding.spinnerNation
                NatSpiner.post(Runnable { NatSpiner.setSelection(text.toInt()) })
            }
        }

        val GenderMap = mapOf(
            0 to "Мужское",
            1 to "Женское",
            2 to "Любое",
        )
        val NationMap = mapOf(
            0 to "Русское",
            1 to "Американское",
            2 to "Греческое",
            3 to "Любое"
        )

        binding.viewModel = viewModel



        val peopleList = JsonReader().GetNames(requireActivity())

        viewModel.GetNameButonCleked.observe(viewLifecycleOwner){
                newValue->
            if (newValue){

                if (viewModel.GetSelectedCharaters ==null||viewModel.GetSelectedCharaters.value==""){
                    Toast.makeText(requireActivity(),"Введите желаемое количество букв в имени", Toast.LENGTH_SHORT).show()
                    return@observe
                }
                if (viewModel.GetSelectedCauntName.value ==null||viewModel.GetSelectedCauntName.value.toString()==""){
                    Toast.makeText(requireActivity(),"Введите желаемое количество имен для вывода", Toast.LENGTH_SHORT).show()
                    return@observe
                }

                val filteredNames = peopleList.people.filter { person ->
                    (person.gender == GenderMap.get(viewModel.GetSelectedGender.value) || GenderMap.get(viewModel.GetSelectedGender.value)=="Любое") && (person.nationality == NationMap.get(viewModel.GetSelectedNation.value)  ||NationMap.get(viewModel.GetSelectedNation.value)=="Любое" ) && person.сharacters == viewModel.GetSelectedCharaters.value!!.toInt()
                }.map { it.name }

                if (viewModel.GetSelectedCharaters.value!!.toInt()<2||viewModel.GetSelectedCharaters.value!!.toInt()>10){
                    Toast.makeText(requireActivity(),"Длинна имени должна быть от 2 до 10 символов", Toast.LENGTH_SHORT).show()
                    return@observe
                }

                if (viewModel.GetSelectedCauntName.value!!.toInt() <1){
                    Toast.makeText(requireActivity(),"Введите желаемое количество имен для вывода", Toast.LENGTH_SHORT).show()
                    return@observe

                }
                val dialog = CustomDialog()
                dialog.setName(filteredNames.take(viewModel.GetSelectedCauntName.value!!.toInt()).shuffled())
                dialog.show(parentFragmentManager,"tag")
            }
        }

    }


}