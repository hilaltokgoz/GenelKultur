package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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


        getData()



    }
    fun getData() {
        var cografyaKonular = ArrayList<String>()
        //Bunları biliyormusunuz cografya demi burda konular mı olucak ?
        val ref = Firebase.database.getReference("BunlariBiliyormusunuz/Cografya")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            //birkere çekileceği için
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataS in snapshot.children) {
                    //iyi bak tarim value değil key veri key içerisi value oke ?
                    // bize tarim lazım oyuzden keyi çekicez.
                    cografyaKonular.add(dataS.key.toString())
                }
                val adapter = ArrayAdapter(requireContext(), R.layout.list_row_cografya_bbm, R.id.textViewCografya, cografyaKonular)
                listView2.adapter = adapter
                listView2.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        val konu = cografyaKonular[position]
                        val action = BunlariBiliyorMusunFragment2Directions.actionBunlariBiliyorMusunFragment2ToBBMFragment2toA(konu)
                        listView2.findNavController().navigate(action)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                // database hatası oluşursa
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }








}


