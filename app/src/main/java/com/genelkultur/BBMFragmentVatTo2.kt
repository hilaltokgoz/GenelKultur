package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_b_b_m_fragment1to_a.*
import kotlinx.android.synthetic.main.fragment_b_b_m_vat_to2.*

class BBMFragmentVatTo2 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_b_m_vat_to2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var info:String?=null
        arguments?.let {
            info = BBMFragmentVatTo2Args.fromBundle(it).info
            info?.let { k->
                getDataVatandaslikBilgi(k)
            }
            //çek...
        }
        imageV2.setOnClickListener {
            info?.let { k->
                getDataVatandaslikBilgi(k)
            }
        }
        imageViewV.setOnClickListener {
            info?.let { k->
                getDataVatandaslikBilgi(k)
            }
        }
    }


    fun getDataVatandaslikBilgi(konu:String){

        val ref_t = Firebase.database.getReference("BunlariBiliyormusunuz/Vatandaslik/$konu")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random().toInt()
                val ss = snapshot.children.toList()[rnd]
                val data = ss.child("data").getValue().toString()
                textViewV.text=data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }



}
