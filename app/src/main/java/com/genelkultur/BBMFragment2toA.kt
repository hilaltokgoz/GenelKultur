package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_b_b_m_fragment1to_a.*
import kotlinx.android.synthetic.main.fragment_b_b_m_fragment2to_a.*


class BBMFragment2toA : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_b_m_fragment2to_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //görünümler falan oluşturulduktan sonra olusturulur.
        super.onViewCreated(view, savedInstanceState)
        var cografya:String?=null
        arguments?.let {
           cografya=BBMFragment2toAArgs.fromBundle(it).cografya
    }
        if (cografya!=null) textViewC.text=cografya
}}