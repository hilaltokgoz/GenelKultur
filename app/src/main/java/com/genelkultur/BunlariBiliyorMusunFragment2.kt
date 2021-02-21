package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_bunlari_biliyor_musun2.*


class BunlariBiliyorMusunFragment2 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bunlari_biliyor_musun2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var cografyaKonular = ArrayList<String>()
        cografyaKonular.add("Haritalar")
        cografyaKonular.add("Yagislar")

        val adapter = ArrayAdapter(requireContext(), R.layout.list_row_cografya_bbm, R.id.textViewTarih, cografyaKonular)
        listView2.adapter = adapter
        listView2.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val konu = cografyaKonular[position]
            val action = BunlariBiliyorMusunFragment2Directions.actionBunlariBiliyorMusunFragment2ToBBMFragment2toA(konu)
            listView2.findNavController().navigate(action)

        }

    }}


