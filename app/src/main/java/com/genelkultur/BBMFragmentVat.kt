package com.genelkultur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_b_b_m_vat.*


class BBMFragmentVat : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_b_m_vat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataVatandaslik()
    }


    fun getDataVatandaslik() {
        var vatKonular = ArrayList<String>()
        val ref_v = Firebase.database.getReference("BunlariBiliyormusunuz/Vatandaslik")
        ref_v.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataS in snapshot.children) {
                    //veri key içerisi value
                    vatKonular.add(dataS.key.toString())
                }
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.list_view_vatandaslik,
                    R.id.textViewVatandaslik,
                    vatKonular
                ) //list row u kendim oluşturdum. list row daki text view id si textView5 .
                listViewV.adapter = adapter //adapter ile liste eşitlendi
                listViewV.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->   //item1 de position 0 olacak(indeks)
                        val info = vatKonular[position]
                        val action =
                            BBMFragmentVatDirections.actionBBMFragmentVatToBBMFragmentVatTo2(info)
                        listViewV.findNavController().navigate(action)

                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


}