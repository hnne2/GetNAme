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
import com.example.getnamenav.adapters.CustomDialog
import com.example.getnamenav.data.JsonReader
import com.example.getnamenav.data.Mymap
import com.example.getnamenav.R
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

        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)           // создние вью модел
        binding.viewModel = viewModel



        val GenderSpinerListOfItems = resources.getStringArray(R.array.genders)      //cоздание спинеров установка для них кастомных адаптеров
        var GenderSpiner = binding.spinnerGender
        val GendSpinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, GenderSpinerListOfItems)
        GendSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        GenderSpiner.adapter=GendSpinnerAdapter


        val NationSpinerListOfItems =  resources.getStringArray(R.array.nations)

        var NationSpiner =  binding.spinnerNation
        val NationSpinerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, NationSpinerListOfItems)
        NationSpinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        NationSpiner.adapter=NationSpinerAdapter

        NationSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {   // cлушатель выбора элемента в спинере
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.GetSelectedNation.value=position

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        GenderSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {                  // cлушатель выбора элемента в спинере
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.GetSelectedGender.value=position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.viewModel = viewModel


        var clicked =false
        viewModel.GetSelectedGender.observe(requireActivity()) { text ->  //при пересоздании фрагмента(смены орентации например) возвращает выбранную позицию в спинер
            if (isAdded()) {
                Log.e("gender", text.toString())
                var gspiner = binding.spinnerGender
                gspiner.post(Runnable { gspiner.setSelection(text.toInt()) })
            }

        }
        viewModel.GetSelectedNation.observe(requireActivity()) { text ->            // при пересоздании фрагмента(смены орентации например) возвращает выбранную позицию в спинер
            if (isAdded()) {
                Log.e("nation", text.toString())
                var NatSpiner = binding.spinnerNation
                NatSpiner.post(Runnable { NatSpiner.setSelection(text.toInt()) })
            }
        }



        binding.button.setOnClickListener {                              // листенер нажатия на кнопку получения имен
            if (viewModel.GetSelectedCharaters == null || viewModel.GetSelectedCharaters.value == "") {      //если не выбранно количество букв в имени вылетает соот. сообщение
            Toast.makeText(
                requireActivity(),
                resources.getText(R.string.charaktersCount),
                Toast.LENGTH_SHORT
            ).show()
            return@setOnClickListener
        }
        if (viewModel.GetSelectedCauntName.value == null || viewModel.GetSelectedCauntName.value.toString() == "") {   //если не выбранно количество имен для вывода вылетает соот. сообщение
            Toast.makeText(
                requireActivity(),
                resources.getString(R.string.namesCount),
                Toast.LENGTH_SHORT
            ).show()
            return@setOnClickListener
        }
        if (viewModel.GetSelectedCharaters.value!!.toInt() < 2 || viewModel.GetSelectedCharaters.value!!.toInt() > 10) {    // если вводят число символов в имени неверно тоаст
            Toast.makeText(
                requireActivity(),
                resources.getString(R.string.charaktersCountEror),
                Toast.LENGTH_SHORT
            ).show()
            return@setOnClickListener
        }

        if (viewModel.GetSelectedCauntName.value!!.toInt() < 1) {      // если вводят количество выводимых имен неверно тоаст
            Toast.makeText(
                requireActivity(),
                resources.getString(R.string.namesCountEror),
                Toast.LENGTH_SHORT
            ).show()
            return@setOnClickListener

        }
            clicked=true
            viewModel.GetFilterList(JsonReader().GetNames(requireActivity()))  // если все верно запрашиваю фильтрованный список имен

    }

        viewModel.mutablePeopleList.observe(requireActivity()){ // по приходу списка имен

            val dialog = CustomDialog()                                                      //открывает диалоговое окно отправляя ему полученные имена
            dialog.setName(it.take(viewModel.GetSelectedCauntName.value!!.toInt()).shuffled()    // перемешал список имен и отобрал первые n имен. где n число которое ввел пользователь
            )
            if (clicked){
                dialog.show(parentFragmentManager, "tag")
            }
        }
    }
}