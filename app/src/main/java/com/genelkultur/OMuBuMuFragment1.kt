package com.genelkultur

import android.graphics.Color
import android.graphics.Color.RED
import android.hardware.camera2.params.RggbChannelVector.RED
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorLong
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_b_b_m_fragment1to_a.*
import kotlinx.android.synthetic.main.fragment_o_mu_bu_mu1.*


class OMuBuMuFragment1 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    var trueAnswer :String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_mu_bu_mu1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataTarihOmuBumu()

        radioButton.setOnClickListener {
            if (radioButton.isChecked && trueAnswer==radioButton.text){
                radioButton.setBackgroundColor(Color.GREEN)

            }else{
             //Yanlış bildi.
                radioButton.setBackgroundColor(Color.RED)
                radioButton2.setBackgroundColor(Color.GREEN)
            }
        }
        radioButton2.setOnClickListener {
            if (radioButton2.isChecked && trueAnswer==radioButton2.text){
                //Doğru bildi
                radioButton2.setBackgroundColor(Color.GREEN)
            }else{
                //Yanlış bildi.
                radioButton2.setBackgroundColor(Color.RED)
                radioButton.setBackgroundColor(Color.GREEN)
            }
        }
    }




    fun getDataTarihOmuBumu() {
        val ref_t = Firebase.database.getReference("OmuBumu/Tarih")

        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru=ss.child("question").getValue().toString() //soruyu text e atadık.
                soru?.let {s->
                    textView15.text=s
                    val o1=ss.child("o1").getValue().toString()
                    val o2=ss.child("o2").getValue().toString()
                    trueAnswer=o1
                    val rndOption=(0..1).random().toInt()//2 seçenek var 01,02
                    if(rndOption==0){
                        radioButton.text=o1
                        radioButton2.text=o2
                    }else{
                        radioButton.text=o2
                        radioButton2.text=o1
                    }

                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })



    }





}