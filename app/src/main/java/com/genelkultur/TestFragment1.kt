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
import kotlinx.android.synthetic.main.dialog_test_detail.view.*
import kotlinx.android.synthetic.main.dialog_test_result.*
import kotlinx.android.synthetic.main.fragment_anasayfa.*
import kotlinx.android.synthetic.main.fragment_test1.*


class TestFragment1 : Fragment() {
    //global tanımlama

    var runnable:Runnable= Runnable {  }   //Runnable bir arayüz
    var handler: Handler = Handler()  //süreyi de handler da oluşturcam.
    var time:Int = -1

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
        val dialog =Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_test_detail)
        dialog.setCancelable(false)

        dialog.btn_start.setOnClickListener { dialog.cancel() //başla butonununa basınca dialogtan çık
            time=dialog.editTextNumber.text.toString().toInt() //seçtiği zamanı text e atadık.

            runnable=object :Runnable{ //süreyi başlatma ayarları
                override fun run() {

                    tv_time.text="Süre : $time dk"
                    time--
                    handler.postDelayed(this,60000)  ///saniye ayarladık
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


        super.onViewCreated(view, savedInstanceState)









    //    var sure=ll_clasic_time.editTextNumber.inputType

        /*val dialogResult=Dialog(requireContext())
        dialogResult.setContentView(R.layout.dialog_test_result)
        dialogResult.ll_test_result.visibility=View.GONE//gizle
        if (sure==0){
            dialogResult.ll_test_result.visibility=View.VISIBLE
            dialogResult.setCancelable(false)
            dialogResult.result_button_id.setOnClickListener { dialog.setContentView(R.layout.dialog_test_detail) } ///diğer dialoğa yönlendi
        }
*/



    }


    }
