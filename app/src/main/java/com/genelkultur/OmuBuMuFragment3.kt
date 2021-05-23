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
import kotlinx.android.synthetic.main.fragment_omu_bu_mu3.*


class OmuBuMuFragment3 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    var trueAnswer :String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_omu_bu_mu3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataGuncelOmuBumu()
        iv_guncel_refresh_oBu.setOnClickListener {
            getDataGuncelOmuBumu()
            radioButton5.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.set_sail_sampange))
            radioButton6.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.set_sail_sampange))
        }

        radioButton5.setOnClickListener {
            if (radioButton5.isChecked && trueAnswer==radioButton5.text){
                radioButton5.setBackgroundColor(Color.GREEN)

            }else{
                //Yanlış bildi.
                radioButton5.setBackgroundColor(Color.RED)
                radioButton6.setBackgroundColor(Color.GREEN)
            }
        }
        radioButton6.setOnClickListener {
            if (radioButton6.isChecked && trueAnswer==radioButton6.text){
                //Doğru bildi
                radioButton6.setBackgroundColor(Color.GREEN)
            }else{
                //Yanlış bildi.
                radioButton6.setBackgroundColor(Color.RED)
                radioButton5.setBackgroundColor(Color.GREEN)
            }
        }


    }







    fun getDataGuncelOmuBumu() {
        val ref_t = Firebase.database.getReference("OmuBumu/Guncel")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").getValue().toString() //soruyu text e atadık.
                soru?.let { s ->
                    textView20.text = s
                    val o1 = ss.child("o1").getValue().toString()
                    val o2 = ss.child("o2").getValue().toString()
                    trueAnswer = o1
                    val rndOption = (0..1).random().toInt()//2 seçenek var 01,02
                    if (rndOption == 0) {
                        radioButton5.text = o1
                        radioButton6.text = o2
                    } else {
                        radioButton5.text = o2
                        radioButton6.text = o1
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }}