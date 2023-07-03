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
import kotlinx.android.synthetic.main.fragment_test1.*


class TestFragment1 : Fragment() {
    //global tanımlama


    var runnable: Runnable = Runnable { }   //Runnable bir arayüz
    var handler: Handler = Handler()  //süreyi de handler da oluşturcam.
    var time: Int = -1
    var trueResponse = 0 //doğru cevap
    var falseResponse = 0 //yanlış cevap
    var false4deltrue: Boolean = false
    var trueAnswer: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSettingsDialog()
        getTestTarih()
        iv_RefreshTestTarih.setOnClickListener {
            setEnableRadioButtons(true)

            getTestTarih()

            rbOptionA.isChecked = false
            rbOptionB.isChecked = false
            rbOptionC.isChecked = false
            rbOptionD.isChecked = false
            rbOptionE.isChecked = false



            rbOptionA.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbOptionB.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbOptionC.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbOptionD.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
            rbOptionE.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.tide_water_green
                )
            )
        }
        rbOptionA.setOnClickListener {
            //checkAnswer(radioButt
            checkAnswer(rbOptionA)
        }

        rbOptionB.setOnClickListener {
            checkAnswer(rbOptionB)
        }


        rbOptionC.setOnClickListener {
            checkAnswer(rbOptionC)
        }

        rbOptionD.setOnClickListener {
            checkAnswer(rbOptionD)

        }
        rbOptionE.setOnClickListener {
            checkAnswer(rbOptionE)

        }
    }
   fun checkAnswer(radioButton: RadioButton) {
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
        if (rbOptionA.text == trueAnswer) rbOptionA.setBackgroundColor(Color.GREEN)
        else if (rbOptionB.text == trueAnswer) rbOptionB.setBackgroundColor(Color.GREEN)
        else if (rbOptionC.text == trueAnswer) rbOptionC.setBackgroundColor(Color.GREEN)
        else if (rbOptionD.text == trueAnswer) rbOptionD.setBackgroundColor(Color.GREEN)
        else if (rbOptionE.text == trueAnswer) rbOptionE.setBackgroundColor(Color.GREEN)
    }

    private fun setEnableRadioButtons(b: Boolean) {
        rbOptionA.isEnabled = b
        rbOptionB.isEnabled = b
        rbOptionC.isEnabled = b
        rbOptionD.isEnabled = b
        rbOptionE.isEnabled = b
    }


    private fun showSettingsDialog() {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_test_detail)
        dialog.setCancelable(false)
        dialog.cb_4d1y.setOnClickListener {
            false4deltrue = dialog.cb_4d1y.isChecked
        }
        //if (radio_btn_random.isChecked) tv_time.visibility=View.GONE //eğer rastgele seçiliuyse süre yi görünmez yap.
        dialog.btn_start.setOnClickListener {
            dialog.cancel() //başla butonununa basınca dialogtan çık

            val et_time_text = dialog.et_time.text.toString()  //girilen süre alındı
            if (et_time_text != null && et_time_text != "") {
                time = et_time_text.toInt()
                runnable = object : Runnable { //süreyi başlatma ayarları
                    override fun run() {
                        if (time == 0) {
                            tv_time.visibility = View.GONE
                            timeEnd()
                        } else if (time != -1) {

                            tv_time.visibility = View.VISIBLE
                            tv_time.text = "Süre : $time dk"
                            time--
                            handler.postDelayed(this, 10000)  ///saniye ayarladık
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
            showSettingsDialog()
        }
        dialogResult.show()
    }

// true+false=soru sayısını tut ,time end
    fun getTestTarih() {
        val ref_t = Firebase.database.getReference("Test/Tarih")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd =
                    (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").value.toString() //soruyu text e atadık.
                soru.let { s ->
                    textViewTest10.text = s
                    val cevap1 = ss.child("cevap1").value.toString()
                    val cevap2 = ss.child("cevap2").value.toString()
                    val cevap3 = ss.child("cevap3").value.toString()
                    val cevap4 = ss.child("cevap4").value.toString()
                    val cevap5 = ss.child("cevap5").value.toString()
                    trueAnswer = cevap1
                    val rndOption = (0..4).random().toInt() //5 seçenek var
                    if (rndOption == 0) {
                        rbOptionA.text = cevap1
                        rbOptionB.text = cevap2
                        rbOptionC.text = cevap3
                        rbOptionD.text = cevap4
                        rbOptionE.text = cevap5

                    } else if (rndOption == 1) {
                        rbOptionA.text = cevap5
                        rbOptionB.text = cevap4
                        rbOptionC.text = cevap3
                        rbOptionD.text = cevap2
                        rbOptionE.text = cevap1
                    } else if (rndOption == 2) {
                        rbOptionA.text = cevap4
                        rbOptionB.text = cevap5
                        rbOptionC.text = cevap1
                        rbOptionD.text = cevap3
                        rbOptionE.text = cevap2
                    } else if (rndOption == 3) {
                        rbOptionA.text = cevap2
                        rbOptionB.text = cevap1
                        rbOptionC.text = cevap3
                        rbOptionD.text = cevap4
                        rbOptionE.text = cevap5
                    } else {
                        rbOptionA.text = cevap3
                        rbOptionB.text = cevap5
                        rbOptionC.text = cevap2
                        rbOptionD.text = cevap1
                        rbOptionE.text = cevap4
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })


    }
}
