package com.example.yourmeduser.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class NoticeDialogFragment(message: String, positiveText: String) : DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener
    val mMessage = message
    val positiveText = positiveText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Build the dialog and set up the button click handlers
            val builder = AlertDialog.Builder(it)

            builder.setMessage(mMessage)
                .setPositiveButton(positiveText,
                    DialogInterface.OnClickListener { dialog, id ->
                        // Send the positive button event back to the host activity
                        if (listener!= null)
                        listener.onDialogPositiveClick(this)
                        else
                            dialog.dismiss()
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Send the negative button event back to the host activity
                        dialog.dismiss()
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setListener(listener: NoticeDialogListener) {
        this.listener = listener
    }

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment){ }

    }


}