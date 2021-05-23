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
import kotlinx.android.synthetic.main.fragment_bunlari_biliyor_musun1.*

class BunlariBiliyorMusunFragment1 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bunlari_biliyor_musun1, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataTarih()
    }

    fun getDataTarih() {
        var tarihKonular = ArrayList<String>()
        val ref_t = Firebase.database.getReference("BunlariBiliyormusunuz/Tarih")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sbBBM = StringBuilder()
                for (dataS in snapshot.children) {
                    //veri key içerisi value
                    tarihKonular.add(dataS.key.toString())

                }
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.list_row,
                    R.id.textViewTarih,
                    tarihKonular
                )
                listView.adapter = adapter
                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        val konuT = tarihKonular[position]
                        val action =
                            BunlariBiliyorMusunFragment1Directions.actionBunlariBiliyorMusunFragment1ToBBMFragment1toA(
                                konuT
                            )
                        listView.findNavController().navigate(action)

                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}
