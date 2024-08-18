package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.FragmentSettingsBinding
import java.util.Locale

class FragmentSettings: Fragment() {

    lateinit var binding:FragmentSettingsBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       setArrayAdapterLanguagesAndModes()

        handleListenerForModes()

        handleListenerForLanguages()
    }

    private fun convertAppToArabic() {
        if(resources.configuration.locale.language != Locale.forLanguageTag("ar").toString()) {
            val config = context?.resources?.configuration
            config?.setLocale(Locale.forLanguageTag("ar"))
            context?.resources?.updateConfiguration(
                config,
                (context as MainActivity).resources.displayMetrics
            )
            activity?.recreate()
        }
    }

    private fun convertAppToEnglish() {

        if(resources.configuration.locale.language != Locale.ENGLISH.toString())
        {
            val config= context?.resources?.configuration
            config?.setLocale(Locale.ENGLISH)
            context?.resources?.updateConfiguration(config,(context as MainActivity).resources.displayMetrics)
            activity?.recreate()
        }

    }

    private fun handleListenerForLanguages() {
        binding.ActvLanguages.setOnItemClickListener(object: AdapterView.OnItemClickListener
        {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                when(position)
                {
                    0 -> convertAppToEnglish()
                    1 -> convertAppToArabic()
                }
            }
        })
    }

    private fun setArrayAdapterLanguagesAndModes()
    {
        val arrayAdapterLanguages= ArrayAdapter(requireContext(),R.layout.modes_and_language_item,resources.getStringArray(R.array.Languages))
        binding.ActvLanguages.setAdapter(arrayAdapterLanguages)

        val arrayAdapterModes= ArrayAdapter(requireContext(),R.layout.modes_and_language_item,resources.getStringArray(R.array.Modes))
        binding.ActvModes.setAdapter(arrayAdapterModes)
    }
    private fun handleListenerForModes() {

                binding.ActvModes.setOnItemClickListener(object: AdapterView.OnItemClickListener
                {
                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {


                        when(position)
                        {
                            0 -> goToLightMode()
                            1 -> goToNightMode()
                        }


                    }

                })








    }



    private fun goToLightMode() {
        if(!isLight())
        {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
    }

    private fun goToNightMode() {
        if(!isNight())
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }
    }

    private fun isNight(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    fun isLight() : Boolean
    {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO
    }

}