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
import kotlinx.android.synthetic.main.fragment_omu_bu_mu_vat.*


class OmuBuMuFragmentVat : Fragment() {

    var trueAnswer: String? = null
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
            rbVatandaslikOptionA.isChecked = false
            rbVatandaslikOptionB.isChecked = false

            rbVatandaslikOptionA.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.set_sail_sampange
                )
            )
            rbVatandaslikOptionB.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.set_sail_sampange
                )
            )
        }



        rbVatandaslikOptionA.setOnClickListener {
            if (rbVatandaslikOptionA.isChecked && trueAnswer == rbVatandaslikOptionA.text) {
                rbVatandaslikOptionA.setBackgroundColor(Color.GREEN)

            } else {
                //Yanlış bildi.
                rbVatandaslikOptionA.setBackgroundColor(Color.RED)
                rbVatandaslikOptionB.setBackgroundColor(Color.GREEN)
            }
        }
        rbVatandaslikOptionB.setOnClickListener {
            if (rbVatandaslikOptionB.isChecked && trueAnswer == rbVatandaslikOptionB.text) {
                //Doğru bildi
                rbVatandaslikOptionB.setBackgroundColor(Color.GREEN)
            } else {
                //Yanlış bildi.
                rbVatandaslikOptionB.setBackgroundColor(Color.RED)
                rbVatandaslikOptionA.setBackgroundColor(Color.GREEN)
            }
        }
    }


    fun getDataVatandaslikOmuBumu() {
        val ref_t = Firebase.database.getReference("OmuBumu/Vatandaslik")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd =
                    (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").value.toString() //soruyu text e atadık.
                soru.let { s ->
                    textView_v.text = s
                    val o1 = ss.child("o1").value.toString()
                    val o2 = ss.child("o2").value.toString()
                    trueAnswer = o1
                    val rndOption = (0..1).random().toInt()//2 seçenek var 01,02
                    if (rndOption == 0) {
                        rbVatandaslikOptionA.text = o1
                        rbVatandaslikOptionB.text = o2
                    } else {
                        rbVatandaslikOptionA.text = o2
                        rbVatandaslikOptionB.text = o1
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}