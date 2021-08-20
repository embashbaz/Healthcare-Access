package com.example.yourmeduser.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.yourmeduser.R
import com.google.android.material.textfield.TextInputLayout


class SearchFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var searchFieldTl : TextInputLayout
    lateinit var typeSp: Spinner
    lateinit var countrySp: Spinner
    lateinit var searchBt: Button

    var searchText = ""
    var country = ""
    var type = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    fun bind(view: View){
        searchFieldTl  = view.findViewById(R.id.seach_text_tl)
        typeSp  = view.findViewById(R.id.type_sp)
        countrySp  = view.findViewById(R.id.country_sp)
        searchBt  = view.findViewById(R.id.search_bt)

        countrySp.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            countrySp.adapter = adapter
        }

        typeSp.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.search_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            typeSp.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null){
            if(parent.id == countrySp.id){
                country = parent.getItemAtPosition(position).toString()
            }else if(parent.id == typeSp.id){
                type = parent.getItemAtPosition(position).toString()
            }



        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}