package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_bunlari_biliyor_musun.*


class BunlariBiliyorMusunFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bunlari_biliyor_musun, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buton10.setOnClickListener {
            val action=BunlariBiliyorMusunFragmentDirections.actionBunlariBiliyorMusunFragmentToBunlariBiliyorMusunFragment1()
            Navigation.findNavController(it).navigate(action)
        }

        button11.setOnClickListener {
            val action=BunlariBiliyorMusunFragmentDirections.actionBunlariBiliyorMusunFragmentToBunlariBiliyorMusunFragment2()
            Navigation.findNavController(it).navigate(action)
        }

        button12.setOnClickListener {
            val action=BunlariBiliyorMusunFragmentDirections.actionBunlariBiliyorMusunFragmentToBunlariBiliyorMusunFragment3()
            Navigation.findNavController(it).navigate(action)
        }



    }

}