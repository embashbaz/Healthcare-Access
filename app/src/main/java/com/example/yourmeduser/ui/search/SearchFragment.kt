package com.example.yourmeduser.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.yourmeduser.R
import com.example.yourmeduser.data.InDemand
import com.example.yourmeduser.ui.dialogs.InDemandDialog
import com.example.yourmeduser.ui.dialogs.InfoDialog
import com.example.yourmeduser.ui.dialogs.NoticeDialogFragment
import com.google.android.material.textfield.TextInputLayout


class SearchFragment : Fragment(), AdapterView.OnItemSelectedListener,NoticeDialogFragment.NoticeDialogListener, InDemandDialog.InDemandItemListener {

    lateinit var searchFieldTl : TextInputLayout
    lateinit var typeSp: Spinner
    lateinit var countrySp: Spinner
    lateinit var searchBt: Button

    var searchText = ""
    var country = ""
    var type = ""
    var inDemand: InDemand? = null

    val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)
        bind(view)

        searchBt.setOnClickListener{
            searchText = searchFieldTl.editText?.text.toString()

            if (!searchText.isNullOrEmpty()){
                search()

            }else{
                openInfodialog("You have to specify what you are looking for", "Error")
            }


        }


        return view
    }

    fun search(){
                  searchViewModel.getData(country, searchText, searchText, getType())
            searchViewModel.resultFromQuery.observe(viewLifecycleOwner,{
                if(it.status == "success" && it.statusValue == "sucsess"){

                }else if(it.status == "success" && it.statusValue != "success"){
                    openNoticeDialog(2, "Your query did not return any result, " +
                            "Would you like to add this service or medicine to the list of needed services or medicine in your area?")
                }else if (it.status == "failed"){
                    openInfodialog(it.statusValue, "Failed")
                }

            })
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

    fun openNoticeDialog(confirmCode: Int, message: String){
        val dialog = NoticeDialogFragment(confirmCode, message, "Yes")
        dialog.setListener(this)
        dialog.show(parentFragmentManager, "Confirm")

    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        inDemand = InDemand(1,"", searchText, "", country)
        val dialog = InDemandDialog(searchText)
        dialog.setListener(this)
        dialog.show(parentFragmentManager, "Add to InDemand")

    }

    fun openInfodialog(message : String, tag: String){
        val dialog = InfoDialog(message)
        dialog?.show(parentFragmentManager, tag)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null){
            if(parent.id == countrySp.id){
                country = parent.getItemAtPosition(position).toString()
            }else if(parent.id == typeSp.id){
                type = parent.getItemAtPosition(position).toString()
            }

            if(!country.isNullOrEmpty() && !type.isNullOrEmpty()){
                searchFieldTl.isEnabled = true
            }


        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onAddItemClick(county: String, contact: String) {

        inDemand!!.countyName = county
        searchViewModel.addToInDemand(inDemand!!,getDeviceId(),contact,  getType())
        searchViewModel.addingServiceOutput.observe(viewLifecycleOwner,{
            if (it["status"] == "success"){
                openInfodialog(it["value"]!!, "Success")
            }else if (it["status"] == "failed"){
                openInfodialog(it["value"]!!, "Failed")
            }
        })

    }

    fun getType(): Int{
        if(type =="Medical service or procedure"){
            return 2
        }else if (type == "Medicine"){
            return 1
        }else{
            return 0
        }
    }

    fun getDeviceId(): String{
        @SuppressLint("HardwareIds") val android_id = Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
        return android_id
    }


}