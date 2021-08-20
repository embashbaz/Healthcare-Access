package com.example.yourmeduser.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.yourmeduser.R
import com.google.android.material.textfield.TextInputLayout

class InDemandDialog(name: String): DialogFragment() {

    internal lateinit var listener: InDemandItemListener

    lateinit var nameIndemandItem: TextView
    lateinit var countyTl: TextInputLayout
    lateinit var contactTl: TextInputLayout
    lateinit var saveBt: Button


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {

            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.in_demand_dialog, null)

            nameIndemandItem = view.findViewById(R.id.item_in_demand_txt)
            countyTl = view.findViewById(R.id.location_tl)
            contactTl = view.findViewById(R.id.contact_tl)
            saveBt = view.findViewById(R.id.add_indemand_bt)

            saveBt.setOnClickListener{
                val county = countyTl.editText?.text.toString()
                val contact = contactTl.editText?.text.toString()

                if (!county.isNullOrEmpty() && !contact.isNullOrEmpty()){
                    listener.onAddItemClick(county, contact)
                }else{

                }
                
            }

            builder.setView(view)
                .setNegativeButton("Cancel"){
                    dialog, id -> dialog.dismiss()
                }

                builder.create()

        }?: throw IllegalStateException("Activity cannot be null")
    }

    interface InDemandItemListener{
        fun onAddItemClick(county: String, contact: String)
    }

    fun setListener(listener: InDemandItemListener) {
        this.listener = listener
    }


}