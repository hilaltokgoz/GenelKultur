package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_b_b_m_fragment1to_a.*
import kotlinx.android.synthetic.main.fragment_b_b_m_vat_to2.*

class BBMFragmentVatTo2 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_b_m_vat_to2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var info:String?=null
        arguments?.let {
            info = BBMFragmentVatTo2Args.fromBundle(it).info
        }

        if (info!=null) textViewV.text=info

    }



}