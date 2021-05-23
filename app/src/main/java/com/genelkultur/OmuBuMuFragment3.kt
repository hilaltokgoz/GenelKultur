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
import kotlinx.android.synthetic.main.fragment_omu_bu_mu3.*


class OmuBuMuFragment3 : Fragment() {

    var trueAnswer: String? = null
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
            rbGuncelOptionA.isChecked = false
            rbGuncelOptionB.isChecked = false

            rbGuncelOptionA.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.set_sail_sampange
                )
            )
            rbGuncelOptionB.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.set_sail_sampange
                )
            )
        }

        rbGuncelOptionA.setOnClickListener {
            if (rbGuncelOptionA.isChecked && trueAnswer == rbGuncelOptionA.text) {
                rbGuncelOptionA.setBackgroundColor(Color.GREEN)

            } else {
                //Yanlış bildi.
                rbGuncelOptionA.setBackgroundColor(Color.RED)
                rbGuncelOptionB.setBackgroundColor(Color.GREEN)
            }
        }
        rbGuncelOptionB.setOnClickListener {
            if (rbGuncelOptionB.isChecked && trueAnswer == rbGuncelOptionB.text) {
                //Doğru bildi
                rbGuncelOptionB.setBackgroundColor(Color.GREEN)
            } else {
                //Yanlış bildi.
                rbGuncelOptionB.setBackgroundColor(Color.RED)
                rbGuncelOptionA.setBackgroundColor(Color.GREEN)
            }
        }


    }


    fun getDataGuncelOmuBumu() {
        val ref_t = Firebase.database.getReference("OmuBumu/Guncel")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd =
                    (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").value.toString() //soruyu text e atadık.
                soru.let { s ->
                    textView20.text = s
                    val o1 = ss.child("o1").value.toString()
                    val o2 = ss.child("o2").value.toString()
                    trueAnswer = o1
                    val rndOption = (0..1).random().toInt()//2 seçenek var 01,02
                    if (rndOption == 0) {
                        rbGuncelOptionA.text = o1
                        rbGuncelOptionB.text = o2
                    } else {
                        rbGuncelOptionA.text = o2
                        rbGuncelOptionB.text = o1
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}