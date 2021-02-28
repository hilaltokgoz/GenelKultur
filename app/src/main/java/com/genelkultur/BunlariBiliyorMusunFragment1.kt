package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_bunlari_biliyor_musun1.*


class BunlariBiliyorMusunFragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bunlari_biliyor_musun1, container, false)

////Datalar internetten çekilecek.Liste Oluşucak.




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var konular=ArrayList<String>()
        konular.add("inkilaplar")// elle yazarsak
        konular.add("1.Dünya Savaşı")
        konular.add("fkjgnjfnjfnjvndjnvjdnvjfdnjvfdvnjfdvnjdnvjdfnvjdfvjdnfvjdjvndfjvjdfnvjfnjdfvnjdfnvjdfvjdfnvjdfvjfnnjnvffnvdjnvkjdfjvndfjkvn")

        val adapter= ArrayAdapter(requireContext(),R.layout.list_row,R.id.textViewTarih,konular) //list row u kendim oluşturdum. list row daki text view id si textView5 .
        listView.adapter=adapter //adapter ile liste eşitlendi

        //tıklanınca ne olacak

        listView.onItemClickListener=AdapterView.OnItemClickListener { parent, view, position, id ->   //item1 de position 0 olacak(indeks)
            val konu = konular[position]
            val action=BunlariBiliyorMusunFragment1Directions.actionBunlariBiliyorMusunFragment1ToBBMFragment1toA(konu)
            listView.findNavController().navigate(action)

        }
    }

}