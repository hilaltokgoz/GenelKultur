package com.genelkultur

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_test_detail.*
import kotlinx.android.synthetic.main.dialog_test_result.*
import kotlinx.android.synthetic.main.fragment_test2.*
import kotlinx.android.synthetic.main.fragment_test2.rb_co1
import kotlinx.android.synthetic.main.fragment_test2.rb_co2
import kotlinx.android.synthetic.main.fragment_test2.rb_co3
import kotlinx.android.synthetic.main.fragment_test2.rb_co4
import kotlinx.android.synthetic.main.fragment_test2.rb_co5
import kotlinx.android.synthetic.main.fragment_test3.*


class TestFragment2 : Fragment() {
    var runnable:Runnable= Runnable {  }   //Runnable bir arayüz
    var handler: Handler = Handler()  //süreyi de handler da oluşturcam.
    var time:Int = -1
    var trueResponse=0
    var falseResponse=0
    var false4deltrue:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    var trueAnswer :String?=null

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

            rb_co1.isChecked=false
            rb_co2.isChecked=false
            rb_co3.isChecked=false
            rb_co4.isChecked=false
            rb_co5.isChecked=false

            rb_co1.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
            rb_co2.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
            rb_co3.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
            rb_co4.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
            rb_co5.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
        }
        rb_co1.setOnClickListener {
            checkAnswer(rb_co1)
        }


        rb_co2.setOnClickListener {
            checkAnswer(rb_co2)
        }


        rb_co3.setOnClickListener {
            checkAnswer(rb_co3)
        }

        rb_co4.setOnClickListener {
            checkAnswer(rb_co4)
        }

        rb_co5.setOnClickListener {
            checkAnswer(rb_co5)
        }
    }


    private fun checkAnswer(radioButton: RadioButton) {
        val answer:String?=radioButton.text.toString() //answer seeçilen cevap
        if (answer==trueAnswer){
            radioButton.setBackgroundColor(Color.GREEN)
            //todo: dogru cevap verildi yapılacakları yap.
            setEnableRadioButtons(false)
        }
        else{
            radioButton.setBackgroundColor(Color.RED)

            //todo:yanlış cevap verildi yapılacakları yap
            setEnableRadioButtons(false)
            showTrueRadiobutton()
        }
    }

    private fun showTrueRadiobutton() {
        if (rb_co1.text==trueAnswer) rb_co1.setBackgroundColor(Color.GREEN)
        else if (rb_co2.text==trueAnswer) rb_co2.setBackgroundColor(Color.GREEN)
        else if (rb_co3.text==trueAnswer) rb_co3.setBackgroundColor(Color.GREEN)
        else if (rb_co4.text==trueAnswer) rb_co4.setBackgroundColor(Color.GREEN)
        else if (rb_co5.text==trueAnswer) rb_co5.setBackgroundColor(Color.GREEN)
    }

    private fun setEnableRadioButtons(b: Boolean) {
        rb_co1.isEnabled=b
        rb_co2.isEnabled=b
        rb_co3.isEnabled=b
        rb_co4.isEnabled=b
        rb_co5.isEnabled=b
    }



 private fun showSettingsDialogCografya(){
     trueResponse=0
     falseResponse=0
     val dialog =Dialog(requireContext())
     dialog.setContentView(R.layout.dialog_test_detail)
     dialog.setCancelable(false)
     dialog.cb_4d1y.setOnClickListener {
         false4deltrue=dialog.cb_4d1y.isChecked
     }
     dialog.btn_start.setOnClickListener { dialog.cancel()
         val et_time_cografya_text= dialog.et_time.text.toString()
         if (et_time_cografya_text!=null && et_time_cografya_text!=""){
             time=et_time_cografya_text.toInt()
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
         if (dialog.radio_btn_random.isChecked){
             dialog.ll_clasic_settings.visibility=View.GONE
         }
     }
     dialog.radio_btn_clasic.setOnClickListener {
         if (dialog.radio_btn_clasic.isChecked){
             dialog.ll_clasic_settings.visibility=View.VISIBLE
         }
     }
     dialog.show()

 }
    private fun timeEnd(){
        time=-1
        val dialogResult=Dialog(requireContext())
        dialogResult.setContentView(R.layout.dialog_test_result)
        dialogResult.show()
        dialogResult.tv_true_number.text=trueResponse.toString()
        dialogResult.tv_false_number.text=trueResponse.toString()
        if (false4deltrue){
            dialogResult.ll_net_count.visibility=View.VISIBLE
            dialogResult.tv_net_number.text=(trueResponse-(falseResponse/4)).toString()
        }
        else    dialogResult.ll_net_count.visibility=View.GONE
        dialogResult.btn_result.setOnClickListener { //dialoğa yönlendir.
            dialogResult.cancel()
            showSettingsDialogCografya()
    }

     }


    fun getTestCografya() {
        val ref_t = Firebase.database.getReference("Test/Cografya")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").getValue().toString() //soruyu text e atadık.
                soru?.let { s ->
                    tv_cografya_soru1.text = s
                    val cevap1 = ss.child("cevap1").getValue().toString()
                    val cevap2 = ss.child("cevap2").getValue().toString()
                    val cevap3 = ss.child("cevap3").getValue().toString()
                    val cevap4 = ss.child("cevap4").getValue().toString()
                    val cevap5 = ss.child("cevap5").getValue().toString()
                    trueAnswer = cevap1
                    val rndOption = (0..4).random().toInt() //5 seçenek var
                    if (rndOption == 0) {
                        rb_co1.text = cevap1
                        rb_co2.text = cevap2
                        rb_co3.text = cevap3
                        rb_co4.text = cevap4
                        rb_co5.text = cevap5

                    } else if (rndOption == 1) {
                        rb_co1.text = cevap5
                        rb_co2.text = cevap4
                        rb_co3.text = cevap3
                        rb_co4.text = cevap2
                        rb_co5.text = cevap1
                    } else if (rndOption == 2) {
                        rb_co1.text = cevap4
                        rb_co2.text = cevap5
                        rb_co3.text = cevap1
                        rb_co4.text = cevap3
                        rb_co5.text = cevap2
                    } else if (rndOption == 3) {
                        rb_co1.text = cevap2
                        rb_co2.text = cevap1
                        rb_co3.text = cevap3
                        rb_co4.text = cevap4
                        rb_co5.text = cevap5
                    } else {
                        rb_co1.text = cevap3
                        rb_co2.text = cevap5
                        rb_co3.text = cevap2
                        rb_co4.text = cevap1
                        rb_co5.text = cevap4
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}









