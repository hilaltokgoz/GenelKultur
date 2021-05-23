package com.genelkultur

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_o_mu_bu_mu1.*
import kotlinx.android.synthetic.main.fragment_o_mu_bu_mu1.textView15
import kotlinx.android.synthetic.main.fragment_omu_bu_mu_vat.*


class OmuBuMuFragmentVat : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    var trueAnswer :String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_omu_bu_mu_vat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataVatandaslikOmuBumu()
        iv_vat_refresh_oBu.setOnClickListener {
            getDataVatandaslikOmuBumu()
            radioButtonVat1.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.set_sail_sampange))
            radioButtonVat2.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.set_sail_sampange))
        }



        radioButtonVat1.setOnClickListener {
            if (radioButtonVat1.isChecked && trueAnswer==radioButtonVat1.text){
                radioButtonVat1.setBackgroundColor(Color.GREEN)

            }else{
                //Yanlış bildi.
                radioButtonVat1.setBackgroundColor(Color.RED)
                radioButtonVat2.setBackgroundColor(Color.GREEN)
            }
        }
        radioButtonVat2.setOnClickListener {
            if (radioButtonVat2.isChecked && trueAnswer==radioButtonVat2.text){
                //Doğru bildi
                radioButtonVat2.setBackgroundColor(Color.GREEN)
            }else{
                //Yanlış bildi.
                radioButtonVat2.setBackgroundColor(Color.RED)
                radioButtonVat1.setBackgroundColor(Color.GREEN)
            }
        }
    }



    fun getDataVatandaslikOmuBumu() {
        val ref_t = Firebase.database.getReference("OmuBumu/Vatandaslik")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru=ss.child("question").getValue().toString() //soruyu text e atadık.
                soru?.let {s->
                    textView_v.text=s
                    val o1=ss.child("o1").getValue().toString()
                    val o2=ss.child("o2").getValue().toString()
                    trueAnswer=o1
                    val rndOption=(0..1).random().toInt()//2 seçenek var 01,02
                    if(rndOption==0){
                        radioButtonVat1.text=o1
                        radioButtonVat2.text=o2
                    }else{
                        radioButtonVat1.text=o2
                        radioButtonVat2.text=o1
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }}