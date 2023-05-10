package com.example.vk.presentation.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.vk.R

class DialogType: DialogFragment() {

    interface TypeInputListener {
        fun onTypeInputted(name: String)
    }

    private lateinit var listener: TypeInputListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as TypeInputListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement NameInputListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_dialog, null)
        val inputEditText = view.findViewById<EditText>(R.id.et_type)
        builder.setView(view)
            .setTitle(R.string.type_dialog)
            .setPositiveButton(R.string.yes) { _, _ ->
                val name = inputEditText.text.toString()
                listener.onTypeInputted(name)
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }
        return builder.create()
    }
}