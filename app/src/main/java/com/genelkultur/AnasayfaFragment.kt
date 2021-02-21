package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_anasayfa.*


class AnasayfaFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anasayfa, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//bbm buton id: button
     button.setOnClickListener {
         val action=AnasayfaFragmentDirections.actionAnasayfaFragmentToBunlariBiliyorMusunFragment() //action oluşturuldu.
        Navigation.findNavController(it).navigate(action)
     }

        //omubumu buton id:buttın2
        button2.setOnClickListener {
            val action=AnasayfaFragmentDirections.actionAnasayfaFragmentToOMuBuMuFragment()
            Navigation.findNavController(it).navigate(action)
        }

        //test buton id:button3
        button3.setOnClickListener {
            val  action=AnasayfaFragmentDirections.actionAnasayfaFragmentToTestFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }


}