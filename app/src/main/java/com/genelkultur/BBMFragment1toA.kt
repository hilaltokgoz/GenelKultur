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
import java.util.*
import kotlin.random.Random

class BBMFragment1toA : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_b_b_m_fragment1to_a, container, false)



   }
   //list view i diğer fragment a aktardın.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //görünümler falan oluşturulduktan sonra olusturulur.
        super.onViewCreated(view, savedInstanceState)


        var konu:String?=null
        arguments?.let {
           konu = BBMFragment1toAArgs.fromBundle(it).name
           konu?.let { k->
               getDataTarihBilgi(k)
           }
            //çek...
        }
       btn_right.setOnClickListener {
           konu?.let { k->
               getDataTarihBilgi(k)
           }
       }

    }



    fun getDataTarihBilgi(konu:String){

        val ref_t = Firebase.database.getReference("BunlariBiliyormusunuz/Tarih/$konu")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random().toInt()
                val ss = snapshot.children.toList()[rnd]
                val data = ss.child("data").getValue().toString()
                tv_data.text=data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


}
