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
import kotlinx.android.synthetic.main.fragment_test_vat.*


class TestFragmentVat : Fragment() {
    //global tanımlama

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
        return inflater.inflate(R.layout.fragment_test_vat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSettingsDialogVatandaslik()

        getTestVatandaslik()
        iv_vat_refrest_test.setOnClickListener {
            setEnableRadioButtons(true)

            getTestVatandaslik()

            rbVat_OptionA.isChecked = false
            rbVat_OptionB.isChecked = false
            rbVat_OptionC.isChecked = false
            rbVat_OptionD.isChecked = false
            rbVat_OptionE.isChecked = false



            rbVat_OptionA.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbVat_OptionB.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbVat_OptionC.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbVat_OptionD.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbVat_OptionE.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
        }


        rbVat_OptionA.setOnClickListener {
            //checkAnswer(radioButt
            checkAnswer(rbVat_OptionA)
        }

        rbVat_OptionB.setOnClickListener {
            checkAnswer(rbVat_OptionB)
        }


        rbVat_OptionC.setOnClickListener {
            checkAnswer(rbVat_OptionC)
        }

        rbVat_OptionD.setOnClickListener {
            checkAnswer(rbVat_OptionD)

        }
        rbVat_OptionE.setOnClickListener {
            checkAnswer(rbVat_OptionE)

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
        if (rbVat_OptionA.text == trueAnswer) rbVat_OptionA.setBackgroundColor(Color.GREEN)
        else if (rbVat_OptionB.text == trueAnswer) rbVat_OptionB.setBackgroundColor(Color.GREEN)
        else if (rbVat_OptionC.text == trueAnswer) rbVat_OptionC.setBackgroundColor(Color.GREEN)
        else if (rbVat_OptionD.text == trueAnswer) rbVat_OptionD.setBackgroundColor(Color.GREEN)
        else if (rbVat_OptionE.text == trueAnswer) rbVat_OptionE.setBackgroundColor(Color.GREEN)
    }

    private fun setEnableRadioButtons(b: Boolean) {
        rbVat_OptionA.isEnabled = b
        rbVat_OptionB.isEnabled = b
        rbVat_OptionC.isEnabled = b
        rbVat_OptionD.isEnabled = b
        rbVat_OptionE.isEnabled = b
    }


    private fun showSettingsDialogVatandaslik() {
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

            val etTimeText = dialog.et_time.text.toString()
            if (etTimeText != null && etTimeText != "") {
                time = etTimeText.toInt()
                runnable = object : Runnable { //süreyi başlatma ayarları
                    override fun run() {
                        if (time == 0) {
                            tv_vat_time.visibility = View.GONE
                            timeEnd()
                        } else if (time != -1) {

                            tv_vat_time.visibility = View.VISIBLE
                            tv_vat_time.text = "Süre : $time dk"
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

    private fun timeEnd() {//sonuç sayfası parametreleri  //tv_true_number:Int?,tv_false_number:Int?,tv_net_number:Float?


        time = -1
        val dialogResult = Dialog(requireContext())
        dialogResult.setContentView(R.layout.dialog_test_result)

        dialogResult.tv_true_number.text = trueResponse.toString()
        dialogResult.tv_false_number.text = falseResponse.toString()
        if (false4deltrue) {
            dialogResult.ll_net_count.visibility = View.VISIBLE
            dialogResult.tv_net_number.text = (trueResponse - (falseResponse / 4)).toString()

        } else dialogResult.ll_net_count.visibility = View.GONE
        dialogResult.btn_result.setOnClickListener { //dialoğa yönlendir.
            dialogResult.cancel()
            showSettingsDialogVatandaslik()
        }
        dialogResult.show()
    }

    fun getTestVatandaslik() {
        val ref_t = Firebase.database.getReference("Test/Vatandaslik")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd =
                    (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").value.toString() //soruyu text e atadık.
                soru.let { s ->
                    textViewTestVat.text = s
                    val cevap1 = ss.child("cevap1").value.toString()
                    val cevap2 = ss.child("cevap2").value.toString()
                    val cevap3 = ss.child("cevap3").value.toString()
                    val cevap4 = ss.child("cevap4").value.toString()
                    val cevap5 = ss.child("cevap5").value.toString()
                    trueAnswer = cevap1
                    val rndOption = (0..4).random().toInt() //5 seçenek var
                    if (rndOption == 0) {
                        rbVat_OptionA.text = cevap1
                        rbVat_OptionB.text = cevap2
                        rbVat_OptionC.text = cevap3
                        rbVat_OptionD.text = cevap4
                        rbVat_OptionE.text = cevap5

                    } else if (rndOption == 1) {
                        rbVat_OptionA.text = cevap5
                        rbVat_OptionB.text = cevap4
                        rbVat_OptionC.text = cevap3
                        rbVat_OptionD.text = cevap2
                        rbVat_OptionE.text = cevap1
                    } else if (rndOption == 2) {
                        rbVat_OptionA.text = cevap4
                        rbVat_OptionB.text = cevap5
                        rbVat_OptionC.text = cevap1
                        rbVat_OptionD.text = cevap3
                        rbVat_OptionE.text = cevap2
                    } else if (rndOption == 3) {
                        rbVat_OptionA.text = cevap2
                        rbVat_OptionB.text = cevap1
                        rbVat_OptionC.text = cevap3
                        rbVat_OptionD.text = cevap4
                        rbVat_OptionE.text = cevap5
                    } else {
                        rbVat_OptionA.text = cevap3
                        rbVat_OptionB.text = cevap5
                        rbVat_OptionC.text = cevap2
                        rbVat_OptionD.text = cevap1
                        rbVat_OptionE.text = cevap4
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
