package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_b_b_m_vat.*
import kotlinx.android.synthetic.main.fragment_bunlari_biliyor_musun1.*
import kotlinx.android.synthetic.main.fragment_bunlari_biliyor_musun1.listView


class BBMFragmentVat : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_b_m_vat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var konular=ArrayList<String>()
        konular.add("Vatandaşlık bilgim yüzde 0")// elle yazarsak
        konular.add("Anayasa")
        konular.add("fkjgnjfnjfnjvndjnvjdnvjfdnjvfdvnjfdvnjdnvjdfnvjdfvjdnfvjdjvndfjvjdfnvjfnjdfvnjdfnvjdfvjdfnvjdfvjfnnjnvffnvdjnvkjdfjvndfjkvn")

        val adapter= ArrayAdapter(requireContext(),R.layout.list_view_vatandaslik,R.id.textViewVatandaslik,konular) //list row u kendim oluşturdum. list row daki text view id si textView5 .
        listViewV.adapter=adapter //adapter ile liste eşitlendi

        //tıklanınca ne olacak

        listViewV.onItemClickListener= AdapterView.OnItemClickListener { parent, view, position, id ->   //item1 de position 0 olacak(indeks)
            val info = konular[position]
           val action=BBMFragmentVatDirections.actionBBMFragmentVatToBBMFragmentVatTo2(info)
            listViewV.findNavController().navigate(action)

        }
    }


}