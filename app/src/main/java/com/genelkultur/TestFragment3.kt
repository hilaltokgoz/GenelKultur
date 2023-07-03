package com.genelkultur

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_test_detail.*
import kotlinx.android.synthetic.main.dialog_test_result.*
import kotlinx.android.synthetic.main.fragment_test4.*

class TestFragment3 : Fragment() {
    var runnable: Runnable = Runnable { }   //Runnable bir arayüz
    var handler: Handler = Handler()  //süreyi de handler da oluşturcam.
    var time: Int = -1
    var trueResponse = 0
    var falseResponse = 0
    var false4deltrue: Boolean = false

    var trueAnswer: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test4, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = Dialog(requireContext())
        showSettingsDialogGuncel()
        getTestGuncel()
        iv_guncel_refresh_test.setOnClickListener {
            setEnableRadioButtons(true)

            getTestGuncel()

            rbGuncelOptionA.isChecked = false
            rbGuncelOptionB.isChecked = false
            rbGuncelOptionC.isChecked = false
            rbGuncelOptionD.isChecked = false
            rbGuncelOptionE.isChecked = false

            rbGuncelOptionA.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbGuncelOptionB.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbGuncelOptionC.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbGuncelOptionD.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbGuncelOptionE.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
        }
        rbGuncelOptionA.setOnClickListener {
            //checkAnswer(radioButt
            checkAnswer(rbGuncelOptionA)
        }

        rbGuncelOptionB.setOnClickListener {
            checkAnswer(rbGuncelOptionB)
        }


        rbGuncelOptionC.setOnClickListener {
            checkAnswer(rbGuncelOptionC)
        }

        rbGuncelOptionD.setOnClickListener {
            checkAnswer(rbGuncelOptionD)

        }
        rbGuncelOptionE.setOnClickListener {
            checkAnswer(rbGuncelOptionE)

        }

    }


    private fun checkAnswer(radioButton: RadioButton) {
        val answer: String? = radioButton.text.toString() //answer seeçilen cevap
        if (answer == trueAnswer) {
            radioButton.setBackgroundColor(Color.GREEN)
            trueResponse++
            setEnableRadioButtons(false)
        } else {
            radioButton.setBackgroundColor(Color.RED)
            falseResponse++
            setEnableRadioButtons(false)
            showTrueRadiobutton()
        }
    }

    private fun showTrueRadiobutton() {
        if (rbGuncelOptionA.text == trueAnswer) rbGuncelOptionA.setBackgroundColor(Color.GREEN)
        else if (rbGuncelOptionB.text == trueAnswer) rbGuncelOptionB.setBackgroundColor(Color.GREEN)
        else if (rbGuncelOptionC.text == trueAnswer) rbGuncelOptionC.setBackgroundColor(Color.GREEN)
        else if (rbGuncelOptionD.text == trueAnswer) rbGuncelOptionD.setBackgroundColor(Color.GREEN)
        else if (rbGuncelOptionE.text == trueAnswer) rbGuncelOptionE.setBackgroundColor(Color.GREEN)
    }

    private fun setEnableRadioButtons(b: Boolean) {
        rbGuncelOptionA.isEnabled = b
        rbGuncelOptionB.isEnabled = b
        rbGuncelOptionC.isEnabled = b
        rbGuncelOptionD.isEnabled = b
        rbGuncelOptionE.isEnabled = b
    }


    private fun showSettingsDialogGuncel() {
        trueResponse = 0
        falseResponse = 0
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_test_detail)
        dialog.setCancelable(false)
        dialog.cb_4d1y.setOnClickListener {
            false4deltrue = dialog.cb_4d1y.isChecked
        }
        dialog.btn_start.setOnClickListener {
            dialog.cancel()
            val et_time_text = dialog.et_time.text.toString()
            if (et_time_text != null && et_time_text != "") {
                time = et_time_text.toInt()
                runnable = object : Runnable { //süreyi başlatma ayarları
                    override fun run() {
                        if (time == 0) {
                            tv_guncel_time.visibility = View.GONE
                            timeEnd()
                        } else if (time != -1) {
                            tv_guncel_time.visibility = View.VISIBLE
                            tv_guncel_time.text = "Süre : $time dk"
                            time--
                            handler.postDelayed(this, 60000)  ///saniye ayarladık
                        }

                    }
                }
            }

            handler.post(runnable)//runnable ı başlattık

        }
        dialog.radio_btn_random.setOnClickListener {
            if (dialog.radio_btn_random.isChecked) {
                dialog.ll_clasic_settings.visibility = View.GONE
            }
        }
        dialog.radio_btn_clasic.setOnClickListener {
            if (dialog.radio_btn_clasic.isChecked) {
                dialog.ll_clasic_settings.visibility = View.VISIBLE
            }
        }
        dialog.show()

    }

    private fun timeEnd() {
        time = -1
        val dialogResult = Dialog(requireContext())
        dialogResult.setContentView(R.layout.dialog_test_result)

        dialogResult.tv_true_number.text = trueResponse.toString()
        dialogResult.tv_false_number.text = falseResponse.toString()
        if (false4deltrue) {
            dialogResult.ll_net_count.visibility = View.VISIBLE
            dialogResult.tv_net_number.text = (trueResponse - (falseResponse / 4)).toString()

        } else dialogResult.ll_net_count.visibility = View.GONE
        dialogResult.btn_result.setOnClickListener { //dialoğa yönlendirmesi yapıldı
            dialogResult.cancel()
            showSettingsDialogGuncel()
        }
        dialogResult.show()
    }


    fun getTestGuncel() {
        val ref_t = Firebase.database.getReference("Test/Guncel")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd =
                    (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").value.toString() //soruyu text e atadık.
                soru.let { s ->
                    tv_guncel_soru.text = s
                    val cevap1 = ss.child("cevap1").value.toString()
                    val cevap2 = ss.child("cevap2").value.toString()
                    val cevap3 = ss.child("cevap3").value.toString()
                    val cevap4 = ss.child("cevap4").value.toString()
                    val cevap5 = ss.child("cevap5").value.toString()
                    trueAnswer = cevap1
                    val rndOption = (0..4).random().toInt() //5 seçenek var
                    if (rndOption == 0) {
                        rbGuncelOptionA.text = cevap1
                        rbGuncelOptionB.text = cevap2
                        rbGuncelOptionC.text = cevap3
                        rbGuncelOptionD.text = cevap4
                        rbGuncelOptionE.text = cevap5

                    } else if (rndOption == 1) {
                        rbGuncelOptionA.text = cevap5
                        rbGuncelOptionB.text = cevap4
                        rbGuncelOptionC.text = cevap3
                        rbGuncelOptionD.text = cevap2
                        rbGuncelOptionE.text = cevap1
                    } else if (rndOption == 2) {
                        rbGuncelOptionA.text = cevap4
                        rbGuncelOptionB.text = cevap5
                        rbGuncelOptionC.text = cevap1
                        rbGuncelOptionD.text = cevap3
                        rbGuncelOptionE.text = cevap2
                    } else if (rndOption == 3) {
                        rbGuncelOptionA.text = cevap2
                        rbGuncelOptionB.text = cevap1
                        rbGuncelOptionC.text = cevap3
                        rbGuncelOptionD.text = cevap4
                        rbGuncelOptionE.text = cevap5
                    } else {
                        rbGuncelOptionA.text = cevap3
                        rbGuncelOptionB.text = cevap5
                        rbGuncelOptionC.text = cevap2
                        rbGuncelOptionD.text = cevap1
                        rbGuncelOptionE.text = cevap4
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

