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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_test_detail.*
import kotlinx.android.synthetic.main.dialog_test_result.*
import kotlinx.android.synthetic.main.fragment_test1.*
import kotlinx.android.synthetic.main.fragment_test1.tv_time
import kotlinx.android.synthetic.main.fragment_test_vat.*


class TestFragmentVat : Fragment() {
    //global tanımlama

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

    override  fun onCreateView(
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
            getTestVatandaslik()
        }


        rb_vatandaslik.setOnClickListener {
            //checkAnswer(radioButt
            checkAnswer(rb_vatandaslik)
        }

        rb_vatandaslik1.setOnClickListener {
            checkAnswer(rb_vatandaslik1)
        }


        rb_vatandaslik2.setOnClickListener {
            checkAnswer(rb_vatandaslik2)
        }

        rb_vatandaslik3.setOnClickListener {
            checkAnswer(rb_vatandaslik3)

        }
        rb_vatandaslik4.setOnClickListener {
            checkAnswer(rb_vatandaslik4)

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
        if (rb_vatandaslik.text==trueAnswer) rb_vatandaslik.setBackgroundColor(Color.GREEN)
        else if (rb_vatandaslik1.text==trueAnswer) rb_vatandaslik1.setBackgroundColor(Color.GREEN)
        else if (rb_vatandaslik2.text==trueAnswer) rb_vatandaslik2.setBackgroundColor(Color.GREEN)
        else if (rb_vatandaslik3.text==trueAnswer) rb_vatandaslik3.setBackgroundColor(Color.GREEN)
        else if (rb_vatandaslik4.text==trueAnswer) rb_vatandaslik4.setBackgroundColor(Color.GREEN)
    }

    private fun setEnableRadioButtons(b: Boolean) {
        rb_vatandaslik.isEnabled=b
        rb_vatandaslik1.isEnabled=b
        rb_vatandaslik2.isEnabled=b
        rb_vatandaslik3.isEnabled=b
        rb_vatandaslik4.isEnabled=b
    }














    private fun showSettingsDialogVatandaslik() {
        trueResponse=0
        falseResponse=0
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_test_detail)
        dialog.setCancelable(false)
        dialog.cb_4d1y.setOnClickListener {
            false4deltrue=dialog.cb_4d1y.isChecked
        }
        dialog.btn_start.setOnClickListener { dialog.cancel()

            val et_time_text= dialog.et_time.text.toString()
            if (et_time_text!=null&&et_time_text!=""){
                time=et_time_text.toInt()
                runnable = object : Runnable { //süreyi başlatma ayarları
                    override fun run() {
                        if (time == 0){
                            tv_vat_time.visibility=View.GONE
                            timeEnd()
                        }
                        else if(time!=-1)
                        {

                            tv_vat_time.visibility=View.VISIBLE
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

    private fun timeEnd() {//sonuç sayfası parametreleri  //tv_true_number:Int?,tv_false_number:Int?,tv_net_number:Float?


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
            showSettingsDialogVatandaslik()
        }
    }
    fun getTestVatandaslik() {
        val ref_t = Firebase.database.getReference("Test/Vatandaslik")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd = (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").getValue().toString() //soruyu text e atadık.
                soru?.let { s ->
                    textViewTestVat.text = s
                    val cevap1 = ss.child("cevap1").getValue().toString()
                    val cevap2 = ss.child("cevap2").getValue().toString()
                    val cevap3 = ss.child("cevap3").getValue().toString()
                    val cevap4 = ss.child("cevap4").getValue().toString()
                    val cevap5 = ss.child("cevap5").getValue().toString()
                    trueAnswer = cevap1
                    val rndOption = (0..4).random().toInt() //5 seçenek var
                    if (rndOption == 0) {
                        rb_vatandaslik.text = cevap1
                        rb_vatandaslik1.text = cevap2
                        rb_vatandaslik2.text = cevap3
                        rb_vatandaslik3.text = cevap4
                        rb_vatandaslik4.text = cevap5

                    } else if (rndOption == 1) {
                        rb_vatandaslik.text = cevap5
                        rb_vatandaslik1.text = cevap4
                        rb_vatandaslik2.text = cevap3
                        rb_vatandaslik3.text = cevap2
                        rb_vatandaslik4.text = cevap1
                    } else if (rndOption == 2) {
                        rb_vatandaslik.text = cevap4
                        rb_vatandaslik1.text = cevap5
                        rb_vatandaslik2.text = cevap1
                        rb_vatandaslik3.text = cevap3
                        rb_vatandaslik4.text = cevap2
                    } else if (rndOption == 3) {
                        rb_vatandaslik.text = cevap2
                        rb_vatandaslik1.text = cevap1
                        rb_vatandaslik2.text = cevap3
                        rb_vatandaslik3.text = cevap4
                        rb_vatandaslik4.text = cevap5
                    } else {
                        rb_vatandaslik.text = cevap3
                        rb_vatandaslik1.text = cevap5
                        rb_vatandaslik2.text = cevap2
                        rb_vatandaslik3.text = cevap1
                        rb_vatandaslik4.text = cevap4
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
