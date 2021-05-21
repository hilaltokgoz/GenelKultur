package com.genelkultur

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import kotlinx.android.synthetic.main.fragment_o_mu_bu_mu1.*
import kotlinx.android.synthetic.main.fragment_test1.*


class TestFragment1 : Fragment() {
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
        iv_refresh_tarih_test.setOnClickListener {
            getTestTarih()
            radioButton7.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
            radioButton8.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
            radioButton9.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
            radioButton10.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
            radioButton11.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tide_water_green))
        }
        radioButton7.setOnClickListener {
            //checkAnswer(radioButt
            checkAnswer(radioButton7)
        }

        radioButton8.setOnClickListener {
            checkAnswer(radioButton8)
        }


        radioButton9.setOnClickListener {
            checkAnswer(radioButton9)
        }

        radioButton10.setOnClickListener {
            checkAnswer(radioButton10)

        }
        radioButton11.setOnClickListener {
            checkAnswer(radioButton11)

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
        if (radioButton7.text==trueAnswer) radioButton7.setBackgroundColor(Color.GREEN)
        else if (radioButton8.text==trueAnswer) radioButton8.setBackgroundColor(Color.GREEN)
        else if (radioButton9.text==trueAnswer) radioButton9.setBackgroundColor(Color.GREEN)
        else if (radioButton10.text==trueAnswer) radioButton10.setBackgroundColor(Color.GREEN)
        else if (radioButton11.text==trueAnswer) radioButton11.setBackgroundColor(Color.GREEN)
    }

    private fun setEnableRadioButtons(b: Boolean) {
        radioButton7.isEnabled=b
        radioButton8.isEnabled=b
        radioButton9.isEnabled=b
        radioButton10.isEnabled=b
        radioButton11.isEnabled=b
    }


    private fun showSettingsDialog() {
        trueResponse=0
        falseResponse=0
        val dialog =Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_test_detail)
        dialog.setCancelable(false)
        dialog.cb_4d1y.setOnClickListener {
            false4deltrue=dialog.cb_4d1y.isChecked
        }
        //if (radio_btn_random.isChecked) tv_time.visibility=View.GONE //eğer rastgele seçiliuyse süre yi görünmez yap.
        dialog.btn_start.setOnClickListener { dialog.cancel() //başla butonununa basınca dialogtan çık

           val et_time_text= dialog.et_time.text.toString()
if (et_time_text!=null&&et_time_text!=""){
    time=et_time_text.toInt()
    runnable = object : Runnable { //süreyi başlatma ayarları
        override fun run() {
            if (time == 0){
                tv_time.visibility=View.GONE
                timeEnd()
            }
            else if(time!=-1)
            {

                tv_time.visibility=View.VISIBLE
                tv_time.text = "Süre : $time dk"
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
    //TODO: timeri durdur. sonucu göster.
    //Todo:show result dialogr
    //todo: rouintg new test
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
        showSettingsDialog()
    }
}




    fun getTestTarih() {
        val ref_t = Firebase.database.getReference("Test/Tarih")
        ref_t.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rnd =
                    (0..snapshot.childrenCount - 1).random().toInt()//soruları random bi şekilde tut
                val ss = snapshot.children.toList()[rnd]
                val soru = ss.child("question").getValue().toString() //soruyu text e atadık.
                soru?.let { s ->
                    textViewTest10.text = s
                    val cevap1 = ss.child("cevap1").getValue().toString()
                    val cevap2 = ss.child("cevap2").getValue().toString()
                    val cevap3 = ss.child("cevap3").getValue().toString()
                    val cevap4 = ss.child("cevap4").getValue().toString()
                    val cevap5 = ss.child("cevap5").getValue().toString()
                    trueAnswer = cevap1
                     val rndOption = (0..4).random().toInt() //5 seçenek var
                    if (rndOption == 0) {
                        radioButton7.text = cevap1
                        radioButton8.text = cevap2
                        radioButton9.text = cevap3
                        radioButton10.text = cevap4
                        radioButton11.text = cevap5

                    } else if (rndOption == 1) {
                        radioButton7.text = cevap5
                        radioButton8.text = cevap4
                        radioButton9.text = cevap3
                        radioButton10.text = cevap2
                        radioButton11.text = cevap1
                    } else if (rndOption == 2) {
                        radioButton7.text = cevap4
                        radioButton8.text = cevap5
                        radioButton9.text = cevap1
                        radioButton10.text = cevap3
                        radioButton11.text = cevap2
                    } else if (rndOption == 3) {
                        radioButton7.text = cevap2
                        radioButton8.text = cevap1
                        radioButton9.text = cevap3
                        radioButton10.text = cevap4
                        radioButton11.text = cevap5
                    } else {
                        radioButton7.text = cevap3
                        radioButton8.text = cevap5
                        radioButton9.text = cevap2
                        radioButton10.text = cevap1
                        radioButton11.text = cevap4
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })


    }
}
