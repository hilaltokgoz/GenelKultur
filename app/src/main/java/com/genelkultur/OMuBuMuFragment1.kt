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
import kotlinx.android.synthetic.main.fragment_o_mu_bu_mu1.*


class OMuBuMuFragment1 : Fragment() {


    var trueAnswer: String? = null
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
        iv_tarih_refresh_oBu.setOnClickListener {
            getDataTarihOmuBumu()
            rbTarihOptionA.isChecked = false
            rbTarihOptionB.isChecked = false

            rbTarihOptionA.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.set_sail_sampange
                )
            )
            rbTarihOptionB.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.set_sail_sampange
                )
            )


        }


        rbTarihOptionA.setOnClickListener {
            if (rbTarihOptionA.isChecked && trueAnswer == rbTarihOptionA.text) {
                rbTarihOptionA.setBackgroundColor(Color.GREEN)

            } else {
                //Yanlış bildi.
                rbTarihOptionA.setBackgroundColor(Color.RED)
                rbTarihOptionB.setBackgroundColor(Color.GREEN)
            }
        }
        rbTarihOptionB.setOnClickListener {
            if (rbTarihOptionB.isChecked && trueAnswer == rbTarihOptionB.text) {
                //Doğru bildi
                rbTarihOptionB.setBackgroundColor(Color.GREEN)
            } else {
                //Yanlış bildi.
                rbTarihOptionB.setBackgroundColor(Color.RED)
                rbTarihOptionA.setBackgroundColor(Color.GREEN)
            }
        }


    }


    fun getDataTarihOmuBumu() {
        val ref_t = Firebase.database.getReference("OmuBumu/Tarih")

        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd =
                    (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").value.toString() //soruyu text e atadık.
                soru.let { s ->
                    textView15.text = s
                    val o1 = ss.child("o1").value.toString()
                    val o2 = ss.child("o2").value.toString()
                    trueAnswer = o1
                    val rndOption = (0..1).random().toInt()//2 seçenek var 01,02
                    if (rndOption == 0) {
                        rbTarihOptionA.text = o1
                        rbTarihOptionB.text = o2
                    } else {
                        rbTarihOptionA.text = o2
                        rbTarihOptionB.text = o1
                    }

                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })


    }


}