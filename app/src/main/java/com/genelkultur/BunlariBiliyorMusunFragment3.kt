package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_bunlari_biliyor_musun3.*


class BunlariBiliyorMusunFragment3 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bunlari_biliyor_musun3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var konular=ArrayList<String>()
        konular.add("2020'de neler oldu?")
        konular.add("İlk kadın programcı kimdir?")
        val adapter=ArrayAdapter(requireContext(),R.layout.list_guncel,R.id.textViewG,konular)
        listViewGuncel.adapter=adapter

       listViewGuncel.onItemClickListener= AdapterView.OnItemClickListener { parent, view, position, id ->
           val konu = konular[position]
           val action =
               BunlariBiliyorMusunFragment3Directions.actionBunlariBiliyorMusunFragment3ToBBmFragment3toA(
                   konu
               )
           listViewGuncel.findNavController().navigate(action)


       }}

        }



