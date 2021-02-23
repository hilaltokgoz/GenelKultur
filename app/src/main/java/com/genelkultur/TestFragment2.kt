package com.genelkultur

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_test_detail.*

class TestFragment2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_test_detail)//oluşturulan dialogu göster
        dialog.setCancelable(false) //arka plana tıklamayı kapat
        dialog.btn_start.setOnClickListener { dialog.cancel() }
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






}