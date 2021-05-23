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
import kotlinx.android.synthetic.main.fragment_test2.rbCografyaOptionA
import kotlinx.android.synthetic.main.fragment_test2.rbCografyaOptionB
import kotlinx.android.synthetic.main.fragment_test2.rbCografyaOptionC
import kotlinx.android.synthetic.main.fragment_test2.rbCografyaOptionD
import kotlinx.android.synthetic.main.fragment_test2.rbCografyaOptionE
import kotlinx.android.synthetic.main.fragment_test3.*


class TestFragment2 : Fragment() {
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
        return inflater.inflate(R.layout.fragment_test3, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSettingsDialogCografya()
        getTestCografya()

        iv_cog_ref_Test.setOnClickListener {
            setEnableRadioButtons(true)

            getTestCografya()

            rbCografyaOptionA.isChecked = false
            rbCografyaOptionB.isChecked = false
            rbCografyaOptionC.isChecked = false
            rbCografyaOptionD.isChecked = false
            rbCografyaOptionE.isChecked = false

            rbCografyaOptionA.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbCografyaOptionB.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbCografyaOptionC.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbCografyaOptionD.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbCografyaOptionE.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
        }
        rbCografyaOptionA.setOnClickListener {
            checkAnswer(rbCografyaOptionA)
        }


        rbCografyaOptionB.setOnClickListener {
            checkAnswer(rbCografyaOptionB)
        }


        rbCografyaOptionC.setOnClickListener {
            checkAnswer(rbCografyaOptionC)
        }

        rbCografyaOptionD.setOnClickListener {
            checkAnswer(rbCografyaOptionD)
        }

        rbCografyaOptionE.setOnClickListener {
            checkAnswer(rbCografyaOptionE)
        }
    }


    private fun checkAnswer(radioButton: RadioButton) {
        val answer: String? = radioButton.text.toString() //answer seeçilen cevap
        if (answer == trueAnswer) {
            radioButton.setBackgroundColor(Color.GREEN)
            //todo: dogru cevap verildi yapılacakları yap.
            setEnableRadioButtons(false)
        } else {
            radioButton.setBackgroundColor(Color.RED)

            //todo:yanlış cevap verildi yapılacakları yap
            setEnableRadioButtons(false)
            showTrueRadiobutton()
        }
    }

    private fun showTrueRadiobutton() {
        if (rbCografyaOptionA.text == trueAnswer) rbCografyaOptionA.setBackgroundColor(Color.GREEN)
        else if (rbCografyaOptionB.text == trueAnswer) rbCografyaOptionB.setBackgroundColor(Color.GREEN)
        else if (rbCografyaOptionC.text == trueAnswer) rbCografyaOptionC.setBackgroundColor(Color.GREEN)
        else if (rbCografyaOptionD.text == trueAnswer) rbCografyaOptionD.setBackgroundColor(Color.GREEN)
        else if (rbCografyaOptionE.text == trueAnswer) rbCografyaOptionE.setBackgroundColor(Color.GREEN)
    }

    private fun setEnableRadioButtons(b: Boolean) {
        rbCografyaOptionA.isEnabled = b
        rbCografyaOptionB.isEnabled = b
        rbCografyaOptionC.isEnabled = b
        rbCografyaOptionD.isEnabled = b
        rbCografyaOptionE.isEnabled = b
    }


    private fun showSettingsDialogCografya() {
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
            val et_time_cografya_text = dialog.et_time.text.toString()
            if (et_time_cografya_text != null && et_time_cografya_text != "") {
                time = et_time_cografya_text.toInt()
                runnable = object : Runnable { //süreyi başlatma ayarları
                    override fun run() {
                        if (time == 0) {
                            tv_cografya_time.visibility = View.GONE
                            timeEnd()
                        } else if (time != -1) {
                            tv_cografya_time.visibility = View.VISIBLE
                            tv_cografya_time.text = "Süre : $time dk"
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
        dialogResult.show()
        dialogResult.tv_true_number.text = trueResponse.toString()
        dialogResult.tv_false_number.text = trueResponse.toString()
        if (false4deltrue) {
            dialogResult.ll_net_count.visibility = View.VISIBLE
            dialogResult.tv_net_number.text = (trueResponse - (falseResponse / 4)).toString()
        } else dialogResult.ll_net_count.visibility = View.GONE
        dialogResult.btn_result.setOnClickListener { //dialoğa yönlendir.
            dialogResult.cancel()
            showSettingsDialogCografya()
        }

    }


    fun getTestCografya() {
        val ref_t = Firebase.database.getReference("Test/Cografya")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd =
                    (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").value.toString() //soruyu text e atadık.
                soru.let { s ->
                    tv_cografya_soru1.text = s
                    val cevap1 = ss.child("cevap1").value.toString()
                    val cevap2 = ss.child("cevap2").value.toString()
                    val cevap3 = ss.child("cevap3").value.toString()
                    val cevap4 = ss.child("cevap4").value.toString()
                    val cevap5 = ss.child("cevap5").value.toString()
                    trueAnswer = cevap1
                    val rndOption = (0..4).random().toInt() //5 seçenek var
                    if (rndOption == 0) {
                        rbCografyaOptionA.text = cevap1
                        rbCografyaOptionB.text = cevap2
                        rbCografyaOptionC.text = cevap3
                        rbCografyaOptionD.text = cevap4
                        rbCografyaOptionE.text = cevap5

                    } else if (rndOption == 1) {
                        rbCografyaOptionA.text = cevap5
                        rbCografyaOptionB.text = cevap4
                        rbCografyaOptionC.text = cevap3
                        rbCografyaOptionD.text = cevap2
                        rbCografyaOptionE.text = cevap1
                    } else if (rndOption == 2) {
                        rbCografyaOptionA.text = cevap4
                        rbCografyaOptionB.text = cevap5
                        rbCografyaOptionC.text = cevap1
                        rbCografyaOptionD.text = cevap3
                        rbCografyaOptionE.text = cevap2
                    } else if (rndOption == 3) {
                        rbCografyaOptionA.text = cevap2
                        rbCografyaOptionB.text = cevap1
                        rbCografyaOptionC.text = cevap3
                        rbCografyaOptionD.text = cevap4
                        rbCografyaOptionE.text = cevap5
                    } else {
                        rbCografyaOptionA.text = cevap3
                        rbCografyaOptionB.text = cevap5
                        rbCografyaOptionC.text = cevap2
                        rbCografyaOptionD.text = cevap1
                        rbCografyaOptionE.text = cevap4
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}









