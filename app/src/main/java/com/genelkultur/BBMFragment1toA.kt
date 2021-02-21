package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_b_b_m_fragment1to_a.*

class BBMFragment1toA : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_b_b_m_fragment1to_a, container, false)



   }
   //list view i diğer fragment a aktardın.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //görünümler falan oluşturulduktan sonra olusturulur.
        super.onViewCreated(view, savedInstanceState)
        var name:String?=null
        arguments?.let {
            name = BBMFragment1toAArgs.fromBundle(it).name
        }

        if (name!=null) textViewT.text=name
    }


}