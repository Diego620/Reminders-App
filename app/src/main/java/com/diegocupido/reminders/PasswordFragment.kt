package com.diegocupido.reminders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.diegocupido.reminders.databinding.DialogEditReminderBinding
import com.diegocupido.reminders.databinding.FragmentPasswordsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PasswordFragment: Fragment() {

    private lateinit var binding: FragmentPasswordsBinding
    private val preferences by lazy { requireActivity().getSharedPreferences("passwords", Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()
        binding.cardViewWifi.setOnClickListener{ showEditDialog(PREF_WIFI)}
        binding.cardViewXboxPin.setOnClickListener{ showEditDialog(PREF_XBOX_PIN)}
        binding.cardViewPhonePin.setOnClickListener{ showEditDialog(PREF_PHONE_PIN)}
    }


    private fun displayValues() {
        binding.textViewWifiValue.text = preferences.getString(PREF_WIFI, null)
        binding.textViewXboxPinValue.text = preferences.getString(PREF_XBOX_PIN, null)
        binding.textViewPhonePinValue.text = preferences.getString(PREF_PHONE_PIN, null)
    }

    private fun showEditDialog(preferenceKey: String) {
        val dialogBinding = DialogEditReminderBinding.inflate(requireActivity().layoutInflater)
        dialogBinding.editTextValue.setText(preferences.getString(preferenceKey,null))
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update value")
            .setView(dialogBinding.root)
            .setPositiveButton("Save"){ _,_ ->
                preferences.edit { putString(preferenceKey, dialogBinding.editTextValue.text?.toString()) }
                displayValues()
            }
            .setNegativeButton("Cancel"){ _,_ ->

            }.show()
    }

    companion object{

        const val PREF_WIFI = "pref_wifi"
        const val PREF_XBOX_PIN = "pref_xbox_pin"
        const val PREF_PHONE_PIN = "pref_phone_pin"
    }
}