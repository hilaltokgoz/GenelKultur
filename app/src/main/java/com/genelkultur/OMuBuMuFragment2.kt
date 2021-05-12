@file:Suppress("UNREACHABLE_CODE")

package com.genelkultur

import android.graphics.Color
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
import kotlinx.android.synthetic.main.fragment_b_b_m_fragment2to_a.*
import kotlinx.android.synthetic.main.fragment_o_mu_bu_mu2.*


class OMuBuMuFragment2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    var trueAnswer :String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_mu_bu_mu2, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataCografyaOmuBumu()

        iv_cografya_refresh_omubumu.setOnClickListener {
                getDataCografyaOmuBumu()
        }



        radioButton4.setOnClickListener {
            if (radioButton4.isChecked && trueAnswer==radioButton4.text){
                radioButton4.setBackgroundColor(Color.GREEN)
            }else{
                //Yanlış bildi.
                radioButton4.setBackgroundColor(Color.RED)
                radioButton3.setBackgroundColor(Color.GREEN)
            }
        }
        radioButton3.setOnClickListener {
            if (radioButton3.isChecked && trueAnswer==radioButton3.text){
                //Doğru bildi
                radioButton3.setBackgroundColor(Color.GREEN)
            }else{
                //Yanlış bildi.
                radioButton3.setBackgroundColor(Color.RED)
                radioButton4.setBackgroundColor(Color.GREEN)
            }
        }
    }



    fun getDataCografyaOmuBumu() {
        val ref_t = Firebase.database.getReference("OmuBumu").child("Cografya")//Cografyanın altındakileri referans al
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random().toInt()//id altındaki herseyi  random bi şekilde tutar snapshot
                val ss = snapshot.children.toList()[rnd]
                val soru1=ss.child("question").getValue().toString() //soruyu text e atadık.
                soru1.let { s->
                    textView16.text=s
                    val o1=ss.child("o1").getValue().toString()
                    val o2=ss.child("o2").getValue().toString()
                    trueAnswer=o1
                    val rndOption=(0..1).random().toInt()//2 seçenek var 01,02
                    if(rndOption==0){
                        radioButton4.text=o1
                        radioButton3.text=o2
                    }else{
                        radioButton4.text=o2
                        radioButton3.text=o1
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })



    }




}