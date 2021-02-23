package com.genelkultur

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_test_detail.*
import kotlinx.android.synthetic.main.dialog_test_result.*
import kotlinx.android.synthetic.main.fragment_test1.*


class TestFragment1 : Fragment() {
    //global tanımlama

    var runnable:Runnable= Runnable {  }   //Runnable bir arayüz
    var handler: Handler = Handler()  //süreyi de handler da oluşturcam.
    var time:Int = -1
    var trueResponse=0
    var falseResponse=0
    var false3deltrue:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

    }

    private fun showSettingsDialog() {
        trueResponse=0
        falseResponse=0
        val dialog =Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_test_detail)
        dialog.setCancelable(false)
        dialog.cb_3d1y.setOnClickListener {
            false3deltrue=dialog.cb_3d1y.isChecked
        }
        //if (radio_btn_random.isChecked) tv_time.visibility=View.GONE //eğer rastgele seçiliuyse süre yi görünmez yap.
        dialog.btn_start.setOnClickListener { dialog.cancel() //başla butonununa basınca dialogtan çık

            /* if (et_time!=null) time = dialog.et_time.text.toString().toInt() //seçtiği zamanı text e atadık.
               else if (et_time==null) {
                   //tv_time.visibility=View.GONE
                 time=-1
             } //görünmez yap
             */
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
        if (false3deltrue){
            dialogResult.ll_net_count.visibility=View.VISIBLE
         dialogResult.tv_net_number.text=(trueResponse-(falseResponse/3)).toString()

        }
        else    dialogResult.ll_net_count.visibility=View.GONE
        dialogResult.btn_result.setOnClickListener { //dialoğa yönlendir.
          dialogResult.cancel()
            showSettingsDialog()
        }
    }
}
