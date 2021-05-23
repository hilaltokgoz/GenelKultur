@file:Suppress("UNREACHABLE_CODE")

package com.genelkultur

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_o_mu_bu_mu2.*


class OMuBuMuFragment2 : Fragment() {

    var trueAnswer: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_mu_bu_mu2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataCografyaOmuBumu()

        iv_cografya_refresh_omubumu.setOnClickListener {
            getDataCografyaOmuBumu()
            rbCografyaOptionA.isChecked = false
            rbCografyaOptionB.isChecked = false

            rbCografyaOptionA.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.set_sail_sampange
                )
            )
            rbCografyaOptionB.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.set_sail_sampange
                )
            )
        }



        rbCografyaOptionB.setOnClickListener {
            if (rbCografyaOptionB.isChecked && trueAnswer == rbCografyaOptionB.text) {
                rbCografyaOptionB.setBackgroundColor(Color.GREEN)
            } else {
                //Yanlış bildi.
                rbCografyaOptionB.setBackgroundColor(Color.RED)
                rbCografyaOptionA.setBackgroundColor(Color.GREEN)
            }
        }
        rbCografyaOptionA.setOnClickListener {
            if (rbCografyaOptionA.isChecked && trueAnswer == rbCografyaOptionA.text) {
                //Doğru bildi
                rbCografyaOptionA.setBackgroundColor(Color.GREEN)
            } else {
                //Yanlış bildi.
                rbCografyaOptionA.setBackgroundColor(Color.RED)
                rbCografyaOptionB.setBackgroundColor(Color.GREEN)
            }
        }
    }


    fun getDataCografyaOmuBumu() {
        val ref_t = Firebase.database.getReference("OmuBumu")
            .child("Cografya")//Cografyanın altındakileri referans al
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random()
                    .toInt()//id altındaki herseyi  random bi şekilde tutar snapshot
                val ss = snapshot.children.toList()[rnd]
                val soru1 = ss.child("question").value.toString() //soruyu text e atadık.
                soru1.let { s ->
                    textView16.text = s
                    val o1 = ss.child("o1").value.toString()
                    val o2 = ss.child("o2").value.toString()
                    trueAnswer = o1
                    val rndOption = (0..1).random().toInt()//2 seçenek var 01,02
                    if (rndOption == 0) {
                        rbCografyaOptionB.text = o1
                        rbCografyaOptionA.text = o2
                    } else {
                        rbCografyaOptionB.text = o2
                        rbCografyaOptionA.text = o1
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })


    }


}