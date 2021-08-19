package com.example.yourmeduser.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class InfoDialog (message: String) : DialogFragment() {
    // Use this instance of the interface to deliver action events

    val mMessage = message

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Build the dialog and set up the button click handlers
            val builder = AlertDialog.Builder(it)

            builder.setMessage(mMessage)

                .setNegativeButton("Ok") { dialog, id ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}