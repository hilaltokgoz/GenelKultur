package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_o_mu_bu_mu.*


class OMuBuMuFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_mu_bu_mu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         buton30.setOnClickListener {
             val action=OMuBuMuFragmentDirections.actionOMuBuMuFragmentToOMuBuMuFragment1()
             Navigation.findNavController(it).navigate(action)
         }
        button31.setOnClickListener {
            val action=OMuBuMuFragmentDirections.actionOMuBuMuFragmentToOMuBuMuFragment2()
            Navigation.findNavController(it).navigate(action)
        }

        button32.setOnClickListener {
            val action=OMuBuMuFragmentDirections.actionOMuBuMuFragmentToOmuBuMuFragment3()
            Navigation.findNavController(it).navigate(action)
        }
        button_obu_vat.setOnClickListener {
            val action=OMuBuMuFragmentDirections.actionOMuBuMuFragmentToOmuBuMuFragmentVat()
            Navigation.findNavController(it).navigate(action)
        }
    }
}